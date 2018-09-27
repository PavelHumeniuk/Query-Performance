package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Result;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.testcontainers.jdbc.ContainerDatabaseDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@AllArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {
    private final List<ContainerResolver> containers;

    @Override
    public List<Result> analyze(List<String> queries) {
        return containers.stream().parallel()
                .map(container -> executeQueries(container, queries))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private List<Result> executeQueries(ContainerResolver container, List<String> queries) {
        Lock lock = container.getLock();
        lock.lock();

        List<Result> results = new ArrayList<>();
        String jdbcUrl = getJdbcUrl(container.getContainer().getJdbcUrl(), container.getName().name());
        ContainerDatabaseDriver driver = new ContainerDatabaseDriver();
        try {
            Connection connection = driver.connect(jdbcUrl, null);
            connection.setAutoCommit(false);
            results = queries.stream()
                    .map(query -> executeSingleQuery(query, connection))
                    .peek(res -> res.setDatabase(container.getContainer().getDockerImageName()))
                    .collect(toList());
        } catch (SQLException e) {
            log.error(e);
        } finally {
            lock.unlock();
        }
        return results;
    }

    private Result executeSingleQuery(String query, Connection connection) {
        Result result = new Result();
        result.setQuery(query);
        try {
            Statement statement = connection.createStatement();

            long startTime = System.nanoTime();
            statement.execute(query);
            connection.commit();
            long endTime = System.nanoTime();

            result.setTime(endTime - startTime);
            result.setExplain(explainSql(query, statement));
        } catch (SQLException e) {
            result.setTime(-1);
            result.setExplain(e.getMessage());
        } finally {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
        return result;
    }

    private String explainSql(String sql, Statement statement) throws SQLException {
        statement.execute(format("explain %s", sql));
        ResultSet resultSet = statement.getResultSet();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder description = new StringBuilder();
        resultSet.next();
        for (int i = 1; i <= columnCount; i++) {
            description
                    .append(metaData.getColumnName(i))
                    .append(" : ").append(resultSet.getString(i))
                    .append("; ");
        }
        return description.toString();
    }

    private String getJdbcUrl(String oldUrl, String name) {
        return oldUrl.replace("jdbc", "jdbc:tc")
                .concat(format("?/TC_INITSCRIPT=%s/init.sql", name));
    }
}



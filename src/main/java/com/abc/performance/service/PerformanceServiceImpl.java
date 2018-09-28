package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Report;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.testcontainers.jdbc.ContainerDatabaseDriver;

import java.sql.*;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@AllArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {
    private final ContainerDatabaseDriver driver = new ContainerDatabaseDriver();

    @Override
    public List<Report> analyze(ContainerResolver container, List<String> queries) {
        List<Report> reports = emptyList();
        String jdbcUrl = getJdbcUrl(container.getContainer().getJdbcUrl(), container.getDatabaseName().name());
        try (Connection connection = driver.connect(jdbcUrl, null)) {
            reports = queries.stream()
                    .map(query -> executeSingleQuery(query, connection))
                    .peek(res -> res.setDatabase(container.getContainer().getDockerImageName()))
                    .collect(toList());
        } catch (SQLException e) {
            log.error(e);
        }
        return reports;
    }

    private Report executeSingleQuery(String query, Connection connection) {
        Report report = new Report();
        report.setQuery(query);
        try {
            Statement statement = connection.createStatement();

            long startTime = System.nanoTime();
            statement.execute(query);
            long endTime = System.nanoTime();

            report.setTime(endTime - startTime);
            report.setExplain(explainSql(query, statement));
        } catch (SQLException e) {
            log.error(e);
            report.setTime(-1);
            report.setExplain(e.getMessage());
        }
        return report;
    }

    private String explainSql(String sql, Statement statement) throws SQLException {
        String explain = format("explain %s", sql);
        log.info(explain);
        statement.execute(explain);
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



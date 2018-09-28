package com.abc.performance.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.testcontainers.jdbc.ContainerDatabaseDriver;

import java.sql.*;

import static java.lang.String.format;

@Log4j2
@Component
public class ContainerRepositoryImpl implements ContainerRepository {
    private final ContainerDatabaseDriver driver = new ContainerDatabaseDriver();

    @Override
    public void executeQuery(String query, String jdbcUrl) {
        try (Connection connection = driver.connect(jdbcUrl, null)) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    @Override
    public String explainSql(String sql, String jdbcUrl) {
        StringBuilder description = new StringBuilder();
        try (Connection connection = driver.connect(jdbcUrl, null)) {
            Statement statement = connection.createStatement();
            String explain = format("explain %s", sql);
            log.info(explain);
            statement.execute(explain);
            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            resultSet.next();
            for (int i = 1; i <= columnCount; i++) {
                description
                        .append(metaData.getColumnName(i))
                        .append(" : ").append(resultSet.getString(i))
                        .append("; ");
            }
        } catch (SQLException e) {
            log.error(e);
            description.append(e.getMessage());
        }

        return description.toString();
    }


}

package com.abc.performance.repository;

public interface ContainerRepository {
    void executeQuery(String query, String jdbcUrl);

    String explainSql(String sql, String jdbcUrl);
}

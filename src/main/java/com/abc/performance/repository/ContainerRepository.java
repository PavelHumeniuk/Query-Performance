package com.abc.performance.repository;

public interface ContainerRepository {
    long executeQuery(String query, String jdbcUrl);

    String explainSql(String sql, String jdbcUrl);
}

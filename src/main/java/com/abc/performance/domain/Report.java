package com.abc.performance.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    /**
     * time in nanoseconds
     */
    private long time;

    /**
     * database name
     */
    private String database;

    /**
     * sql query
     */
    private String query;

    /**
     * result of explain for query {@link Report#query}
     */
    private String explain;
}

package com.abc.performance.rest;

import lombok.Data;

import java.util.List;

@Data
class Request {
    /**
     * sql queries
     */
    private List<String> queries;
}

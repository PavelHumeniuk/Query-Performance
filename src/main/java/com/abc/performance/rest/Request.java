package com.abc.performance.rest;

import lombok.Data;

import java.util.List;

@Data
class Request {
    private List<String> queries;
}

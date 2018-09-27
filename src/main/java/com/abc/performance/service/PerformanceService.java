package com.abc.performance.service;

import com.abc.performance.domain.Result;

import java.util.List;

public interface PerformanceService {
    List<Result> analyze(List<String> queries);
}

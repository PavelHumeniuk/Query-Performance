package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Report;

import java.util.List;

public interface PerformanceService {
    /**
     * perform query analysis in provided database
     *
     * @param container database container
     * @param queries   for analysis
     * @return list of reports {@link Report}
     */
    List<Report> analyze(ContainerResolver container, List<String> queries);
}

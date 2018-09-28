package com.abc.performance.service;

import com.abc.performance.domain.Report;

import java.util.List;

public interface ContainerResolverService {
    /**
     * perform query analysis in containers
     * @param queries - sql queries
     * @return  list of reports {@link Report}
     */
    List<Report> resolve(List<String> queries);
}

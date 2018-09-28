package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Report;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@AllArgsConstructor
public class ContainerResolverServiceImpl implements ContainerResolverService {
    private final List<ContainerResolver> containers;
    private final PerformanceService performanceService;

    @Override
    public List<Report> resolve(List<String> queries) {
        log.info("analyze " + queries);
        return containers.stream()
                .parallel()
                .map(container -> getReports(container, queries))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private List<Report> getReports(ContainerResolver container, List<String> queries) {
        Lock lock = container.getLock();
        lock.lock();
        List<Report> reports;
        try {
            reports = performanceService.analyze(container, queries);
        } finally {
            lock.unlock();
        }
        return reports;
    }
}

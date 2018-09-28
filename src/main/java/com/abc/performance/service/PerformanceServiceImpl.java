package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Report;
import com.abc.performance.repository.ContainerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@AllArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {
    private final ContainerRepository repository;

    @Override
    public List<Report> analyze(ContainerResolver container, List<String> queries) {
        String jdbcUrl = getJdbcUrl(container.getContainer().getJdbcUrl(), container.getDatabaseName().name());
        return queries.stream()
                .map(query -> executeSingleQuery(query, jdbcUrl))
                .peek(res -> res.setDatabase(container.getContainer().getDockerImageName()))
                .collect(toList());
    }

    private Report executeSingleQuery(String query, String jdbcUrl) {
        Report report = new Report();
        report.setQuery(query);
        report.setTime(repository.executeQuery(query, jdbcUrl));
        report.setExplain(repository.explainSql(query, jdbcUrl));
        return report;
    }

    private String getJdbcUrl(String oldUrl, String name) {
        return oldUrl.replace("jdbc", "jdbc:tc")
                .concat(format("?/TC_INITSCRIPT=%s/init.sql", name));
    }
}



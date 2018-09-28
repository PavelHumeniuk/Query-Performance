package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.repository.ContainerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.util.List;

import static com.abc.performance.domain.Database.MYSQL;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PerformanceServiceImplTest {
    private final List<String> queries = singletonList("test");

    private PerformanceService service;

    @Mock
    private ContainerRepository repository;

    @Mock
    private ContainerResolver mySql;

    @Mock
    private JdbcDatabaseContainer container;

    @Before
    public void init() {
        service = new PerformanceServiceImpl(repository);
    }

    @Test
    public void shouldAnaliseQuery() {
        when(mySql.getContainer()).thenReturn(container);
        when(mySql.getDatabaseName()).thenReturn(MYSQL);
        when(container.getJdbcUrl()).thenReturn("test");

        service.analyze(mySql, queries);

        verify(repository, times(1)).executeQuery(anyString(), anyString());
        verify(repository, times(1)).explainSql(anyString(), anyString());
    }
}
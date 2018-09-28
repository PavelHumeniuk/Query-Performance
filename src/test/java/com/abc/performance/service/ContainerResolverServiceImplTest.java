package com.abc.performance.service;

import com.abc.performance.container.ContainerResolver;
import com.abc.performance.domain.Report;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContainerResolverServiceImplTest {
    private ContainerResolverService service;

    @Mock
    private ContainerResolver mySql;

    @Mock
    private ContainerResolver postgres;

    @Mock
    private PerformanceService performanceService;

    @Before
    public void init() {
        service = new ContainerResolverServiceImpl(asList(mySql, postgres), performanceService);
    }

    @Test
    public void shouldExecuteQueriesInAllContainers() {
        List<String> queries = singletonList("test");
        List<Report> reports = singletonList(new Report());
        Lock postgresLock = new ReentrantLock();
        Lock mySqlLock = new ReentrantLock();
        when(mySql.getLock()).thenReturn(mySqlLock);
        when(postgres.getLock()).thenReturn(postgresLock);
        when(performanceService.analyze(mySql, queries)).thenReturn(reports);
        when(performanceService.analyze(postgres, queries)).thenReturn(reports);

        List<Report> result = service.resolve(queries);

        assertEquals(result.size(), 2);
        verify(performanceService, times(1)).analyze(mySql, queries);
        verify(performanceService, times(1)).analyze(postgres, queries);
    }
}
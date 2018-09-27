package com.abc.performance.container;

import com.abc.performance.domain.Database;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MariaDBContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.abc.performance.domain.Database.MARIA;

@Getter
@Component
public class MariaDBResolver implements ContainerResolver {
    private final JdbcDatabaseContainer container = new MariaDBContainer();
    private final Database name = MARIA;
    private final Lock lock = new ReentrantLock();

    @PostConstruct
    private void start() {
        container.start();
    }

    @PreDestroy
    private void stop() {
        container.stop();
    }
}

package com.abc.performance.container;

import com.abc.performance.domain.Database;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.abc.performance.domain.Database.MYSQL;

@Getter
@Component
public class MySQLResolver implements ContainerResolver {
    private final JdbcDatabaseContainer container = new MySQLContainer();
    private final Database databaseName = MYSQL;
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

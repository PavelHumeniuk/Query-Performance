package com.abc.performance.container;

import com.abc.performance.domain.Database;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.util.concurrent.locks.Lock;

public interface ContainerResolver {

    Database getName();

    JdbcDatabaseContainer getContainer();

    Lock getLock();

}

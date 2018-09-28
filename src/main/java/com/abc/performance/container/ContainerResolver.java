package com.abc.performance.container;

import com.abc.performance.domain.Database;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.util.concurrent.locks.Lock;

/**
 * provides database container
 */
public interface ContainerResolver {

    /**
     * @return database name
     */
    Database getDatabaseName();

    /**
     * @return opened database container {@link JdbcDatabaseContainer}
     */
    JdbcDatabaseContainer getContainer();

    /**
     * @return lock for current container {@link Lock}
     */
    Lock getLock();

}

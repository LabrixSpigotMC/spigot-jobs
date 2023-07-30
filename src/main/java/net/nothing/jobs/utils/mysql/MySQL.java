package net.nothing.jobs.utils.mysql;

import java.sql.Connection;
import java.sql.ResultSet;

public interface  MySQL {

    /**
     * Executes an update
     * @param update the specified update
     */
    boolean update(final String update);

    /**
     * Executes an query
     * @param query the specified query
     */
    ResultSet query(final String query);

    /**
     * Dissolves the connection
     */
    void disconnect();

    /**
     * Returns the current connection
     * @return the mentioned connection
     */
    Connection getConnection();
}

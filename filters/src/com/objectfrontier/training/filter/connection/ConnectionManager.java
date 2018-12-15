package com.objectfrontier.training.filter.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.objectfrontier.training.filter.exception.AppException;
import com.objectfrontier.training.filter.exception.ExceptionCodes;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

    private static HikariDataSource dataSource;
    public static ThreadLocal<Connection> connectionThread = new ThreadLocal<>();

    static {

        String location = "/db.properties";
        HikariConfig config = new HikariConfig(location);
        config.setMaximumPoolSize(2);
        dataSource = new HikariDataSource(config);
    }

    public static void setConnection() {
        connectionThread.set(getConnection());
    }

    public static Connection getConnection() {

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new AppException(ExceptionCodes.SERVER_ERROR);
        }
    }

    public static void manageTransaction(Connection connection,boolean flag) {

        try {
            if (flag == true) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.close();
        } catch (SQLException e) {
            throw new AppException(ExceptionCodes.SERVER_ERROR);
        }
    }
}

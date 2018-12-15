package com.objectfrontier.training.java.services.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig("/db.properties");
        config.setMaximumPoolSize(3);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws Exception {

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    public void manageTransaction(Connection connection,boolean flag) throws SQLException {

        if(flag == true) {
            connection.commit();
        } else {
            connection.rollback();
        }
        connection.close();
    }
}

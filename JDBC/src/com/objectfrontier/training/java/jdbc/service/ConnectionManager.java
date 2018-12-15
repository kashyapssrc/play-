package com.objectfrontier.training.java.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://pc1620:3306/abhishek_c?useSSL=true";
        String user = "abhishek_c";
        String password = "demo";

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}

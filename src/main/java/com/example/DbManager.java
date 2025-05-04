package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Anatoliy Shikin
 */
public class DbManager {
    private static Connection connection;

    public static void init() {
        Properties props = new Properties();

        try (InputStream input = DbManager.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find liquibase.properties");
            }
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");
//            String driverClassName = props.getProperty("db.driverClassName");

//            Class.forName(driverClassName);

            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database connection: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Database connection is not initialized. Call DatabaseManager.init() first.");
        }
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}

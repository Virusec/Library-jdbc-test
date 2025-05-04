package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Anatoliy Shikin
 */
public class JdbcTest {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/jdbc_library_db";
        String user = "jdbcUser";
        String password = "qwerty";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS authors (id SERIAL PRIMARY KEY, first_name VARCHAR(100), last_name VARCHAR(100))");
                statement.executeUpdate("INSERT INTO authors (first_name, last_name) VALUES ('Fedor','Dostoevskiy')");
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

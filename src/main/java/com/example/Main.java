package com.example;

import java.sql.*;

/**
 * @author Anatoliy Shikin
 */
public class Main {
    public static void main(String[] args) {
        try {
            DbManager.init(); // Инициализация подключения
            Connection conn = DbManager.getConnection();

//            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM emp WHERE id = ?")) {
//                ps.setInt(1, 1);
//                try (ResultSet empRs = ps.executeQuery()) {
//                    // обрабатываем emp таблицу, если она есть
//                } catch (SQLException ignore) {
//                    System.out.println("emp table does not exist (expected in memory DB).");
//                }
//            }

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))");
                stmt.executeUpdate("INSERT INTO users (name) VALUES ('Bob')");
                stmt.executeUpdate("INSERT INTO users (name) VALUES ('Alice')");

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        System.out.println("User ID: " + id + ", Name: " + name);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        } finally {
            DbManager.close(); // Закрытие подключения
        }
    }
}
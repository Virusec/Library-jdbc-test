package com.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Anatoliy Shikin
 */
public class JdbcCheckVersion {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/jdbc_library_db";
        String user = "jdbcUser";
        String password = "qwerty";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData meta = conn.getMetaData();

            // Версия самого драйвера
            String driverName = meta.getDriverName();
            String driverVersion = meta.getDriverVersion();
            System.out.printf("JDBC Driver: %s v%s%n", driverName, driverVersion);

            // Версия JDBC API, которую поддерживает драйвер
            int major = meta.getJDBCMajorVersion();
            int minor = meta.getJDBCMinorVersion();
            System.out.printf("JDBC API Version: %d.%d%n", major, minor);
        }

    }
}

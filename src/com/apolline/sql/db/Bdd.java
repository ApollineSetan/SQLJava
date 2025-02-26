package com.apolline.sql.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd {
    static final String DB_URL = "jdbc:mysql://localhost/mydatabase";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    private static Connection connexion;
    static {
        try {
            connexion = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
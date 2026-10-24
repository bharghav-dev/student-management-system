package com.studentmanagement.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {

            if (connection != null && connection.isValid(5)) {
                System.out.println("MySQL connection successful!");
            }

        } catch (SQLException e) {
            System.out.println("MySQL connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}


package com.example.pattypus;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    protected static String DB = "db_midterm";
    protected static String IP = "192.168.100.155";
    protected static String PORT = "3306";
    protected static String USER = "renda";
    protected static String PASS = "123456789";

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB;
            conn = DriverManager.getConnection(connectionString, USER, PASS);
            Log.i("SUCCESS", "Connected to MySQL"); // Add this log message
        } catch (ClassNotFoundException | SQLException e) {
            Log.e("ERROR", "Connection error: " + e.getMessage()); // Modify this log message
        }
        return conn;
    }
}
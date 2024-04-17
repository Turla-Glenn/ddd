package com.example.pattypus;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {

    protected static String DB = "renda";

    protected static String IP = "172.16.1.203";

    protected static String PORT = "3306";

    protected static String USER = "renda";

    protected static String PASS = "123456789";

    public Connection CONN() {
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectiongString = "jdbc:mysql://"+IP+":"+PORT+"/"+DB;
            conn = DriverManager.getConnection(connectiongString,USER,PASS);
        }catch (Exception e) {
            Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }
}

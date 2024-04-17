package com.example.pattypus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class sql extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    ResultSet rs;

    String name, str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        connectionClass = new ConnectionClass();
        connect();

    }

    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                if (con == null) {
                    str = "Error in connection with MySQL Server";
                } else {
                    str = "Connected with MySQL Server";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });

    }

    public void btnClick(View view) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                String query = "SELECT * FROM users";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                StringBuilder builder = new StringBuilder("USER LIST\n");
                while (rs.next()) {
                    builder.append(rs.getString("uname")).append("\n");
                    builder.append(rs.getString("uname"));
                }
                name = builder.toString();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextView textView = findViewById(R.id.tv1);
                textView.setText(name);
            });
        });
    }
}

package com.example.pattypus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        connectionClass = new ConnectionClass();
    }

    public void btnClick(View view) {
        retrieveData();
    }

    public void insertData(View view) {
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        EditText ageEditText = findViewById(R.id.age);

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String ageText = ageEditText.getText().toString().trim();

        // Check if any required field is empty
        if (username.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
            Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert age to integer
        int age = Integer.parseInt(ageText);

        insertDataToDB(username, password, age);
    }

    public void updateData(View view) {
        EditText idEditText = findViewById(R.id.update_id);
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        EditText ageEditText = findViewById(R.id.age);

        String idText = idEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String ageText = ageEditText.getText().toString().trim();

        // Check if ID is empty
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please provide ID to update", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert ID to integer
        int id = Integer.parseInt(idText);

        // If both password and age are empty, prompt user to provide at least one field to update
        if (username.isEmpty() && password.isEmpty() && ageText.isEmpty()) {
            Toast.makeText(this, "Please provide at least one field to update", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert age to integer if provided
        int age = ageText.isEmpty() ? -1 : Integer.parseInt(ageText);

        updateDataInDB(id, username, password, age);
    }

    public void deleteData(View view) {
        EditText idEditText = findViewById(R.id.delete_id);
        String idText = idEditText.getText().toString().trim();

        // Check if ID is empty
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please provide ID to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert ID to integer
        int id = Integer.parseInt(idText);
        deleteDataFromDB(id);
    }

    private void retrieveData() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.getConnection();
                if (con == null) {
                    str = "Error in connection with MySQL Server";
                } else {
                    String query = "SELECT * FROM users";
                    PreparedStatement stmt = con.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    StringBuilder builder = new StringBuilder("USER LIST\n");
                    while (rs.next()) {
                        builder.append("ID: ").append(rs.getInt("Id")).append(", ");
                        builder.append("Username: ").append(rs.getString("uname")).append(", ");
                        builder.append("Password: ").append(rs.getString("pass")).append(", ");
                        builder.append("Age: ").append(rs.getInt("age")).append("\n");
                    }
                    str = builder.toString();
                }
            } catch (SQLException e) {
                str = "Error: " + e.getMessage();
            }
            runOnUiThread(() -> {
                TextView textView = findViewById(R.id.tv1);
                textView.setText(str);
            });
        });
    }

    private void insertDataToDB(String username, String password, int age) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.getConnection();
                if (con == null) {
                    str = "Error in connection with MySQL Server";
                } else {
                    String query = "INSERT INTO users (uname, pass, age) VALUES (?, ?, ?)";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setInt(3, age);
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        str = "Data inserted successfully";
                    } else {
                        str = "Failed to insert data";
                    }
                }
            } catch (SQLException e) {
                str = "Error: " + e.getMessage();
            }
            runOnUiThread(() -> {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateDataInDB(int id, String username, String password, int age) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.getConnection();
                if (con == null) {
                    str = "Error in connection with MySQL Server";
                } else {
                    StringBuilder queryBuilder = new StringBuilder("UPDATE users SET");
                    boolean isFirst = true;

                    if (!username.isEmpty()) {
                        queryBuilder.append(" uname = '").append(username).append("'");
                        isFirst = false;
                    }
                    if (!password.isEmpty()) {
                        if (!isFirst) {
                            queryBuilder.append(",");
                        }
                        queryBuilder.append(" pass = '").append(password).append("'");
                        isFirst = false;
                    }
                    if (age != -1) {
                        if (!isFirst) {
                            queryBuilder.append(",");
                        }
                        queryBuilder.append(" age = ").append(age);
                    }

                    queryBuilder.append(" WHERE Id = ").append(id);

                    PreparedStatement stmt = con.prepareStatement(queryBuilder.toString());
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        str = "Data updated successfully";
                    } else {
                        str = "Failed to update data";
                    }
                }
            } catch (SQLException e) {
                str = "Error: " + e.getMessage();
            }
            runOnUiThread(() -> {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void deleteDataFromDB(int id) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.getConnection();
                if (con == null) {
                    str = "Error in connection with MySQL Server";
                } else {
                    str = "Connected to MySQL Server";

                    String query = "DELETE FROM users WHERE Id = ?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setInt(1, id);
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        str = "Data deleted successfully";
                    } else {
                        str = "Failed to delete data";
                    }
                }
            } catch (SQLException e) {
                str = "Error: " + e.getMessage();
            }
            runOnUiThread(() -> {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }
}

package com.example.pattypus;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class
toast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void onClick(View v) {
        // Checking which button was clicked
        if (v.getId() == R.id.buttonName) {
            showToast("Name Button Clicked");
        } else if (v.getId() == R.id.buttonCourse) {
            showToast("Course Button Clicked");
        } else if (v.getId() == R.id.buttonAddress) {
            showToast("Address Button Clicked");
        } else if (v.getId() == R.id.buttonBirthday) {
            showToast("Birthday Button Clicked");
        } else if (v.getId() == R.id.buttonAge) {
            showToast("Age Button Clicked");
        } else if (v.getId() == R.id.buttonMessage) {
            showToast("Message Button Clicked");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

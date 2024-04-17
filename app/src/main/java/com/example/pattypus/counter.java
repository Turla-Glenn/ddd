package com.example.pattypus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class counter extends AppCompatActivity {
    private int counter = 0;
    private TextView textViewCounter;
    private Button buttonIncrement, buttonDecrement;
    private static final int MAX_VALUE = 20;
    private static final int MIN_VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        textViewCounter = findViewById(R.id.textViewCounter);
        buttonIncrement = findViewById(R.id.buttonIncrement);
        buttonDecrement = findViewById(R.id.buttonDecrement);

        updateCounterDisplay();
    }

    public void onIncrementClicked(View view) {
        if (counter < MAX_VALUE) {
            counter++;
            updateCounterDisplay();
        }
        updateButtonStates();
    }

    public void onDecrementClicked(View view) {
        if (counter > MIN_VALUE) {
            counter--;
            updateCounterDisplay();
        }
        updateButtonStates();
    }

    private void updateCounterDisplay() {
        textViewCounter.setText(String.valueOf(counter));
    }

    private void updateButtonStates() {
        buttonIncrement.setEnabled(counter < MAX_VALUE);
        buttonDecrement.setEnabled(counter > MIN_VALUE);
    }
}

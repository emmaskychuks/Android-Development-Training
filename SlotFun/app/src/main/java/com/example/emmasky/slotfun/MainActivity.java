package com.example.emmasky.slotfun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText amountInput;
    private TextView accountDisplay;
    private TextView reel1Display;
    private TextView ree21Display;
    private TextView reel3Display;
    private Button newGameButton;
    private Button setValueButton;
    private Button pullLeverButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

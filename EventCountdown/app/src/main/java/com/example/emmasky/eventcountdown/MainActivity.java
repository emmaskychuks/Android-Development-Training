package com.example.emmasky.eventcountdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Integer[] highestNotificationTimes = new Integer[] {1, 5, 10, 20, 30, 60, 90};
    private Spinner spinner;
    private final List<Integer> highestNotificationTimesList =
            new ArrayList<>(Arrays.asList(highestNotificationTimes));
    private ArrayAdapter<Integer> spinnerArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item,
                highestNotificationTimesList)
        {
            @Override
            public boolean isEnabled(int position)
            {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent)
            {
                return convertView;
            }
        };

        spinner = findViewById(R.id.spinner);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}

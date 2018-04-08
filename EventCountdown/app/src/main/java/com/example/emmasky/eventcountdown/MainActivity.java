package com.example.emmasky.eventcountdown;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Integer[] highestNotificationTimes = new Integer[] {1, 5, 10, 20, 30, 60, 90};
    private final int SECONDSTOMILLISECONDS = 1000;

    private int countdownTime;
    private int selectedHighestNotificationTimeIndex;
    private int selectedHighestNotificationTime;
    private Spinner spinner;
    private EditText countdownTimeEditText;
    private EditText eventMessageEditText;
    private Button setCountDownButton;
    private Button startCountDownButton;
    private Timer timer;
    private TimerTask timerTask;

    private final List<Integer> highestNotificationTimesList =
            new ArrayList<>(Arrays.asList(highestNotificationTimes));
    private ArrayAdapter<Integer> spinnerArrayAdapter;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!countdownTimeEditText.getText().toString().equals("")) {
                switch (v.getId()) {
                    case R.id.bt_set_count_down_button:
                        eventMessageEditText.setEnabled(true);
                        startCountDownButton.setEnabled(true);
                        if (isInputValid(Integer.parseInt(countdownTimeEditText.getText().toString()))) {
                            countdownTime = Integer.parseInt(countdownTimeEditText.getText().toString());
                            spinner.setEnabled(true);
                        }
                        else
                            countdownTimeEditText.requestFocus();
                        break;
                    case R.id.bt_start_count_down:
                        if(eventMessageEditText.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Please, type in a event message.",
                                    Toast.LENGTH_SHORT).show();
                            eventMessageEditText.requestFocus();
                        }
                        else {
                            new DisplayNotification().execute();
                        }
                        break;
                    default:
                        break;
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Please, type in a countdown value",
                        Toast.LENGTH_SHORT).show();
                countdownTimeEditText.requestFocus();
            }
        }
    };

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedHighestNotificationTime = Integer.parseInt(parent.getItemAtPosition(position).toString());
            selectedHighestNotificationTimeIndex = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            eventMessageEditText.setEnabled(false);
            startCountDownButton.setEnabled(false);
        }
    };

    private class DisplayNotification extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), R.string.count_down_started, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            startTimedTask();
            return null;
        }
    }

    private boolean isInputValid(int value)
    {
        if(value < 5 || value > 120) {
            countdownTimeEditText.setText("");
            Toast.makeText(getApplicationContext(),
                    "Please, type in a value between 5 and 120.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(value % 5 != 0)
        {
            countdownTimeEditText.setText("");
            Toast.makeText(getApplicationContext(),
                "Please, type in a value in increments of 5",
                Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private void resetWidgets()
    {
        spinner.setEnabled(false);
        eventMessageEditText.setEnabled(false);
        startCountDownButton.setEnabled(false);
    }

    private void startTimedTask()
    {
        while(countdownTime > 0)
        {
            /*if(countdownTime <= selectedHighestNotificationTime) {
                selectedHighestNotificationTime = highestNotificationTimes[--selectedHighestNotificationTimeIndex];
                countdownTime = countdownTime - selectedHighestNotificationTime;
            }*/
            countdownTime = countdownTime - selectedHighestNotificationTime;
            try {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        NotificationHelper.createNotification(getString(R.string.event_update),
                                String.valueOf(countdownTime) + " " + getString(R.string.current_countdown_message) + " " +
                                        eventMessageEditText.getText().toString(), getApplicationContext());
                    }
                };

                timer = new Timer(true);
                timer.schedule(timerTask, selectedHighestNotificationTime * SECONDSTOMILLISECONDS);
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        NotificationHelper.createNotification(getString(R.string.event_update), getString(R.string.count_down_ended) + " " + eventMessageEditText.getText().toString(),
                 this);
        timer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownTimeEditText = findViewById(R.id.et_count_down_value);
        eventMessageEditText = findViewById(R.id.et_event_message_value);
        setCountDownButton = findViewById(R.id.bt_set_count_down_button);
        startCountDownButton = findViewById(R.id.bt_start_count_down);
        spinner = findViewById(R.id.spinner);

        setCountDownButton.setOnClickListener(onClickListener);
        startCountDownButton.setOnClickListener(onClickListener);


        spinnerArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item,
                highestNotificationTimesList)
        {
            @Override
            public boolean isEnabled(int position)
            {
                if(highestNotificationTimes[position] > countdownTime)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(highestNotificationTimes[position] > countdownTime)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);

                return view;
            }
        };

        spinner.setOnItemSelectedListener(onItemSelectedListener);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        resetWidgets();
    }
}

/******************************************************************************************************************
 Name: Emmanuel Obi
 Instructor: Prof. John Baugh
 Class: CIS 436 - Mobile Design
 Project: Slot Machine
 *****************************************************************************************************************/

package com.example.emmasky.slotfun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity
{
    private EditText amountInput;
    private TextView accountDisplay;
    private TextView reel1Display;
    private TextView reel2Display;
    private TextView reel3Display;
    private Button newGameButton;
    private Button setValueButton;
    private Button pullLeverButton;
    private Random randomNumberGenerator = new Random();


    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bt_set_value:
                    setValueEffect();
                    break;
                case R.id.bt_pull_lever:
                    pullLeverEffect();
                    break;
                case R.id.bt_new_game:
                    resetGame();
                    break;
                default:
                    break;

            }
        }
    };
    private TextWatcher amountInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pullLeverButton.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!s.toString().equals("")) {
                int currentInput = Integer.parseInt(s.toString());
                if (currentInput < 100 || currentInput > 500)
                    Toast.makeText(getApplicationContext(),
                            "Hello, please type in a amount between 100 and 500", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void setValueEffect() {
        int currentInput = Integer.parseInt(amountInput.getText().toString());
        if (currentInput < 100 || currentInput > 500){
            Toast.makeText(getApplicationContext(),
                    "Hello, please type in an amount between 100 and 500", Toast.LENGTH_SHORT).show();
            amountInput.setText("");
        }
        else {
            setValueButton.setEnabled(false);
            amountInput.setEnabled(false);
            pullLeverButton.setEnabled(true);

            accountDisplay.setText("$" + amountInput.getText());
        }
    }
    private void pullLeverEffect()
    {
        if(pullLeverButton.isEnabled()) {
            int ree1 = randomNumberGenerator.nextInt(9) + 1;
            int reel2 = randomNumberGenerator.nextInt(9) + 1;
            int reel3 = randomNumberGenerator.nextInt(9) + 1;

            double currentAccountValue = Double.parseDouble(accountDisplay.getText().toString().substring(1)) - 5.0;
            accountDisplay.setText("$" + Double.toString(currentAccountValue));

            //Display random numbers to the reels
            reel1Display.setText(Integer.toString(ree1));
            reel2Display.setText(Integer.toString(reel2));
            reel3Display.setText(Integer.toString(reel3));

            checkForPlayerWinnings(ree1, reel2, reel3);
            checkBankAccount();
        }
    }
    private void checkForPlayerWinnings(int reel1, int reel2, int reel3)
    {
        double currentAccountValue = Double.parseDouble(accountDisplay.getText().toString().substring(1));
        if(reel1 == reel2 || reel1 == reel3 || reel2 == reel3) {
            accountDisplay.setText("$" + Double.toString(currentAccountValue + 10.0));
        }
        else if(reel1 == reel2 && reel1 == reel3 && reel2 == reel3)
        {
            if(reel1 < 5) {
                accountDisplay.setText("$" + Double.toString(currentAccountValue + 40.0));
            }
            else if(reel1 >= 5 && reel1 <= 8 ) {
                accountDisplay.setText("$" + Double.toString(currentAccountValue + 100.0));
            }
            else if(reel1 == 9)
                accountDisplay.setText("$" + Double.toString(currentAccountValue + 1000.0));
        }
        else {
            //Nothing much
        }

    }
    private void checkBankAccount()
    {
        if(!accountDisplay.getText().equals("$0")) {
            double currentAccount = Double.parseDouble(accountDisplay.getText().toString().substring(1));
            if (currentAccount <= 0.0) {
                resetGame();
                pullLeverButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Unfortunately, you've lost all your money. The game will now restart",
                        Toast.LENGTH_LONG).show();
            } else if (currentAccount <= 4.0) {
                Toast.makeText(getApplicationContext(), "Unfortunately, you've don't have sufficient funds to keep playing." +
                        "Press 'NEW GAME' to restart", Toast.LENGTH_LONG).show();
                pullLeverButton.setEnabled(false);
            } else if (currentAccount >= 1000.0) {
                resetGame();
                Toast.makeText(getApplicationContext(), "Congrats!, you've cleared the slot machine. The game will now restart",
                        Toast.LENGTH_LONG).show();
                pullLeverButton.setEnabled(false);
            }
        }
    }
    private void resetGame()
    {
        accountDisplay.setText("$0");
        pullLeverButton.setEnabled(false);
        amountInput.setText("");
        amountInput.setEnabled(true);
        reel3Display.setText("");
        reel2Display.setText("");
        reel1Display.setText("");

        if(!setValueButton.isEnabled())
            setValueButton.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountInput = (EditText) findViewById(R.id.et_amount_input);
        accountDisplay = (TextView) findViewById(R.id.tv_bank_account_display);
        reel1Display = (TextView) findViewById(R.id.tv_reel1_display);
        reel2Display = (TextView) findViewById(R.id.tv_reel2_display);
        reel3Display = (TextView) findViewById(R.id.tv_reel3_display);
        newGameButton = (Button) findViewById(R.id.bt_new_game);
        setValueButton = (Button) findViewById(R.id.bt_set_value);
        pullLeverButton = (Button) findViewById(R.id.bt_pull_lever);

        newGameButton.setOnClickListener(buttonListener);
        setValueButton.setOnClickListener(buttonListener);
        pullLeverButton.setOnClickListener(buttonListener);
        pullLeverButton.setEnabled(false);
        amountInput.addTextChangedListener(amountInputTextWatcher);
    }
}



package com.example.pokercalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        double profit = 0;
        int numberOfRows = preferences.getInt("rows", 0);
        for (int new_rows = 0; new_rows < numberOfRows+1; new_rows++) {
            String retrievedCashOut = preferences.getString("cashOut" + new_rows, "0");
            String retrievedBuyIn = preferences.getString("buyIn" + new_rows, "0");

            int cashOutValue = Integer.parseInt(retrievedCashOut);
            int buyInValue = Integer.parseInt(retrievedBuyIn);
            profit += cashOutValue - buyInValue;

            Log.d("profit", Double.toString(profit));
            Log.d("row", Integer.toString(new_rows));
        }

        TextView totalProfit = findViewById(R.id.totalProfit);
        totalProfit.setText("Career: " + Double.toString(profit));

        populateSessionData(preferences,linearLayout);
        }
    public void clearAllSessions(View view){
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        double profit = 0;
    }

    public void new_Main(View view) {
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        populateSessionData(preferences, linearLayout);

        double profit = 0;
        int numberOfRows = preferences.getInt("rows", 0);
        for (int new_rows = 0; new_rows < numberOfRows+1; new_rows++) {
            String retrievedCashOut = preferences.getString("cashOut" + new_rows, "0");
            String retrievedBuyIn = preferences.getString("buyIn" + new_rows, "0");

            int cashOutValue = Integer.parseInt(retrievedCashOut);
            int buyInValue = Integer.parseInt(retrievedBuyIn);
            profit += cashOutValue - buyInValue;

            Log.d("profit", Double.toString(profit));
            Log.d("row", Integer.toString(new_rows));
        }

        TextView totalProfit = findViewById(R.id.totalProfit);
        totalProfit.setText("Career: " + Double.toString(profit));
    }

    private void populateSessionData(SharedPreferences preferences, LinearLayout linearLayout) {
        int numberOfRows = preferences.getInt("rows", 0);
        for (int i = 0; i < numberOfRows; i++) {
            int rowCount = i + 1;
            addSessionTextView(linearLayout, preferences, rowCount);
        }
    }

    private void addSessionTextView(LinearLayout linearLayout, SharedPreferences preferences, int rowCount) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        String sessionDate = preferences.getString("date" + rowCount, null);
        String smallBlind = preferences.getString("smallBlind" + rowCount, null);
        String bigBlind = preferences.getString("bigBlind" + rowCount, null);
        String buyInAmount = preferences.getString("buyIn" + rowCount, null);
        String cashOutAmount = preferences.getString("cashOut" + rowCount, null);
        int profit = Integer.parseInt(cashOutAmount) - Integer.parseInt(buyInAmount);

        textView.setText(sessionDate + " - $" + profit + " at " + smallBlind + "/" + bigBlind);
        //Green if profit, red if not, 0 = red still
        textView.setBackgroundColor(profit > 0 ? 0xff66ff66 : 0xfff00000);
        textView.setPadding(20, 20, 20, 20);

        linearLayout.addView(textView);
    }

    private boolean validateFields() {
        return validateDate(findViewById(R.id.Date)) //Date
                && validateTime(findViewById(R.id.StartTime)) //Session Start
                && validateTime(findViewById(R.id.EndTime)) //Session End
                && validateTextNotNull(findViewById(R.id.SmallBlind)) //Small Blind
                && validateTextNotNull(findViewById(R.id.BigBlind)) //Big Blind
                && validateTextNotNull(findViewById(R.id.Buyin)) //BuyInText
                && validateTextNotNull(findViewById(R.id.Cashout)); //Cash out
    }

    private boolean validateDate(EditText editText, int... positions) {
        String slash = "/";
        if (editText.getText().length() != 10) {
            editText.setError("Your Input is Invalid");
            return false;
        }

        if (editText.getText().charAt(2) != slash.charAt(0) || editText.getText().charAt(5) != slash.charAt(0)) {
            editText.setError("Your Input is Invalid");
            return false;
        }
        return true;
    }
    private boolean validateTime(EditText editText) {
        int length = editText.getText().length();
        //Too long or too short
        if (length < 4 || length > 5) {
            editText.setError("Your Input is Invalid");
            return false;
        }

        String text = editText.getText().toString();
        String colon = ":";
        //If length if 4 then check for colon at index 2 ex 3:08
        //Length being 5 ex 10:38
        int pos = length == 4 ? 1 : 2;

        //Check that proper index is a colon and all others are not
        if (text.charAt(pos) != colon.charAt(0) || text.substring(0, pos).contains(colon) || text.substring(pos + 1).contains(colon)) {
            editText.setError("Your Input is Invalid");
            return false;
        }
        return true;
    }

    private boolean validateTextNotNull(EditText editText) {
        if (editText.getText().length() < 1) {
            editText.setError("Your Input is Invalid");
            return false;
        }
        return true;
    }

    public void add_sesh(View view){
        setContentView(R.layout.add_session);

        EditText editDate = findViewById(R.id.Date);
        EditText sessionStart = findViewById(R.id.StartTime);
        EditText sessionEnd = findViewById(R.id.EndTime);
        EditText smallBlindText = findViewById(R.id.SmallBlind);
        EditText bigBlindText = findViewById(R.id.BigBlind);
        EditText buyInText = findViewById(R.id.Buyin);
        EditText cashOutText = findViewById(R.id.Cashout);

        Button btn = findViewById(R.id.addInfoButton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        btn.setOnClickListener(v -> {
            if(validateFields()) {
                //Get Data

                //REMOVE AND DIRECTLY PUT IN BELOW!!!!!!!!
                String date = editDate.getText().toString();
                String startTime = sessionStart.getText().toString();
                String endTime = sessionEnd.getText().toString();
                String smallBlind = smallBlindText.getText().toString();
                String bigBlind = bigBlindText.getText().toString();
                String buyIn = buyInText.getText().toString();
                String cashOut = cashOutText.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

                Log.i(editDate.getText().toString(), "Testing");

                //Increase number of entries by 1
                Integer new_rows = preferences.getInt("rows", 0) + 1;
                editor.putInt("rows", new_rows);

                //Put in new data
                editor.putString("date" + new_rows.toString(), date);
                editor.putString("startTime" + new_rows.toString(), startTime);
                editor.putString("endTime" + new_rows.toString(), endTime);
                editor.putString("smallBlind" + new_rows.toString(), smallBlind);
                editor.putString("bigBlind" + new_rows.toString(), bigBlind);
                editor.putString("buyIn" + new_rows.toString(), buyIn);
                editor.putString("cashOut" + new_rows.toString(), cashOut);
                editor.apply();

                submitInfo(view);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void submitInfo(View view){
        setContentView(R.layout.sesh_info);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String numRows = Integer.toString(preferences.getInt("rows", 0));
        Log.d("rows", numRows);

        //BlindsDisplay
        String small = preferences.getString("smallBlind" + numRows , null);
        String big = preferences.getString("bigBlind" + numRows , null);
        TextView blindsDisplay = findViewById(R.id.Blinds);
        blindsDisplay.setText(small + "/" + big);

        //DateDisplay
        String sessionDate = preferences.getString("date" + numRows , null);
        TextView dateDisplay = findViewById(R.id.date);
        dateDisplay.setText(sessionDate);

        //ProfitDisplay
        String buyInAmount = preferences.getString("buyIn" + numRows , null);
        String cashOutAmount = preferences.getString("cashOut" + numRows , null);

        double profit = Integer.parseInt(cashOutAmount) - Integer.parseInt(buyInAmount);
        TextView profitDisplay = findViewById(R.id.profit);
        profitDisplay.setText(Double.toString(profit));

        //ProfitDescription Display
        TextView profitDescriptionDisplay = findViewById(R.id.BuyIn);
        String textInfo = "In for " + buyInAmount + " ,Out with " + cashOutAmount;
        profitDescriptionDisplay.setText(textInfo);

        //BigBlinds Won
        TextView bigBlindsWon = findViewById(R.id.Bigs);
        String bigBlinds = preferences.getString("bigBlind" + numRows , null);
        double bigsProfit = profit / Double.parseDouble(bigBlinds);
        String bigsWon = Double.toString(Math.round(bigsProfit * 100) / 100) + " Bigs";
        bigBlindsWon.setText(bigsWon);

        //Blinds per hour
        String startTimeCalc = preferences.getString("startTime" + numRows , null);
        int hoursStart = 0,minutesStart = 0;
        //Normal time ex. 10:30
        if(startTimeCalc.length() == 5){
            hoursStart = Integer.parseInt(startTimeCalc.substring(0,2));
            minutesStart = Integer.parseInt(startTimeCalc.substring(3,5));
        }else{
            //edge case ex. 3:04
            hoursStart = Integer.parseInt(startTimeCalc.substring(0,1));
            minutesStart = Integer.parseInt(startTimeCalc.substring(2,4));
        }
        String endTimeCalc = preferences.getString("endTime" + numRows , null);

        int hoursEnd = 0,minutesEnd = 0;
        //Normal time ex. 10:30
        if(endTimeCalc.length() == 5){
            hoursEnd = Integer.parseInt(endTimeCalc.substring(0,2));
            minutesEnd = Integer.parseInt(endTimeCalc.substring(3,5));
        }else{
            //edge case ex. 3:04
            hoursEnd = Integer.parseInt(endTimeCalc.substring(0,1));
            minutesEnd = Integer.parseInt(endTimeCalc.substring(2,4));
        }

        // X Blinds per hour
        if(hoursEnd < hoursStart){
            hoursEnd += 24;
        }
        if(minutesEnd < minutesStart){
            minutesEnd += 60;
            hoursEnd -= 1;
        }
        //Hours played with decimal
        double calculate = (hoursEnd-hoursStart) + ((minutesEnd - minutesStart)/60.00);
        Log.d("calculate", String.valueOf(calculate));
        double blindsPerHourCalc = (bigsProfit / calculate);
        Log.d("blindsPerHourCalc", String.valueOf(blindsPerHourCalc));
        TextView blindsPerHour = findViewById(R.id.blindPerHour);
        blindsPerHour.setText(Integer.toString((int) blindsPerHourCalc) + " Blinds per hour");
    }

}
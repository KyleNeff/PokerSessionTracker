package com.example.pokercalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear().commit();

        Integer numRows = preferences.getInt("rows", 0);
        String received_data = preferences.getString("date" + numRows , null);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        String numberRows = Integer.toString(preferences.getInt("rows", 0));

        for(int i = 0; i < Integer.parseInt(numberRows); i ++){
            int rowCount = i + 1;
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            //Date, blind size, profit
            String sessionDate = preferences.getString("date" + rowCount , null);
            String small = preferences.getString("smallBlind" + rowCount , null);
            String big = preferences.getString("bigBlind" + rowCount , null);
            String buyInAmount = preferences.getString("buyIn" + rowCount , null);
            String cashOutAmount = preferences.getString("cashOut" + rowCount , null);
            int profit = Integer.parseInt(cashOutAmount) - Integer.parseInt(buyInAmount);
            if(profit > 0){
                textView1.setBackgroundColor(0xff66ff66);
            }else{
                textView1.setBackgroundColor(0xfff00000);
            }
            textView1.setText(sessionDate + " - $" + profit + " at " + small + "/" + big);

            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(textView1);
        }


    }

    //Functions to load correct Page on Button press
    public void clearAllSessions(View view){
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();

        String numRows = Integer.toString(preferences.getInt("rows", 0));
        Log.d("rows", numRows);

    }
    @SuppressLint("SetTextI18n")
    public void new_Main(View view){
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        String numberRows = Integer.toString(preferences.getInt("rows", 0));

        for(int i = 0; i < Integer.parseInt(numberRows); i ++){
            int rowCount = i + 1;
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            //Date, blind size, profit, big blinds per hour
            String sessionDate = preferences.getString("date" + rowCount , null);
            String small = preferences.getString("smallBlind" + rowCount , null);
            String big = preferences.getString("bigBlind" + rowCount , null);
            String buyInAmount = preferences.getString("buyIn" + rowCount , null);
            String cashOutAmount = preferences.getString("cashOut" + rowCount , null);
            int profit = Integer.parseInt(cashOutAmount) - Integer.parseInt(buyInAmount);

            textView1.setText(sessionDate + " - $" + profit + " at " + small + "/" + big);
            if(profit > 0){
                textView1.setBackgroundColor(0xff66ff66);
            }else{
                textView1.setBackgroundColor(0xfff00000);;
            }
            textView1.setPadding(20, 20, 20, 20);
            linearLayout.addView(textView1);
        }
    }

    private boolean validateFields() {

        EditText editDate = findViewById(R.id.editTextDate1);
        EditText sessionStart = findViewById(R.id.editTextTime);
        EditText sessionEnd = findViewById(R.id.editTextTime2);
        EditText smallBlindText = findViewById(R.id.editTextNumber3);
        EditText bigBlindText = findViewById(R.id.editTextNumber4);
        EditText buyInText = findViewById(R.id.editTextNumber);
        EditText cashOutText = findViewById(R.id.editTextNumber2);

        if (editDate.getText().length() != 10) {
            editDate.setError("Your Input is Invalid");
            return false;
        }else if (!(editDate.getText().toString().substring(2,3)).equals("/")) {
            editDate.setError("Your Input is Invalid");
            return false;
        }else if (!(editDate.getText().toString().substring(5,6)).equals("/")) {
            editDate.setError("Your Input is Invalid");
            return false;
        }else if (sessionStart.getText().toString().length() > 5 || sessionStart.getText().toString().length() < 4) {
            sessionStart.setError("Your Input is Invalid");
            return false;
        }else if (sessionStart.getText().toString().length() == 4 && ((!(sessionStart.getText().toString().substring(1,2)).equals(":")) || (sessionStart.getText().toString().substring(0,1)).contains(":") || (sessionStart.getText().toString().substring(2,4)).contains(":"))) {
            sessionStart.setError("Your Input is Invalid");
            return false;
        }else if (sessionStart.getText().toString().length() == 5 && (!(sessionStart.getText().toString().substring(2,3)).equals(":")  || (sessionStart.getText().toString().substring(0,2)).contains(":") || (sessionStart.getText().toString().substring(3,5)).contains(":"))) {
            sessionStart.setError("Your Input is Invalid");
            return false;
        }else if (sessionEnd.getText().toString().length() > 5 || sessionEnd.getText().toString().length() < 4) {
            sessionEnd.setError("Your Input is Invalid");
            return false;
        }else if (sessionEnd.getText().toString().length() == 4 && !(sessionEnd.getText().toString().substring(1,2)).equals(":")) {
            sessionEnd.setError("Your Input is Invalid");
            return false;
        }else if (sessionEnd.getText().toString().length() == 5 && !(sessionEnd.getText().toString().substring(2,3)).equals(":")) {
            sessionEnd.setError("Your Input is Invalid");
            return false;
        } else if (smallBlindText.getText().length() < 1) {
            smallBlindText.setError("Your Input is Invalid");
            return false;
        } else if (bigBlindText.getText().length() < 1) {
            bigBlindText.setError("Your Input is Invalid");
            return false;
        } else if (buyInText.getText().length() < 1) {
            buyInText.setError("Your Input is Invalid");
            return false;
        } else if (cashOutText.getText().length() < 1) {
            cashOutText.setError("Your Input is Invalid");
            return false;
        } else {
            return true;
        }
    }

    public void add_sesh(View view){
        setContentView(R.layout.add_session);

        EditText editDate = findViewById(R.id.editTextDate1);
        EditText sessionStart = findViewById(R.id.editTextTime);
        EditText sessionEnd = findViewById(R.id.editTextTime2);
        EditText smallBlindText = findViewById(R.id.editTextNumber3);
        EditText bigBlindText = findViewById(R.id.editTextNumber4);
        EditText buyInText = findViewById(R.id.editTextNumber);
        EditText cashOutText = findViewById(R.id.editTextNumber2);

        Button btn = findViewById(R.id.addInfoButton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        btn.setOnClickListener(v -> {
            if(validateFields()) {
                //Get Data
                String date = editDate.getText().toString();
                String startTime = sessionStart.getText().toString();
                String endTime = sessionEnd.getText().toString();
                String smallBlind = smallBlindText.getText().toString();
                String bigBlind = bigBlindText.getText().toString();
                String buyIn = buyInText.getText().toString();
                String cashOut = cashOutText.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

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
        TextView blindsDisplay = findViewById(R.id.textView4);
        blindsDisplay.setText(small + "/" + big);

        //DateDisplay
        String sessionDate = preferences.getString("date" + numRows , null);
        TextView dateDisplay = findViewById(R.id.textView5);
        dateDisplay.setText(sessionDate);

        //ProfitDisplay
        String buyInAmount = preferences.getString("buyIn" + numRows , null);
        String cashOutAmount = preferences.getString("cashOut" + numRows , null);

        int profit = Integer.parseInt(cashOutAmount) - Integer.parseInt(buyInAmount);
        TextView profitDisplay = findViewById(R.id.textView6);
        profitDisplay.setText(Integer.toString(profit));

        //ProfitDescription Display
        TextView profitDescriptionDisplay = findViewById(R.id.textView8);
        String textInfo = "Bought in for " + buyInAmount + " , out with " + cashOutAmount;
        profitDescriptionDisplay.setText(textInfo);

        //BigBlinds Won
        TextView bigBlindsWon = findViewById(R.id.textView9);
        String bigBlinds = preferences.getString("bigBlind" + numRows , null);
        int bigsProfit = profit / Integer.parseInt(bigBlinds);
        String bigsWon = Integer.toString(bigsProfit) + " Bigs";
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
        TextView blindsPerHour = findViewById(R.id.textView10);
        blindsPerHour.setText(Integer.toString((int) blindsPerHourCalc) + " Blinds per hour");
    }

}
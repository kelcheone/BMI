package com.example.bmicalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.time.*;

public class MainActivity<Int, val> extends AppCompatActivity {
    //getting the global variables
    Button button;
    String greeting ;
    TextView rString;
    EditText rName, rHeight, rWeight;
    int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Creating a new action bar
        //adding ActionBar and an Exit Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setCustomView(R.layout.custom_action_bar);
        View view = Objects.requireNonNull(getSupportActionBar()).getCustomView();
//Exit Button
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(this::onClickClose);//calling the onclose() functin
//Greeting using time
        if (currentTime > 0 && currentTime < 12){//from midnight to noon
            greeting = "Morning";
        }else if (currentTime > 12 && currentTime <= 15){//from noon to 3pm
            greeting = "Afternoon";
        }else if (currentTime > 15 && currentTime <= 21){//from 3pm to 9pm
            greeting = "Evening";
        }else{
            greeting = " Night";//from 9pm to midnight
        }
//This Gets The name from the user and  Sets the greeting message
        String greeting_d = getString(R.string.greeting, greeting);
        rString = findViewById(R.id.greeting);
        rString.setText(greeting_d);
//Getting the form
        button = findViewById(R.id.calculate);
        rName = findViewById(R.id.name);
        rWeight = findViewById(R.id.weight);
        rHeight = findViewById(R.id.heigt);
//This listens when the user is inserting the text and calls the textWatcher function to check the text
        rName.addTextChangedListener(textWatcher);
        rHeight.addTextChangedListener(textWatcher);
        rWeight.addTextChangedListener(textWatcher);
//Listens when the user clicks the button
        button.setOnClickListener(this::onClickCalculate);
    }
//Method to make sure text is entered
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String cName = rName.getText().toString().trim();//Makes sure the text is not empty
            String cHeight = rHeight.getText().toString().trim();
            String cWeight = rWeight.getText().toString().trim();


//The button is enabled only if the textFields are filled
            button.setEnabled(!cName.isEmpty() && ! cHeight.isEmpty() && !cWeight.isEmpty() );

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
//Function that calculates the BMI
    private void onClickCalculate(View v){

        String text = rName.getText().toString();//Gets the name from User
        double height = Double.parseDouble(rHeight.getText().toString());//Gets height from user and convert it into double
        double weight = Double.parseDouble(rWeight.getText().toString());//Gets height from user and convert it into double
        double expHeight = Math.pow(height, 2);//Squaring the height
        double lResult =  (double)(weight/expHeight);
        double result = Math.round(lResult *100.0)/100.0;
        if(height <= 0 || weight <=0){
            button.setEnabled(false);
            //You cant calculate zero values
            Toast.makeText(MainActivity.this, "Value cannot be zero", Toast.LENGTH_SHORT).show();
        }else {
//Linking Main Activity to Results
            Intent intent = new Intent(MainActivity.this, Results.class);
            String sResult = String.valueOf(result);

            String status;
            if (lResult <= 18.5) {
                status = "Underweight";
            } else if (lResult > 18.5 && lResult < 25) {
                status = "Normal";
            } else if(lResult>= 25 && lResult < 30 ){
                status = "Overweight";
            }else{
                status = "Obese";
            }
//Storing the results in Bundle
            Bundle extras = new Bundle();
            extras.putString("BMI", sResult);
            extras.putString("Name", text);
            extras.putString("Status", status);
            extras.putString("Time", greeting);
//Parse the Results into the results activity
            intent.putExtras(extras);
            //Allows user to exit the current Activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

    }
    private  void  onClickClose(View v){
        finish();
    }
}
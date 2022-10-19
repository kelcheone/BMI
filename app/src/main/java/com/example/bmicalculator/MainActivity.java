package com.example.bmicalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.time.*;

public class MainActivity extends AppCompatActivity {

    Button button;
    Date currentTime = Calendar.getInstance().getTime();
//    DateTime dt =



//    public static final String BMI = "BMI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setCustomView(R.layout.custom_action_bar);
        View view = Objects.requireNonNull(getSupportActionBar()).getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        ImageButton imageButton2= (ImageButton)view.findViewById(R.id.action_bar_forward);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Forward Button is clicked",Toast.LENGTH_LONG).show();
            }
        });
        button = findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText t = findViewById(R.id.name);
                String text = t.getText().toString();
                EditText rHeight = findViewById(R.id.heigt);
                double height = Double.parseDouble(rHeight.getText().toString());
                EditText rWeight = findViewById(R.id.weight);
                double weight = Double.parseDouble(rWeight.getText().toString());

                double expHeight = Math.pow(height, 2);
                double lResult =  (double)(weight/expHeight);
                double result = Math.round(lResult *100.0)/100.0;

                Log.d("My name is ", text);
                Log.d("Your weight is : ", Double.toString(weight));
                Log.d("Your height is : ", Double.toString(height));
                Log.d("Your BMI is : ", String.valueOf(result));

                Intent intent = new Intent(MainActivity.this, Results.class);
                String sResult = String.valueOf(result);

                String status;
                if (lResult <= 18.5){
                    status = "Underweight";
                }else if (lResult > 18.5 && lResult < 25){
                    status= "Normal";
                }else{
                    status = "Overweight";
                }

                Bundle extras = new Bundle();
                extras.putString("BMI", sResult);
                extras.putString("Name", text);
                extras.putString("Status", status);

                intent.putExtras(extras);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });

    }

    public void calculate (View v){


    }
}
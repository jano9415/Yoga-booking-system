package com.example.yoga_booking_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButton , signupButton , yogaTrainersBtn, yogaTypesBtn, trainingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        yogaTrainersBtn = findViewById(R.id.yogaTrainersBtn);
        yogaTypesBtn = findViewById(R.id.yogaTypesBtn);
        trainingsBtn = findViewById(R.id.trainingsBtn);

        //Bejelentkezés gomb megnyomása
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
            }
        });

        //Regisztráció gomb megnyomása
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SignupActivity.class));
            }
        });

        //Jóga oktatók gomb megnyomása
        yogaTrainersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , YogatrainersActivity.class).putExtra("id" , 0));

            }
        });

        //Jóga típusok gomb megnyomása
        yogaTypesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , YogatypesActivity.class).putExtra("id" , 0));

            }
        });

        //Edzések gomb megnyomása
        trainingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , TrainingsActivity.class));

            }
        });
    }
}
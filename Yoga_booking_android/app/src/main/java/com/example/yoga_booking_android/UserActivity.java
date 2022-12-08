package com.example.yoga_booking_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.User;
import com.example.yoga_booking_android.remote.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private Button logoutButton, yogaTrainersBtnOnUserAc, yogaTypesBtnOnUserAc, trainingsBtnOnUserAc, purchaseYogapassesActivityBtn,
                    cancelTrainingActivity;
    private User user;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        userId = getIntent().getLongExtra("id" , 0);
        String roleName = getIntent().getStringExtra("rolename");


        logoutButton = findViewById(R.id.logoutButton);
        yogaTrainersBtnOnUserAc = findViewById(R.id.yogaTrainersBtnOnUserAc);
        yogaTypesBtnOnUserAc = findViewById(R.id.yogaTypesBtnOnUserAc);
        trainingsBtnOnUserAc = findViewById(R.id.trainingsBtnOnUserAc);
        purchaseYogapassesActivityBtn = findViewById(R.id.purchaseYogapassesActivityBtn);
        cancelTrainingActivity = findViewById(R.id.cancelTrainingActivity);

        //Kérés küldése a szervernek
        //Felhasználó keresése id szerint
        Call<User> call = Network.getInstance().getApiService().findUserById(userId);

        //Válasz a szervertől
        //A válasz egy felhasználó objektum
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //Oktatók gomb megnyomása
        yogaTrainersBtnOnUserAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , YogatrainersActivity.class).putExtra("id" , userId));
            }
        });

        //Óratípusok gomb megnyomása
        yogaTypesBtnOnUserAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , YogatypesActivity.class).putExtra("id" , userId));
            }
        });

        //Órarend gomb megnyomása
        trainingsBtnOnUserAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , TrainingsForUserActivity.class).putExtra("id" , userId));

            }
        });

        //Bérlet vásárlása gomb megnyomása
        purchaseYogapassesActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , PurchaseYogapassActivity.class).putExtra("id" , userId));
            }
        });

        //Óra lemondása gomb megnyomása
        cancelTrainingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , CancelTrainingActivity.class).putExtra("id" , userId));
            }
        });

        //Kijelentkezés gomb megnyomása
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this , MainActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext() , "Nem tudsz visszalépni!" , Toast.LENGTH_LONG).show();

    }
}
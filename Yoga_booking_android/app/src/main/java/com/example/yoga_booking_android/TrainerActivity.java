package com.example.yoga_booking_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Yogatrainer;
import com.example.yoga_booking_android.remote.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerActivity extends AppCompatActivity {

    private Button logoutBtnOnTrainerAc, addNewTrainingActivityBtn, addNewYogatypeActivityBtn;
    private Yogatrainer yogatrainer;
    private Context context;

    private long trainerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        getSupportActionBar().hide();

        context = this;

        logoutBtnOnTrainerAc = findViewById(R.id.logoutBtnOnTrainerAc);
        addNewTrainingActivityBtn = findViewById(R.id.addNewTrainingActivityBtn);
        addNewYogatypeActivityBtn = findViewById(R.id.addNewYogatypeActivityBtn);

        trainerId = getIntent().getLongExtra("id" , 0);
        String roleName = getIntent().getStringExtra("rolename");

        //Kérés küldése a szervernek
        //Jóga oktató keresése id szerint
        Call<Yogatrainer> call = Network.getInstance().getApiService().findYogatrainerById(trainerId);

        //Válasz a szervertől
        //A válasz egy jóga oktató objektum
        call.enqueue(new Callback<Yogatrainer>() {
            @Override
            public void onResponse(Call<Yogatrainer> call, Response<Yogatrainer> response) {
                yogatrainer = response.body();
            }

            @Override
            public void onFailure(Call<Yogatrainer> call, Throwable t) {

            }
        });



        //Edzés hozzáadása gomb megnyomása
        addNewTrainingActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainerActivity.this , AddNewTrainingActivity.class)
                        .putExtra("id" , trainerId));

            }
        });

        //Új jóga típus hozzáadása gomb megnyomása
        addNewYogatypeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , AddNewYogatypeActivity.class).putExtra("id" , trainerId));
            }
        });


        //Kijelentkezés gomb megnyomása
        logoutBtnOnTrainerAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , MainActivity.class));
            }
        });
    }
}
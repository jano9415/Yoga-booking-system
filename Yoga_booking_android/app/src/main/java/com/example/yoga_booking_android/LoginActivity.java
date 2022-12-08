package com.example.yoga_booking_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.User;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailaddressEditTextOnLoginActivity, passwordEditTextOnLoginActivity;
    private Button loginButtonOnLoginActivity;
    private FloatingActionButton backActionButton9;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        context = this;

        emailaddressEditTextOnLoginActivity = findViewById(R.id.emailaddressEditTextOnLoginActivity);
        passwordEditTextOnLoginActivity = findViewById(R.id.passwordEditTextOnLoginActivity);
        loginButtonOnLoginActivity = findViewById(R.id.loginButtonOnLoginActivity);
        backActionButton9 = findViewById(R.id.backActionButton9);

        //Bejelentkezés gomb megnyomása
        loginButtonOnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }
        });

        //Visszatérés a főoldalra
        backActionButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , MainActivity.class));
            }
        });

    }

    private void loginUser(){
        String emailAddress = emailaddressEditTextOnLoginActivity.getText().toString().trim();
        String password = passwordEditTextOnLoginActivity.getText().toString().trim();


        if(validateDatas(emailAddress,password)){

            //Bejelentkezés kérés küldése a szervernek.
            //A request egy User objektum.
            Call<User> call = Network.getInstance()
                    .getApiService()
                    .login(emailAddress,password);




            //Szervertől jövő válasz feldolgozása.
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if(response.body().getRole().getRoleName().equals("user")){
                        Bundle extras = new Bundle();
                        extras.putLong("id" , response.body().getId());
                        extras.putString("rolename" , response.body().getRole().getRoleName());

                        startActivity(new Intent(LoginActivity.this , UserActivity.class).putExtras(extras));

                    }
                    if(response.body().getRole().getRoleName().equals("trainer")){
                        Bundle extras = new Bundle();
                        extras.putLong("id" , response.body().getId());
                        extras.putString("rolename" , response.body().getRole().getRoleName());

                        startActivity(new Intent(LoginActivity.this , TrainerActivity.class).putExtras(extras));

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this , "Hibás email cím vagy jelszó!", Toast.LENGTH_LONG).show();

                }
            });

        }


    }

    //Megadott adatok validációja.
    //Az email cím mező nem lehet üres.
    //A jelszó mező nem lehet üres.
    private boolean validateDatas(String emailAddress , String password){
        if(emailAddress.isEmpty()){
            emailaddressEditTextOnLoginActivity.setError("Add meg az email címed!");
            emailaddressEditTextOnLoginActivity.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            passwordEditTextOnLoginActivity.setError("Add meg a jelszót!");
            passwordEditTextOnLoginActivity.requestFocus();
            return false;
        }

        return true;
    }
}
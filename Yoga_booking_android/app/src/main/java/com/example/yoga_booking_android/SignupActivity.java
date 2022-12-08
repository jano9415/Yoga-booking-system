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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText emailaddressEditText, passwordEditText, repasswordEditText,
            lastnameEditText, firstnameEditText;
    private Button signupButtonOnSignupActivity;
    private FloatingActionButton backActionButton10;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        context = this;

        emailaddressEditText = findViewById(R.id.emailaddressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repasswordEditText = findViewById(R.id.repasswordEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        firstnameEditText = findViewById(R.id.firstnameEditText);
        signupButtonOnSignupActivity = findViewById(R.id.signupButtonOnSignupActivity);
        backActionButton10 = findViewById(R.id.backActionButton10);

        //Regisztrálok gomb megnyomása
        signupButtonOnSignupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        //Visszatérés a főoldalra
        backActionButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , MainActivity.class));
            }
        });

    }

    //Felhasználó regisztrálása. Adatok validációja majd küldése a szervernek.
    private void registerUser(){
        String emailAddress = emailaddressEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String rePassword = repasswordEditText.getText().toString().trim();
        String lastName = lastnameEditText.getText().toString().trim();
        String firstName = firstnameEditText.getText().toString().trim();

        if(validateDatas(emailAddress,password,rePassword,lastName,firstName)){

            //Kérés küldése a szervernek
            //Regisztráció
            Call<ResponseBody> call = Network.getInstance()
                    .getApiService()
                    .signup(new User(emailAddress,password,firstName,lastName));


            //Válasz a szervertől
            //A válasz "successRegistration" vagy "emailAddressAlredyExist"
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String responseString = "";
                    try {
                        responseString = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if(responseString.equals("successRegistration")){
                        Toast.makeText(SignupActivity.this, "Sikeres regisztráció.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }
                    else if (responseString.equals("emailAddressAlredyExist")){
                        Toast.makeText(SignupActivity.this , "Ezzel az email címmel már regisztráltak.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Hiba. Nem lehet kapcsolódni a szerverhez.");

                }
            });

        }

    }

    //Megadott adatok validációja.
    //Egyik mező sem lehet üres és a két jelszónak meg kell egyezni
    private boolean validateDatas(String emailAddress,String password,String rePassword,String lastName,String firstName){

        if(emailAddress.isEmpty()){
            emailaddressEditText.setError("Add meg az email címed!");
            emailaddressEditText.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Add meg a jelszót!");
            passwordEditText.requestFocus();
            return false;
        }

        if(rePassword.isEmpty()){
            repasswordEditText.setError("Add meg a jelszót újra!");
            repasswordEditText.requestFocus();
            return false;
        }

        if(!password.equals(rePassword)){
            repasswordEditText.setError("A két jelszó nem egyezik meg!");
            repasswordEditText.requestFocus();
            return false;
        }

        if(lastName.isEmpty()){
            lastnameEditText.setError("Add meg a vezetékneved!");
            lastnameEditText.requestFocus();
            return false;
        }

        if(firstName.isEmpty()){
            firstnameEditText.setError("Add meg a keresztneved!");
            firstnameEditText.requestFocus();
            return false;
        }

        return true;

    }
}
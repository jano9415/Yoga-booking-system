package com.example.yoga_booking_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.User;
import com.example.yoga_booking_android.domain.Yogapass;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseYogapassActivity extends AppCompatActivity {

    private ListView yogaPassesListView;
    private Button purchaseBtn;
    private TextView textView6, textView7;
    private FloatingActionButton backActionButton3;

    private User actualUser;

    private List<Yogapass> yogapasses = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Context context;
    private long userId, selectedYogapassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_yogapass);
        getSupportActionBar().hide();

        context = this;
        userId = getIntent().getLongExtra("id" , 0);

        yogaPassesListView = findViewById(R.id.yogaPassesListView);
        purchaseBtn = findViewById(R.id.purchaseBtn);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        backActionButton3 = findViewById(R.id.backActionButton3);

        yogaPassesListView.setVisibility(View.INVISIBLE);
        purchaseBtn.setVisibility(View.INVISIBLE);
        textView6.setVisibility(View.INVISIBLE);
        textView7.setVisibility(View.INVISIBLE);

        //Kérés küldése a szervernek
        //Felhasználó keresése id szerint
        Call<User> call = Network.getInstance().getApiService().findUserById(userId);

        //Válasz a szervertől
        //A válasz egy felhasználó objektum
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                actualUser = response.body();

                if(actualUser.getYogapass() == null){

                    yogaPassesListView.setVisibility(View.VISIBLE);


                }
                else{

                    textView6.setVisibility(View.VISIBLE);
                    textView7.setVisibility(View.VISIBLE);

                    textView6.setText("A bérleted típusa: " + actualUser.getYogapass().getDescription());
                    textView7.setText("Még felhasználható alkalmak száma: " + actualUser.getOccasionCounter());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //Kérés küldése a szervernek
        //Bérlet típusok lekérése
        Call<List<Yogapass>> call2 = Network.getInstance().getApiService().showYogapasses();

        //Válasz a szervertől
        //A válasz egy bérletek objektum lista
        call2.enqueue(new Callback<List<Yogapass>>() {
            @Override
            public void onResponse(Call<List<Yogapass>> call, Response<List<Yogapass>> response) {
                yogapasses = response.body();

                List<String> yogapassesDescription = new ArrayList<>();

                for(Yogapass y : yogapasses){
                    yogapassesDescription.add(y.getDescription());
                }
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,yogapassesDescription);
                yogaPassesListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Yogapass>> call, Throwable t) {

            }
        });

        //Kattintási esemény a jóga bérletek lista egyik elemére
        //A kiválasztás után megjelenik a vásárlás gomb
        yogaPassesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("A kiválasztott bérlet")
                        .setMessage("Bérlet típusa: " + yogapasses.get(index).getDescription() + "\n" +
                                "Ár: " + yogapasses.get(index).getPrice())
                        .setPositiveButton("Kiválasztás", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedYogapassId = yogapasses.get(index).getId();
                                purchaseBtn.setVisibility(View.VISIBLE);

                            }
                        }).show();

            }
        });

        //Vásárlás gomb megnyomása
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Kérés küldése a szervernek
                //Bérlet vásárlása
                Call<ResponseBody> call2 = Network.getInstance().getApiService().purchaseYogaPass(userId , selectedYogapassId);

                //Válasz a szervertől
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String responseString = null;
                        try {
                            responseString = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (responseString.equals("ok")) {
                            Toast.makeText(context, "Sikeres vásárlás", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context , PurchaseYogapassActivity.class).putExtra("id" , userId));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        //Visszatérés a főoldalra
        backActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , UserActivity.class).putExtra("id" , userId));
            }
        });


    }
}
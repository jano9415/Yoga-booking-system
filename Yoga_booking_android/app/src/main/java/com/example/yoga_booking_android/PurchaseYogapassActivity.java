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

        //K??r??s k??ld??se a szervernek
        //Felhaszn??l?? keres??se id szerint
        Call<User> call = Network.getInstance().getApiService().findUserById(userId);

        //V??lasz a szervert??l
        //A v??lasz egy felhaszn??l?? objektum
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

                    textView6.setText("A b??rleted t??pusa: " + actualUser.getYogapass().getDescription());
                    textView7.setText("M??g felhaszn??lhat?? alkalmak sz??ma: " + actualUser.getOccasionCounter());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //K??r??s k??ld??se a szervernek
        //B??rlet t??pusok lek??r??se
        Call<List<Yogapass>> call2 = Network.getInstance().getApiService().showYogapasses();

        //V??lasz a szervert??l
        //A v??lasz egy b??rletek objektum lista
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

        //Kattint??si esem??ny a j??ga b??rletek lista egyik elem??re
        //A kiv??laszt??s ut??n megjelenik a v??s??rl??s gomb
        yogaPassesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("A kiv??lasztott b??rlet")
                        .setMessage("B??rlet t??pusa: " + yogapasses.get(index).getDescription() + "\n" +
                                "??r: " + yogapasses.get(index).getPrice())
                        .setPositiveButton("Kiv??laszt??s", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedYogapassId = yogapasses.get(index).getId();
                                purchaseBtn.setVisibility(View.VISIBLE);

                            }
                        }).show();

            }
        });

        //V??s??rl??s gomb megnyom??sa
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //K??r??s k??ld??se a szervernek
                //B??rlet v??s??rl??sa
                Call<ResponseBody> call2 = Network.getInstance().getApiService().purchaseYogaPass(userId , selectedYogapassId);

                //V??lasz a szervert??l
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
                            Toast.makeText(context, "Sikeres v??s??rl??s", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context , PurchaseYogapassActivity.class).putExtra("id" , userId));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        //Visszat??r??s a f??oldalra
        backActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , UserActivity.class).putExtra("id" , userId));
            }
        });


    }
}
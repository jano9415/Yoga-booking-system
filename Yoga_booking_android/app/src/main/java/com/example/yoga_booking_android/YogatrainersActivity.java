package com.example.yoga_booking_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Yogatrainer;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogatrainersActivity extends AppCompatActivity {

    private ListView yogatrainersListView;
    private List<Yogatrainer> yogatrainers = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Context context;
    private FloatingActionButton backActionButton6;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yogatrainers);
        getSupportActionBar().hide();

        context = this;
        userId = getIntent().getLongExtra("id" , 0);
        yogatrainersListView = findViewById(R.id.yogatrainersListView);
        backActionButton6 = findViewById(R.id.backActionButton6);


        //Kérés küldése a szervernek
        //Jóga oktatók lekérése
        Call<List<Yogatrainer>> call = Network.getInstance().getApiService().showYogaTrainers();

        //Válasz a szervertől
        //Jóga oktatók vezeték és kereszt neveinek megjelenítése egy listview-ban
        //
        call.enqueue(new Callback<List<Yogatrainer>>() {
            @Override
            public void onResponse(Call<List<Yogatrainer>> call, Response<List<Yogatrainer>> response) {

                yogatrainers = response.body();

                List<String> names = new ArrayList<>();

                for(Yogatrainer yt : yogatrainers){
                    names.add(yt.getLastName() + " " + yt.getFirstName());
                }

                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,names);
                yogatrainersListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Yogatrainer>> call, Throwable t) {

            }
        });

        //Ha rákattintunk az egyik jóga oktató elemre a ListView-ban, akkor egy alert dialog-ban megjeleník a jóga oktató leírása.
        yogatrainersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(yogatrainers.get(i).getLastName() + " " + yogatrainers.get(i).getFirstName())
                        .setMessage(yogatrainers.get(i).getDescription())
                        .show();
            }
        });

        //Visszatérés a főoldalra
        backActionButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == 0){
                    startActivity(new Intent(context , MainActivity.class));
                }
                else{
                    startActivity(new Intent(context , UserActivity.class).putExtra("id" , userId));
                }
            }
        });


    }

}
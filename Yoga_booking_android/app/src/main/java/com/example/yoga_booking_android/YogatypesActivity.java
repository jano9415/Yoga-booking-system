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

import com.example.yoga_booking_android.domain.Yogatype;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogatypesActivity extends AppCompatActivity {

    private ListView yogatypesListView;
    private List<Yogatype> yogatypes = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Context context;
    private FloatingActionButton backActionButton7;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yogatypes);
        getSupportActionBar().hide();

        context = this;
        userId = getIntent().getLongExtra("id" , 0);
        yogatypesListView = findViewById(R.id.yogatypesListView2);
        backActionButton7 = findViewById(R.id.backActionButton7);

        //Kérés küldése a szervernek
        //Jóga típusok lekérése
        Call<List<Yogatype>> call = Network.getInstance().getApiService().showYogaTypes();

        //Válasz a szervertől
        //Jóga típusok neveinek megjelenítése egy listview-ban
        call.enqueue(new Callback<List<Yogatype>>() {
            @Override
            public void onResponse(Call<List<Yogatype>> call, Response<List<Yogatype>> response) {

                yogatypes = response.body();

                List<String> typeNames = new ArrayList<>();

                for(Yogatype yt : yogatypes){
                    typeNames.add(yt.getName());
                }
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,typeNames);
                yogatypesListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Yogatype>> call, Throwable t) {

            }

        });

        //Ha rákattintunk az egyik jóga típus elemre a ListView-ban, akkor egy alert dialog-ban megjeleník a jóga típus leírása.
        yogatypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(yogatypes.get(i).getName())
                        .setMessage(yogatypes.get(i).getDescription())
                        .show();
            }
        });

        //Visszatérés a főoldalra
        backActionButton7.setOnClickListener(new View.OnClickListener() {
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
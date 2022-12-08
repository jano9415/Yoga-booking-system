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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Yogatrainer;
import com.example.yoga_booking_android.domain.Yogatype;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewYogatypeActivity extends AppCompatActivity {



    private ListView yogatypesListView2;
    private Button addNewYogatypeBtn;
    private FloatingActionButton backActionButton8;

    private long trainerId;
    private long selectedYogatypeId;
    private Yogatrainer actualYogatrainer;
    private List<Yogatype> allYogatypes = new ArrayList<>();
    private List<Yogatype> myYogatypes = new ArrayList<>();
    private List<Yogatype> restOfYogatypes = new ArrayList<>();
    private Context context;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_yogatype);
        getSupportActionBar().hide();

        context = this;

        trainerId = getIntent().getLongExtra("id" , 0);

        yogatypesListView2 = findViewById(R.id.yogatypesListView2);
        addNewYogatypeBtn = findViewById(R.id.addNewYogatypeBtn);
        backActionButton8 = findViewById(R.id.backActionButton8);


        addNewYogatypeBtn.setVisibility(View.INVISIBLE);

        //Kérés küldése a szervernek
        //A bejelentkezett jóga oktató lekérése
        Call<Yogatrainer> call = Network.getInstance().getApiService().findYogatrainerById(trainerId);

        //Válasz a szervertől
        call.enqueue(new Callback<Yogatrainer>() {
            @Override
            public void onResponse(Call<Yogatrainer> call, Response<Yogatrainer> response) {
                actualYogatrainer = response.body();

                for(Yogatype y : actualYogatrainer.getYogatypes()){
                    myYogatypes.add(y);
                }

            }

            @Override
            public void onFailure(Call<Yogatrainer> call, Throwable t) {

            }
        });

        //Kérés küldése a szervernek
        //Összes jóga típus lekérése
        Call<List<Yogatype>> call2 = Network.getInstance().getApiService().showYogaTypes();


        //Válasz a szervertől
        //Jóga típusok neveinek megjelenítése egy listview-ban
        call2.enqueue(new Callback<List<Yogatype>>() {
            @Override
            public void onResponse(Call<List<Yogatype>> call, Response<List<Yogatype>> response) {

                allYogatypes = response.body();

                List<String> typeNames = new ArrayList<>();

                for(Yogatype yt : allYogatypes){
                    typeNames.add(yt.getName());
                }
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,typeNames);
                yogatypesListView2.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Yogatype>> call, Throwable t) {

            }

        });



        //Ha rákattintunk az egyik jóga típus elemre a ListView-ban, akkor egy alert dialog-ban megjeleník a jóga típus leírása
        //és egy kiválasztás gomb. A kiválasztás megnyomása után megjelenik a hozzáadás nevű gomb.
        yogatypesListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(allYogatypes.get(index).getName())
                        .setMessage(allYogatypes.get(index).getDescription())
                        .setPositiveButton("Kiválasztás", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                selectedYogatypeId = allYogatypes.get(index).getId();
                                addNewYogatypeBtn.setVisibility(View.VISIBLE);

                            }
                        })
                        .show();
            }
        });

        //Hozzáadás gomb megnyomása
        addNewYogatypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Kérés küldése a szervernek
                //Új jóga típus hozzáadása
                Call<ResponseBody> call2 = Network.getInstance().getApiService().addYogatype(trainerId , selectedYogatypeId);

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
                            Toast.makeText(context, "Új típus hozzáadva", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context , AddNewYogatypeActivity.class).putExtra("id" , trainerId));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        //Visszatérés a főoldalra
        backActionButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , TrainerActivity.class).putExtra("id" , trainerId));
            }
        });
    }
}
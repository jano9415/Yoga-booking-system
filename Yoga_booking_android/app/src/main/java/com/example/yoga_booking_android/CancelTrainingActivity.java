package com.example.yoga_booking_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Training;
import com.example.yoga_booking_android.domain.User;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelTrainingActivity extends AppCompatActivity {

    private CalendarView selectDateCalendarOnCtAc;
    private ListView trainingsListViewOnCtAc;
    private Button cancelTrainingBtn;
    private FloatingActionButton backActionButton4;

    Context context;
    private User actualUser;

    private List<Training> trainings = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Date selectedDate, thisDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long millis, userId, selectedTrainingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_training);
        getSupportActionBar().hide();

        context = this;
        userId = getIntent().getLongExtra("id" , 0);

        millis = System.currentTimeMillis();

        calendar = Calendar.getInstance();
        thisDate = new Date(calendar.getTimeInMillis());

        selectDateCalendarOnCtAc = findViewById(R.id.selectDateCalendarOnCtAc);
        trainingsListViewOnCtAc = findViewById(R.id.trainingsListViewOnCtAc);
        cancelTrainingBtn = findViewById(R.id.cancelTrainingBtn);
        backActionButton4 = findViewById(R.id.backActionButton4);

        cancelTrainingBtn.setVisibility(View.INVISIBLE);

        //Kérés küldése a szervernek
        //Felhasználó keresése id szerint
        Call<User> call = Network.getInstance().getApiService().findUserById(userId);

        //Válasz a szervertől
        //A válasz egy felhasználó objektum
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                actualUser = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //Dátum kiválasztása a calendar view-ból
        //Csak akkor kérem le az edzéseket, ha a kiválasztott dátum nagyobb vagy egyenlő, mint a mai dátum
        selectDateCalendarOnCtAc.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR , i);
                calendar.set(Calendar.MONTH , i1);
                calendar.set(Calendar.DAY_OF_MONTH , i2);
                selectedDate = calendar.getTime();

                trainingsListViewOnCtAc.setAdapter(null);


                if(selectedDate.getTime() >= thisDate.getTime()){


                    //Kérés küldése a szervernek
                    //Edzések lekérése dátum szerint
                    Call<List<Training>> call = Network.getInstance().getApiService().showUserTrainingsByDate(
                             userId , simpleDateFormat.format(selectedDate));

                    //Válasz a szervertől
                    //Edzések dátumának és kezdő időpontjának megjelenítése egy listview-ban
                    call.enqueue(new Callback<List<Training>>() {
                        @Override
                        public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                            trainings = response.body();

                            List<String> trainingDates = new ArrayList<>();

                            for(Training t : trainings){
                                trainingDates.add(t.getYogatype().getName() + "    " + t.getDate() + "  " + t.getStartingTime() + " - " +
                                        t.getFinishingTime());
                            }
                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,trainingDates);
                            trainingsListViewOnCtAc.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<List<Training>> call, Throwable t) {

                        }
                    });

                }


            }
        });

        //Kattintás az edzések lista egyik elemére
        //Edzést lemondani csak az edzés előtti napig lehet
        //Ha az edzés napján akarja lemondani, akkor az edzés kiválasztása után az adatai látja és
        // egy szöveget, hogy az edzést már nem lehet lemondani
        //Ha korábban akarja lemondani, akkor az edzés kiválasztása után megjelenik egy lemondás nevű gomb. A gomb megnyomása után
        //véglegessé válik a lemondás. A felhasználó bérlete is frissítve lesz.
        trainingsListViewOnCtAc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int trainingId, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if(selectedDate.getTime() == thisDate.getTime()){

                    builder.setTitle(trainings.get(trainingId).getYogatype().getName())
                            .setMessage("Dátum: " + trainings.get(trainingId).getDate() + "\n\n" +
                                    "Kezdés időpontja: " + trainings.get(trainingId).getStartingTime() + "\n" +
                                    "Befejezés időpontja: " + trainings.get(trainingId).getFinishingTime() + "\n" +
                                    "Oktató: " + trainings.get(trainingId).getYogatrainer().getLastName() + " " + trainings.get(trainingId).getYogatrainer().getFirstName() + "\n" +
                                    "Edzés nyelve: " + trainings.get(trainingId).getLanguage() + "\n" +
                                    "Maximum létszám: " + trainings.get(trainingId).getMaxCapacity() + "\n\n" +
                                    "Ezt az edzést már nem mondhatod le!")
                            .show();
                }
                else{
                    builder.setTitle(trainings.get(trainingId).getYogatype().getName())
                            .setMessage("Dátum: " + trainings.get(trainingId).getDate() + "\n\n" +
                                    "Kezdés időpontja: " + trainings.get(trainingId).getStartingTime() + "\n" +
                                    "Befejezés időpontja: " + trainings.get(trainingId).getFinishingTime() + "\n" +
                                    "Oktató: " + trainings.get(trainingId).getYogatrainer().getLastName() + " " + trainings.get(trainingId).getYogatrainer().getFirstName() + "\n" +
                                    "Edzés nyelve: " + trainings.get(trainingId).getLanguage() + "\n" +
                                    "Maximum létszám: " + trainings.get(trainingId).getMaxCapacity())
                            .setPositiveButton("Kiválasztás", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    selectedTrainingId = trainings.get(trainingId).getId();
                                    cancelTrainingBtn.setVisibility(View.VISIBLE);
                                }
                            })
                            .show();
                }
            }
        });

        //Lemondás gomb megnyomása
        cancelTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Kérés küldése a szervernek
                //Edzés lemondása
                Call<ResponseBody> call2 = Network.getInstance().getApiService().cancelTraining(userId , selectedTrainingId);

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
                            Toast.makeText(context, "A kiválasztott edzés lemondva", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context , CancelTrainingActivity.class).putExtra("id" , userId));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        //Visszatérés a főoldalra
        backActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , UserActivity.class).putExtra("id" , userId));
            }
        });
    }
}
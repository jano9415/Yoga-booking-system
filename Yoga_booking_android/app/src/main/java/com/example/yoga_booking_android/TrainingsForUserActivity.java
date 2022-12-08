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

public class TrainingsForUserActivity extends AppCompatActivity {

    private CalendarView selectDateCalendarOnTfuAc;
    private ListView trainingsListViewOnTfuAc;
    private Button joinTrainingBtn;
    private FloatingActionButton backActionButton2;

    private User actualUser;

    private List<Training> trainings = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Context context;
    private Date selectedDate, thisDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long millis, userId, selectedTrainingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_for_user);
        getSupportActionBar().hide();

        context = this;
        userId = getIntent().getLongExtra("id" , 0);

        millis = System.currentTimeMillis();

        calendar = Calendar.getInstance();
        thisDate = new Date(calendar.getTimeInMillis());

        selectDateCalendarOnTfuAc = findViewById(R.id.selectDateCalendarOnTfuAc);
        trainingsListViewOnTfuAc = findViewById(R.id.trainingsListViewOnCtAc);
        joinTrainingBtn = findViewById(R.id.joinTrainingBtn);
        backActionButton2 = findViewById(R.id.backActionButton2);

        joinTrainingBtn.setVisibility(View.INVISIBLE);

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
        selectDateCalendarOnTfuAc.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR , i);
                calendar.set(Calendar.MONTH , i1);
                calendar.set(Calendar.DAY_OF_MONTH , i2);
                selectedDate = calendar.getTime();

                trainingsListViewOnTfuAc.setAdapter(null);

                if(selectedDate.getTime() >= thisDate.getTime()){

                    //Kérés küldése a szervernek
                    //Edzések lekérése dátum szerint
                    Call<List<Training>> call = Network.getInstance().getApiService().findTrainingByDateAfterLogin(
                            simpleDateFormat.format(selectedDate) , userId);

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
                            trainingsListViewOnTfuAc.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<List<Training>> call, Throwable t) {

                        }
                    });

                }

            }
        });

        //Kattintás az edzések lista egyik elemére
        //Ha a bejelentkezett felhasználó még nem vásárolt bérletet, akkor nem tud jelentkezni az edzésre csak látja őket
        //Ha már vásárolt bérletet, akkor az edzés kiválasztása után megjelenik egy jelentkezés nevű gomb.
        // Ha megnyomja a gombot, akkor lesz végleges a jelentkezés
        trainingsListViewOnTfuAc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int trainingId, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if(actualUser.getYogapass() == null){

                    builder.setTitle(trainings.get(trainingId).getYogatype().getName())
                            .setMessage("Dátum: " + trainings.get(trainingId).getDate() + "\n\n" +
                                    "Kezdés időpontja: " + trainings.get(trainingId).getStartingTime() + "\n" +
                                    "Befejezés időpontja: " + trainings.get(trainingId).getFinishingTime() + "\n" +
                                    "Oktató: " + trainings.get(trainingId).getYogatrainer().getLastName() + " " + trainings.get(trainingId).getYogatrainer().getFirstName() + "\n" +
                                    "Edzés nyelve: " + trainings.get(trainingId).getLanguage() + "\n" +
                                    "Maximum létszám: " + trainings.get(trainingId).getMaxCapacity() + "\n\n" +
                                    "Csak bérlettel rendelkező felhasználó tud jelentkezni jóga órára." + "\n" +
                                    "Vásárolj bérletet a jelentkezéshez!")
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
                            joinTrainingBtn.setVisibility(View.VISIBLE);

                        }
                    }).show();

                }
            }
        });

        //Jelentkezés gomb megnyomása
        joinTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Kérés küldése a szervernek
                //Jelentkezés a kiválasztott edzése
                Call<ResponseBody> call = Network.getInstance().getApiService().participateTraining(userId , selectedTrainingId);

                //Válasz a szervertől
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String responseString = null;
                        try {
                            responseString = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (responseString.equals("ok")) {
                            Toast.makeText(context, "Sikeres jelentkezés", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context , TrainingsForUserActivity.class).putExtra("id" , userId));
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        //Visszatérés a főoldalra
        backActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , UserActivity.class).putExtra("id" , userId));
            }
        });

    }
}
package com.example.yoga_booking_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Training;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingsActivity extends AppCompatActivity {

    private FloatingActionButton backActionButton5;

    private ListView trainingsListView;
    private List<Training> trainings = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Context context;
    private CalendarView selectDateCalendarOnTrainingsAc;
    private Date selectedDate, thisDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);
        getSupportActionBar().hide();

        context = this;
        trainingsListView = findViewById(R.id.trainingsListView2);
        selectDateCalendarOnTrainingsAc = findViewById(R.id.selectDateCalendarOnTrainingsAc);
        backActionButton5 = findViewById(R.id.backActionButton5);

        millis = System.currentTimeMillis();

        calendar = Calendar.getInstance();
        thisDate = new Date(calendar.getTimeInMillis());

        //Dátum kiválasztása a calendar view-ból
        selectDateCalendarOnTrainingsAc.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR , i);
                calendar.set(Calendar.MONTH , i1);
                calendar.set(Calendar.DAY_OF_MONTH , i2);
                selectedDate = calendar.getTime();

                trainingsListView.setAdapter(null);

                if(selectedDate.getTime() >= thisDate.getTime()){

                    //Kérés küldése a szervernek
                    //Edzések lekérése dátum szerint
                    Call<List<Training>> call = Network.getInstance().getApiService().findTrainingByDate(simpleDateFormat.format(selectedDate));

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
                            trainingsListView.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<List<Training>> call, Throwable t) {

                        }
                    });

                }
            }
        });




        //Ha rákattintunk az egyik edzés elemre a ListView-ban, akkor egy alert dialog-ban megjelennek az edzés tulajdonságai.
        trainingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(trainings.get(i).getYogatype().getName())
                        .setMessage("Dátum: " + trainings.get(i).getDate() + "\n\n" +
                        "Kezdés időpontja: " + trainings.get(i).getStartingTime() + "\n" +
                        "Befejezés időpontja: " + trainings.get(i).getFinishingTime() + "\n" +
                        "Oktató: " + trainings.get(i).getYogatrainer().getLastName() + " " + trainings.get(i).getYogatrainer().getFirstName() + "\n" +
                        "Edzés nyelve: " + trainings.get(i).getLanguage() + "\n" +
                        "Maximum létszám: " + trainings.get(i).getMaxCapacity())
                        .show();
            }
        });

        //Visszatérés a főoldalra
        backActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , MainActivity.class));
            }
        });
    }
}
package com.example.yoga_booking_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_booking_android.domain.Training;
import com.example.yoga_booking_android.domain.Yogatrainer;
import com.example.yoga_booking_android.domain.Yogatype;
import com.example.yoga_booking_android.remote.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTrainingActivity extends AppCompatActivity {

    private CalendarView selectDateCalendar;
    private TimePicker selectStartTimePicker , selectFinishTimePicker;
    private Button selectLanguageBtn, selectYogaTypeBtn, addNewTrainingBtn;
    private EditText maxCapacityTn;
    private ListView trainingsListView2;
    private Context context;

    private FloatingActionButton backActionButton1;

    private long trainerId;

    private String selectedLanguage;
    private long millis;
    private Date selectedDate, thisDate;
    private Calendar calendar;
    private String selectedStartTime, selectedFinishTime;
    private List<Yogatype> yogatypes = new ArrayList<>();
    private Yogatype selectedYogatype;
    private Yogatrainer actualYogatrainer;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private List<Training> trainings = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_training);
        getSupportActionBar().hide();

        context = this;

        trainerId = getIntent().getLongExtra("id" , 0);


        millis = System.currentTimeMillis();

        calendar = Calendar.getInstance();
        thisDate = new Date(calendar.getTimeInMillis());


        selectDateCalendar = findViewById(R.id.selectDateCalendar);
        selectStartTimePicker = findViewById(R.id.selectStartTimePicker);
        selectFinishTimePicker = findViewById(R.id.selectFinishTimePicker);
        selectLanguageBtn = findViewById(R.id.selectLanguageBtn);
        selectYogaTypeBtn = findViewById(R.id.selectYogaTypeBtn);
        maxCapacityTn = findViewById(R.id.maxCapacityTn);
        addNewTrainingBtn = findViewById(R.id.addNewTrainingBtn);
        backActionButton1 = findViewById(R.id.backActionButton1);
        trainingsListView2 = findViewById(R.id.trainingsListView2);

        selectStartTimePicker.setIs24HourView(true);
        selectFinishTimePicker.setIs24HourView(true);

        //Kérés küldése a szervernek
        //A bejelentkezett jóga oktató lekérése
        Call<Yogatrainer> call2 = Network.getInstance().getApiService().findYogatrainerById(trainerId);

        //Válasz a szervertől
        call2.enqueue(new Callback<Yogatrainer>() {
            @Override
            public void onResponse(Call<Yogatrainer> call, Response<Yogatrainer> response) {
                actualYogatrainer = response.body();

            }

            @Override
            public void onFailure(Call<Yogatrainer> call, Throwable t) {

            }
        });

        //Kérés küldése a szervernek
        //A bejelentkezett jóga oktató által oktatott típusok lekérése
        Call<Set<Yogatype>> call = Network.getInstance().getApiService().showMyYogatypes(trainerId);

        //Válasz a szervertől
        call.enqueue(new Callback<Set<Yogatype>>() {
            @Override
            public void onResponse(Call<Set<Yogatype>> call, Response<Set<Yogatype>> response) {
                for(Yogatype y : response.body()){
                    yogatypes.add(y);
                }
            }

            @Override
            public void onFailure(Call<Set<Yogatype>> call, Throwable t) {

            }
        });




        //Dátum kiválasztása a calendar view-ból
        selectDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR , i);
                calendar.set(Calendar.MONTH , i1);
                calendar.set(Calendar.DAY_OF_MONTH , i2);
                selectedDate = calendar.getTime();

                trainingsListView2.setAdapter(null);

                if(selectedDate.getTime() >= thisDate.getTime()){

                    //Kérés küldése a szervernek
                    //Edzések lekérése dátum szerint
                    Call<List<Training>> call2 = Network.getInstance().getApiService().findAllTrainingByDate(
                            simpleDateFormat.format(selectedDate));

                    //Válasz a szervertől
                    //Edzések objektum lista megjelenítése egy list view-ban
                    call2.enqueue(new Callback<List<Training>>() {
                        @Override
                        public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {

                            trainings = response.body();

                            List<String> trainingDates = new ArrayList<>();

                            for(Training t : trainings){
                                trainingDates.add(t.getYogatype().getName() + "    " + t.getDate() + "  " + t.getStartingTime() + " - " +
                                        t.getFinishingTime());
                            }
                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,trainingDates);
                            trainingsListView2.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<List<Training>> call, Throwable t) {

                        }
                    });
                }
            }
        });

        //Kezdő időpont kiválasztása
        selectStartTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                //selectedStartTime = new Time(i,i1,0);
                selectedStartTime = i + ":" + i1;
            }
        });

        //Befejező időpont kiválasztása
        selectFinishTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                //selectedFinishTime = new Time(i,i1,0);
                selectedFinishTime = i + ":" + i1;

            }
        });

        //Nyelv kiválasztása gomb megnyomása
        selectLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence charSequence[] = {"magyar" , "angol"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Nyelv kiválasztása")
                        .setItems(charSequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0) selectedLanguage = "magyar";
                                if(i == 1) selectedLanguage = "angol";
                            }
                        })
                        .show();

            }
        });




        //Jóga típus kiválasztása gomb megnyomása
        selectYogaTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence charSequence[] = new CharSequence[yogatypes.size()];
                for(int i=0; i<yogatypes.size(); i++){
                    charSequence[i] = yogatypes.get(i).getName();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Típus kiválasztása")
                        .setItems(charSequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedYogatype = yogatypes.get(i);
                            }
                        })
                        .show();

            }
        });

        //Hozzáadás gomb megnyomása
        //Edzés objektum összeállítása és küldése a szervernek, ha a kiválasztott dátum nagyobb vagy egyenlő, mint a mai
        addNewTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedDate.getTime() >= thisDate.getTime()){

                    Training actualTraining = new Training(simpleDateFormat.format(selectedDate) ,selectedStartTime,
                            selectedFinishTime,selectedLanguage,
                            selectedYogatype,actualYogatrainer,Integer.parseInt(maxCapacityTn.getText().toString()),null);

                    //Kérés küldése a szervernek
                    //Edzés objektum küldése
                    Call<ResponseBody> call = Network.getInstance().getApiService().addNewTraining(actualTraining);

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
                                Toast.makeText(context, "Új edzés hozzáadva", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(context , AddNewTrainingActivity.class).putExtra("id" , trainerId));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }
            }
        });

        //Visszatérés a főoldalra
        backActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context , TrainerActivity.class).putExtra("id" , trainerId));
            }
        });
    }
}
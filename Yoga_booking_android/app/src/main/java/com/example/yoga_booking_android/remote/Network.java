package com.example.yoga_booking_android.remote;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static  final String BASE_URL = " http://192.168.0.13:8080/";
    private static Network mInstance;
    private Retrofit retrofit;


    private Network () {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized Network getInstance() {
        if (mInstance == null) {
            mInstance = new Network();
        }
        return mInstance;
    }

    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }

}

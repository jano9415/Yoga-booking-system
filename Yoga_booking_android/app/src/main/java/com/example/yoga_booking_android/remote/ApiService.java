package com.example.yoga_booking_android.remote;

import com.example.yoga_booking_android.domain.Role;
import com.example.yoga_booking_android.domain.Training;
import com.example.yoga_booking_android.domain.User;
import com.example.yoga_booking_android.domain.Yogapass;
import com.example.yoga_booking_android.domain.Yogatrainer;
import com.example.yoga_booking_android.domain.Yogatype;

import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //Regisztráció
    @POST("/signup")
    Call<ResponseBody> signup(@Body User user);

    //Bejelentkezés
    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("emailAddress") String emailAddress , @Field("password") String password);

    //Oktató keresése id alapján
    @FormUrlEncoded
    @POST("/findyogatrainerbyid")
    Call<Yogatrainer> findYogatrainerById(@Field("yogatrainerId") long yogatrainerId);

    //Felhasználó keresése id alapján
    @FormUrlEncoded
    @POST("/finduserbyid")
    Call<User> findUserById(@Field("userId") long userId);

    //Jóga oktatók lekérése
    @GET("/showyogatrainers")
    Call<List<Yogatrainer>> showYogaTrainers();

    //Felhasználók lekérése
    @GET("/showusers")
    Call<List<User>> showUsers();

    //Szerepkörök lekérése
    @GET("/showroles")
    Call<List<Role>> showRoles();

    //Jóga típusok lekérése
    @GET("/showyogatypes")
    Call<List<Yogatype>> showYogaTypes();

    //Edzések lekérése
    @GET("/showtrainings")
    Call<List<Training>> showTrainings();

    //Bérletek lekérése
    @GET("/showyogapasses")
    Call<List<Yogapass>> showYogapasses();

    //Edzések keresése dátum szerint
    @GET("/findalltrainingbydate")
    Call<List<Training>> findAllTrainingByDate(@Query("date") String date);

    //Edzések keresése dátum szerint
    //Csak olyan edzések jönnek vissza a szervertől, ami még nincs tele
    @GET("findtrainingbydate")
    Call<List<Training>> findTrainingByDate(@Query("date") String date);

    //Edzések keresése dátum szerint
    //Csak olyan edzések jönnek vissza a szervertől, ami még nincs tele és a felhasználó még nem jelentkezett rá
    @FormUrlEncoded
    @POST("findtrainingbydateafterlogin")
    Call<List<Training>> findTrainingByDateAfterLogin(@Field("date") String date , @Field("userId") long userId);

    //Új edzés hozzáadása
    @POST("/addnewtraining")
    Call<ResponseBody> addNewTraining(@Body Training actualTraining);

    //Jóga oktató edzéseinek lekérése
    @GET("/showmytrainings")
    Call<Set<Training>> showMyTrainings(@Query("yogatrainerId") long yogatrainerId);

    //Egy már meglévő edzés valamely paraméternének megváltozatása
    @PATCH("/modifytraining")
    Call<ResponseBody> modifyTraining(@Body Training actualTraining);

    //Edzés törlése
    @DELETE("/deletetraining")
    Call<ResponseBody> deleteTraining(@Query("trainingId") long trainingId);

    //Jóga oktatóhoz új jóga típus hozzáadása
    @FormUrlEncoded
    @POST("/addyogatype")
    Call<ResponseBody> addYogatype(@Field("yogatrainerId") long yogatrainerId , @Field("yogatypeId") long yogatypeId);

    //Jóga oktató által oktatott típusok lekérése
    @GET("/showmyyogatypes")
    Call<Set<Yogatype>> showMyYogatypes(@Query("yogatrainerId") long yogatrainerId);

    //Jelentkezés egy edzésre
    @FormUrlEncoded
    @POST("/participatetraining")
    Call<ResponseBody> participateTraining(@Field("userId") long userId , @Field("trainingId") long trainingId);

    //Edzések lekérése, amire már jelentkezett a felhasználó
    @GET("/showusertrainings")
    Call<Set<Training>> showUserTrainings(@Query("userId") long userId);

    //Edzések lekérése dátum szerint, amire már jelentkezett a felhasználó
    @FormUrlEncoded
    @POST("/showusertrainingsbydate")
    Call<List<Training>> showUserTrainingsByDate(@Field("userId") long userId , @Field("date") String date);

    //Jelentkezés lemondása
    @FormUrlEncoded
    @POST("/canceltraining")
    Call<ResponseBody> cancelTraining(@Field("userId") long userId , @Field("trainingId") long trainingId);

    //Bérlet vásárlása
    @FormUrlEncoded
    @POST("/purchaseyogapass")
    Call<ResponseBody> purchaseYogaPass(@Field("userId") long userId , @Field("passId") long passId);







}

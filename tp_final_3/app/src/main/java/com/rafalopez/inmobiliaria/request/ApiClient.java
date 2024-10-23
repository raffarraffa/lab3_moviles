package com.rafalopez.inmobiliaria.request;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public class ApiClient {


    private static final String TAG = "ApiClient";
    @NonNull
    public static InmobiliariaServices getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppParams.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
              //  .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(InmobiliariaServices.class);
    }


    public  interface InmobiliariaServices{
        @POST("login1")
        Call<User> PostLogin1(@Body User user);
        @POST("login")
        Call<String> PostLogin(@Body User user);

        @POST("login/passwordrestore")
        Call<ResponseBody> PostRestore(@Body User user);


        @GET("propietario/perfil")
        Call<Propietario> GetPerfil(@Header("Authorization") String token);

        @PATCH("propietario/update")
        Call<Propietario> PatchPerfil(@Header("Authorization") String token,   @Body Propietario propietario);


    }
}
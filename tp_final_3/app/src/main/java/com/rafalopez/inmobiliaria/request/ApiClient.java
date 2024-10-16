package com.rafalopez.inmobiliaria.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {
   // public static final String URL_BASE= "https://lab3.rafalopez.ar/api/";
    public static final String URL_BASE= "http://192.168.10.25:8104/api/";
    //public static final String URL_BASE= "https://api.rafalopez.ar/";

    public static InmobiliariaServices getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaServices.class);
    }

    public  interface InmobiliariaServices{
        @POST("login")
        Call<String> PostLogin(@Body User user);
        @GET("propietario/perfil")
        Call<String> GetPerfil(@Header("Authorization") String token);

   }

}

// @POST("user/edit")//    Call<User> updateUser(@Body User user);
package com.rafalopez.inmobiliaria.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rafalopez.inmobiliaria.entity.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiClient {
    public static final String URL_BASE= "https://api.rafalopez.ar/";

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


   }
}

// @POST("user/edit")//    Call<User> updateUser(@Body User user);
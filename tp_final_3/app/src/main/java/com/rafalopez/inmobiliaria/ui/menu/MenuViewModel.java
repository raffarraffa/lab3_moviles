package com.rafalopez.inmobiliaria.ui.menu;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private final String token;
    public MenuViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
        token = ApiData.leerData(context,AppParams.PREFERENCES_DATA, AppParams.TOKEN_KEY);
    }
    public void getProfile(){
        Call<Propietario> req = api.GetPerfil(token);
        req.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Propietario propietario = response.body();
                boolean isProrietarioSaved =ApiData.guardarDataPropietario(context,
                        AppParams.PREFERENCES_DATA, propietario);
//                String jsonInmuebles = new Gson().toJson(propietario.getInmuebles());
//                boolean isInmublesSaved=ApiData.guardarData(context,AppParams.PREFERENCES_DATA,jsonInmuebles, AppParams.INMUEBLE_KEY);
                Log.d(TAG, "onResponse: 41"+ propietario);

            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {

            }
        });
        Log.d(TAG, "getProfile: 22" + token);
    }
}

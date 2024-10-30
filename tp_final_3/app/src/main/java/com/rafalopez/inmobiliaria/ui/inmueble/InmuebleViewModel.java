package com.rafalopez.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {

    private static final String TAG = "salida";
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private MutableLiveData<String> mBtnAction;
    private MutableLiveData<ActionMutable> mBtnAction2;
    private List<Inmueble> inmuebles = new ArrayList<>();
    private final Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
    }

    public void getInmuebles(){
        String token = ApiData.getDataToken(context);
        Call<List<Inmueble>> req = api.GetInmuebleList(token);
        req.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                inmuebles = response.body();
                Log.d(TAG, "onResponse: 51 vmInmueble  "+ inmuebles.size());
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d(TAG, "onResponse: 56 vmInmueble  " );
            }
        });
    }
}
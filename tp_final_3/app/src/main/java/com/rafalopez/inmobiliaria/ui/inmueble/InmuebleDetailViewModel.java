package com.rafalopez.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetailViewModel extends AndroidViewModel {

    private static final String TAG = "salida";

    private final ApiClient.InmobiliariaServices api;

    private MutableLiveData<Inmueble> mInmueble;

    private final Context context;

    public InmuebleDetailViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
    }
    public MutableLiveData<Inmueble> getmInmueble(){
        if(mInmueble==null){
            mInmueble =new MutableLiveData<Inmueble>();
        }
        return mInmueble;
    }

    public void  getBundle(@NonNull Bundle bundle){

        if (bundle.containsKey("inmueble")) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) {
                mInmueble.setValue(inmueble);
                Log.d(TAG, "Inmueble seteado en ViewModel: " + inmueble);
            }
        }

    }
}
package com.rafalopez.inmobiliaria.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.entity.Contrato;

import com.rafalopez.inmobiliaria.request.ApiClient;

public class ContratoDetailViewModel extends AndroidViewModel {

    private static final String TAG = "salida";

    private final ApiClient.InmobiliariaServices api;

    private MutableLiveData<Contrato> mContrato;

    private final Context context;

    public ContratoDetailViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
    }
    public MutableLiveData<Contrato> getmContrato(){
        if(mContrato==null){
            mContrato =new MutableLiveData<Contrato>();
        }
        return mContrato;
    }

    public void  getBundle(@NonNull Bundle bundle){

        if (bundle.containsKey("Contrato")) {
            Contrato contrato = (Contrato) bundle.getSerializable("Contrato");
            if (contrato != null) {
                mContrato.setValue(contrato);
                Log.d(TAG, "Contrato seteado en ViewModel: " + contrato);
            }
        }

    }
}
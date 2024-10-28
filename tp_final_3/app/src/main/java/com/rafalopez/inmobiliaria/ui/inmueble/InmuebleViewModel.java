package com.rafalopez.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

public class InmuebleViewModel extends AndroidViewModel {

    private static final String TAG = "salida";
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private MutableLiveData<String> mBtnAction;
    private MutableLiveData<ActionMutable> mBtnAction2;
    private final Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
    }

    public void getInmuebles(){

    }
}
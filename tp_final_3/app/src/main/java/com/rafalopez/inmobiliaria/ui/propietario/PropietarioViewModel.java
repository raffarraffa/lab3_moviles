package com.rafalopez.inmobiliaria.ui.propietario;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

public class PropietarioViewModel extends AndroidViewModel {
    private static final String TAG = "PropeitarioViewModel";
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private final Context context;


    public PropietarioViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
    }

    /**
     * GET LiveData que contiene el propietario
     *
     * @return LiveData de tipo Propietario
     */
    LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    /**
     * GET LiveData
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
            mLoginError.setValue(false);
        }
        return mLoginError;
    }

    /**
     * GET LiveData
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
        }
        return mLoginOk;
    }

    /**
     * GET LiveData  error
     *
     * @return LiveData de tipo String
     */
    LiveData<String> getMsgError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }
    public void verSiAnda(){
        Propietario propietario = ApiData.leerDataPropietario(context, AppParams.PREFERENCES_DATA, AppParams.PROPIETARIO_KEY);
        if(propietario!=null){
            mPropietario.setValue(propietario);
        }
    }

    // TODO: Implement the ViewModel
}
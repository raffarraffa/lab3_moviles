package com.rafalopez.inmobiliaria.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Contrato;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Contrato> mContrato;
    private MutableLiveData<List<Contrato>> mListContratos;

    private List<Contrato> contratos = new ArrayList<>();


    private final Context context;


    public ContratoViewModel(@NonNull Application application) {
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
    public MutableLiveData<List<Contrato>> getmContratos(){
        if(mListContratos==null){
            mListContratos =new MutableLiveData<List<Contrato>>();
        }
        return mListContratos;
    }






    public  void  getContratos(){
        String token = ApiData.getDataToken(context);
        Call<List<Contrato>> req = api.GetListContrato(token);
        req.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful() && response.body() != null) {
                  contratos = response.body();
                  mListContratos.setValue(contratos);
                  contratos.forEach(c->{
                      Log.e(AppParams.TAG, c.toString()  );
                  });
                    Log.i(AppParams.TAG, "onResponse: " + contratos);
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                Log.d(AppParams.TAG, "Error en getContratos: " + throwable.getMessage());
            }
        });
    }

}
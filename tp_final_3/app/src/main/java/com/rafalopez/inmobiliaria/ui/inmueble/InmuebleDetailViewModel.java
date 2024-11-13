package com.rafalopez.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.ResMsg;
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
    private MutableLiveData<String> mSwitch;
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
    public  MutableLiveData<String> getMSwitch(){
        if(mSwitch==null){
            mSwitch =new MutableLiveData<>();
        }
        return  mSwitch;
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
    public void verificaEstado(boolean banderaActual, boolean banderaNueva){
        if(banderaActual != banderaNueva){
            Log.e(TAG, "verificaEstado: " + banderaActual +" " + banderaNueva);
            if(banderaNueva) {
                mSwitch.setValue("Disponible");
            }else {
                mSwitch.setValue("Retirado");
            }
        }
    }
    public  void  cambiarEstado(int id, String estado){
        String token = ApiData.getDataToken(context);
        Call<Inmueble> req = api.CambiarEstado(token,id,estado);
        req.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful())
                    Toast.makeText(context,"Inmueble modificado ",Toast.LENGTH_SHORT).show();
                mInmueble.setValue(response.body());
                if(!response.isSuccessful())
                    Toast.makeText(context,"Inmueble No se modifico ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
                Toast.makeText(context,"Error, el inmueble no se modifico",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", throwable );
            }
        });
    }
    // @PATCH("inmueble/changeEstado/{id}/{estado}")
    //        Call<ResMsg> CambiarEstado(@Header("Authorization") String token, @Path("id") int id, @Path("estado") String estado);
}
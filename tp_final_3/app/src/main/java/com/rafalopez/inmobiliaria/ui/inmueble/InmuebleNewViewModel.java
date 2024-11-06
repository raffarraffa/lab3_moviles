package com.rafalopez.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.InmuebleDto;
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleNewViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Boolean> mResultOk;
    private MutableLiveData<String> mMsg;
    private MutableLiveData<Boolean> mPermisoGaleria;

    /**
     * cnstructor de InmuebleNewViewModel
     * @param application contexto de la app
     */

    public InmuebleNewViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
    }
    LiveData<Boolean> getMResultOk() {
        if (mResultOk == null) {
            mResultOk = new MutableLiveData<>();
        }
        return mResultOk;
    }
    LiveData<Boolean> getmPermisoGaleria() {
        if (mPermisoGaleria == null) {
            mPermisoGaleria = new MutableLiveData<>();
        }
        return mPermisoGaleria;
    }
    LiveData<String> getMMsg() {
        if (mMsg == null) {
            mMsg = new MutableLiveData<>();
        }
        return mMsg;
    }

    public void  crearInmueble(InmuebleDto inmueble){
        String token = ApiData.getDataToken(context);
        Log.d(TAG, "crearInmueble: " + inmueble);
        Call<ResMsg> req =api.CreateInmueble(token,inmueble);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                if(response.isSuccessful()){
                    mResultOk.setValue(true);
                    Log.d(TAG, "onResponse: 68");
                }else{
                    Log.d(TAG, "onResponse: 71");
                }

                Log.d(TAG, "onResponse: " + response);
            }
            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {

            }
        });
   }
}
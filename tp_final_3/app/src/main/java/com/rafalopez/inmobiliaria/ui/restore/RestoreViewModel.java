package com.rafalopez.inmobiliaria.ui.restore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoreViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    // mutalbes
    private MutableLiveData<String> mAceptResultOk;
    public RestoreViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
    }
    /**
     * Obtiene LiveData que indica si hubo un error en el inicio de SESSION
     * @return LiveData de tipo Boolean
     */
    LiveData<String> getMAceptResultOk() {
        if (mAceptResultOk == null) {
            mAceptResultOk = new MutableLiveData<>();
        }
        return mAceptResultOk;
    }

    /**
     *
     * @param email
     * @param password
     */
    public void acceptRestore(String email, String password) {
        User user = new User(email, password);
        Call<ResMsg> req = api.PostAcceptRestore(user);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                Log.d(TAG, "onResponse: " + response);
                ResMsg msg=response.body();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: 57 " + msg.toString());
                 //   mAceptResultOk.setValue(msg.getMsg());
                } else {

                    Log.d(TAG, "onResponse : 61");
                //    mAceptResultOk.setValue("Error de Recuper");
                }
            }
            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
//                mAceptResultOk.setValue("Error de conexión: " + throwable.getMessage());
               // mAceptResultOk.setValue("Error de conexión: " );
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }
}

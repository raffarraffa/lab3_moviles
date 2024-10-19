package com.rafalopez.inmobiliaria;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private static final String PREFERENCES_DATA = "dataInmobiliaria";
    private static final String TOKEN_KEY = "token";
    private static final String TAG = "MainViewModel";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Boolean> mTokenInvalid;
    private MutableLiveData<Boolean> mTokenValid;
    private MutableLiveData<Boolean> mIntenet;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
        registerConnectivityReceiver();
    }

    public MutableLiveData<Boolean> getMTokenInvalid() {
        if (mTokenInvalid == null) mTokenInvalid = new MutableLiveData<>();
        return mTokenInvalid;
    }

    public MutableLiveData<Boolean> getMTokenValid() {
        if (mTokenValid == null) mTokenValid = new MutableLiveData<>();
        return mTokenValid;
    }

    public MutableLiveData<Boolean> getMInternet() {
        if (mIntenet == null) mIntenet = new MutableLiveData<>();
        return mIntenet;
    }

    private boolean isPresentToken() {
        String token = ApiData.leerData(context, PREFERENCES_DATA, TOKEN_KEY);
        Log.d(TAG, "isPresentToken: " + token);
        if (token == null || token.isEmpty()) {
            mTokenInvalid.setValue(true);
            return false;
        }
        return true;
    }

    private void isValidToken() {
        String token = "Bearer " + ApiData.leerData(context, PREFERENCES_DATA, TOKEN_KEY);

        Call<Propietario> propReq = api.GetPerfil(token);
        propReq.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mTokenValid.setValue(true);
                } else {
                    mTokenInvalid.setValue(true);
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
            }
        });
    }
    private void checkJwt() {
     //   Toast.makeText(context,isPresentToken()+"",Toast.LENGTH_LONG).show();
        if (isPresentToken()) {
            isValidToken();
        } else {
            Log.d(TAG, "checkJwt: No hay jwt");
         //   Toast.makeText(context, "Debe inciar Session", Toast.LENGTH_LONG).show();
        }
    }
    public String isInmobiliariaOk() {
        checkJwt();
        return "iniciando";
    }
    private void registerConnectivityReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(new ConnectivityReceiver(), filter);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        context.unregisterReceiver(new ConnectivityReceiver());
    }
    public class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isInternet() && mIntenet.getValue() == null) {
                mIntenet.setValue(false);
            } else {
                checkJwt();
            }
        }
        private boolean isInternet() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}

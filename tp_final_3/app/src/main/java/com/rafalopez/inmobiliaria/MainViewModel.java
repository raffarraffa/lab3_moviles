package com.rafalopez.inmobiliaria;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel  auth y conectividad
 */
public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "salidaDebug"; // tag para log
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Boolean> mTokenInvalid;
    private MutableLiveData<Boolean> mTokenValid;
    private MutableLiveData<Boolean> mInternet;

    /**
     * constructor del ViewModel.
     *
     * @param application  aplicacion actual
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
        registerConnectivityReceiver();
    }

    /**
     * devuelve estado de token invalido
     *
     * @return MutableLiveData para token invalido
     */
    public MutableLiveData<Boolean> getMTokenInvalid() {
        if (mTokenInvalid == null) mTokenInvalid = new MutableLiveData<>();
        return mTokenInvalid;
    }

    /**
     * devuelve estado de token  valido
     *
     * @return MutableLiveData para token valido
     */
    public MutableLiveData<Boolean> getMTokenValid() {
        if (mTokenValid == null) mTokenValid = new MutableLiveData<>();
        return mTokenValid;
    }

    /**
     * devuelve  estado de la conexion a internet
     *
     * @return MutableLiveData para estado internet
     */
    //todo faltaria un mutable para estado  levantado?
    public MutableLiveData<Boolean> getMInternet() {
        if (mInternet == null) mInternet = new MutableLiveData<>();
        return mInternet;
    }

    /**  logica aplicacion */

    /**
     * verifica Token existey si es valido con una solicitud a la API
     */
    public void isValidToken() {

        // leo data d esharedpreference
        String token = ApiData.getDataToken(context);
        if(token == null || token.isEmpty()) {
            mTokenInvalid.setValue(true);
            return;
        }
        // call a la api para verificar is es valido el user
        Call<Propietario> propReq = api.GetPerfil(token);
        propReq.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Log.d(TAG, "onResponse: code " + response.code());
                if (response.isSuccessful()) {

                 // codigo  entre 200 y 300, token autorizado, mostrar perfil propietario
                   mTokenValid.setValue(true);
                   return;
                }
                    // invalido
                    mTokenInvalid.setValue(true);
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Log.e(TAG, "Error en la solicitud de token: " + throwable.getMessage());
            }
        });
    }

    /**
     * Verifica si  JWT es valido
     */
//   //}



    /**
     *  registra el reciver  para verificar cambios en la conectividad a internet
     */
    private void registerConnectivityReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(new ConnectivityReceiver(), filter);
    }

    /**
     * anula registro reciverr cuando clear viewmodel
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        context.unregisterReceiver(new ConnectivityReceiver());
    }

    /**
     * crea eciver para detectra cambios  conectividad a internet
     */
    public class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isInternet() && mInternet.getValue() == null) {
                mInternet.setValue(false);
            } else {
             //   checkJwt();
            }
        }

        /**
         * verify  Internet
         * consional segun SDK
         * @return true si hay internet, false si no
         */
        private boolean isInternet() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // android 6.0 (API >23)
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            } else {
                // android 5.1 (API <=23)
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }
    }
}

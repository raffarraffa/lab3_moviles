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

    private static final String TAG = "MainViewModel"; // tag para log
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

    /**
     * verificacion  token presente
     *
     * @return true si el token resente, false si ausente
     */
    private boolean isPresentToken() {
        String token = ApiData.leerData(context, AppParams.PREFERENCES_DATA, AppParams.TOKEN_KEY);
        Log.d(TAG, "isPresentToken: 85 " + token);
        if (token == null || token.isEmpty()) {
            //
            // mTokenInvalid.setValue(true);
            return false;
        }
        return true;
    }

    /**
     * verifica tsoken valido con una solicitud a la API
     */
    private void isValidToken() {
        String token = "Bearer " + ApiData.leerData(context, AppParams.PREFERENCES_DATA, AppParams.TOKEN_KEY);
        Call<Propietario> propReq = api.GetPerfil(token);
        propReq.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "isValidToken: 103 " + response.body().toString());
                    mTokenValid.setValue(true);
                 //   Log.d(TAG, "onResponse: ");
                } else {
                    mTokenInvalid.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Log.e(TAG, "Error en la solicitud de token: " + throwable.getMessage());
                mTokenInvalid.setValue(true);
            }
        });

    }

    /**
     * Verifica si  JWT es valido
     */
    private void checkJwt() {
        if (isPresentToken()) {
            isValidToken();
        } else {
            Log.d(TAG, "checkJwt: No hay jwt");
        }
    }

    /**
     * inicia la verificacio  token al iniciar la aplicacin
     *
     * @return String  inicio del proceso
     */
    public String isInmobiliariaOk() {
        checkJwt();
        return "iniciando";
    }

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
                checkJwt();
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

package com.rafalopez.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel  inicio de SESSION
 */
public class LoginViewModel extends AndroidViewModel {   
    private static final String TAG = "LoginViewModel";
    private final Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private final ApiClient.InmobiliariaServices api;

    /**
     * cnstructor de LoginViewModel
     * @param application contexto de la aplicacin
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
        Log.d(TAG, "LoginViewModel: ");
    }

    /**
     * Obtiene LiveData que contiene el propietario
     * @return LiveData de tipo Propietario
     */
    LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    /**
     * Obtiene LiveData que indica si hubo un error en el inicio de SESSION
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMLoginError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
        }
        return mLoginError;
    }

    /**
     * Obtiene LiveData que indica si el inicio de SESSION fue exitoso
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMLoginOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
        }
        return mLoginOk;
    }

    /**
     * Obtiene LiveData que contiene el mensaje de error en el inicio de SESSIION
     * @return LiveData de tipo String
     */
    LiveData<String> getMsgLoginError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }

    /**
     * Inicia SESSION para el usuario
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {
        User user = new User(email, password);
        Call<User> req = api.PostLogin(user);
        req.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User userReq = response.body();
                    boolean isSavetoken = ApiData.guardarData(context, AppParams.PREFERENCES_DATA, userReq.getToken(), AppParams.TOKEN_KEY);
                    boolean isSavePropietario = ApiData.guardarData(context, AppParams.PREFERENCES_DATA, userReq.getPropietario(), AppParams.PROPIETARIO_KEY);
                    String propietario = ApiData.leerData(context, AppParams.PREFERENCES_DATA, AppParams.PROPIETARIO_KEY);
                    Log.d(TAG, "onResponse: propietario " + isSavePropietario + "\n" + propietario);
                    mLoginOk.setValue(true);
                    Log.d(TAG, "onResponse: " + userReq.getToken());
                } else {
                    Log.d(TAG, "onResponse: error en el inicio de SESSION");
                    mLoginError.setValue(true);
                    mLoginMsgError.setValue("Error en el inicio de sESESION");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                mLoginMsgError.setValue("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /**
     * Verifica el token de datos.
     */
    public void checkDataToken() {
        String token = ApiData.leerData(context, AppParams.PREFERENCES_DATA, AppParams.TOKEN_KEY);
        Log.d(TAG, "checkDataToken: " + token);
    }
}

package com.rafalopez.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel  inicio de sesión
 */
public class LoginViewModel extends AndroidViewModel {
    private static final String PREFERENCES_DATA = "dataInmobiliaria";
    private static final String TOKEN_KEY = "token";
    private static final String PROPIETARIO_KEY = "propietario";
    private static final String TAG = "LoginViewModel";
    private final Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private final ApiClient.InmobiliariaServices api;

    /**
     * Constructor de LoginViewModel.
     *
     * @param application contexto aplciacion.
     */
    public LoginViewModel(@NonNull Application application) {

        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
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
     * GET LiveData  error en el inicio de sesión
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMLoginError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
            mLoginError.setValue(false);
        }
        return mLoginError;
    }

    /**
     * GET LiveData  sesión fue exitoso
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMLoginOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
        }
        return mLoginOk;
    }

    /**
     * GET LiveData  error en el inicio de SESSION
     *
     * @return LiveData de tipo String
     */
    LiveData<String> getMsgLoginError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }

    /**
     * Inicio SESSION usuario
     *
     * @param email    Correo  del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {
        User user = new User(email, password);
        Call<User> req = api.PostLogin(user);
        Toast.makeText(context,"linea109",Toast.LENGTH_SHORT).show();
        req.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User userReq = response.body();
                   boolean isSavetoken = ApiData.guardarData(context,PREFERENCES_DATA,userReq.getToken().toString(), TOKEN_KEY);
                   boolean isSavePropietario = ApiData.guardarData(context,PREFERENCES_DATA, userReq.getPropietario(),PROPIETARIO_KEY);
                       mLoginOk.setValue(true);
                    Log.d(TAG, "onResponse: " + userReq.getToken());
                    Log.d(TAG, "onResponse 117: " + response.body());
                } else {
                    mLoginError.setValue(true);
                    mLoginMsgError.setValue("Error en el inicio de sesion.");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
//                mLoginError.setValue(true);
                mLoginMsgError.setValue("Error de conexion: " + throwable.getMessage());
            }
        });
    }
    public void checkDataToken(){
        String token=ApiData.leerData(context,PREFERENCES_DATA,TOKEN_KEY);
        Log.d(TAG, "checkDataToken: " + token);

    }
}

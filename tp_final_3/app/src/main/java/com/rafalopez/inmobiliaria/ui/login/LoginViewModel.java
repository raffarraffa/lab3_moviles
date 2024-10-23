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
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel  inicio de SESSION
 */
public class LoginViewModel extends AndroidViewModel {   
    private static final String TAG = "salidaDebug";
    private final Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<String> mLoginOk;
    private MutableLiveData<String> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private MutableLiveData<String> mRestoreResult;
    private final ApiClient.InmobiliariaServices api;

    /**
     * cnstructor de LoginViewModel
     * @param application contexto de la app
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
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
    LiveData<String> getMLoginError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
        }
        return mLoginError;
    }


    /**
     * Obtiene LiveData que indica si el inicio de SESSION fue exitoso
     * @return LiveData de tipo Boolean
     */
    LiveData<String> getMLoginOk() {
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
     * Obtiene LiveData que indica si hubo un error en el inicio de SESSION
     * @return LiveData de tipo Boolean
     */
    LiveData<String> getMRestoreResult() {
        if (mRestoreResult == null) {
            mRestoreResult = new MutableLiveData<>();
        }
        return mRestoreResult;
    }

    /**
     * Inicia SESSION para el usuario
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {
        User user = new User(email, password);
        Call<String> req = api.PostLogin(user);
        req.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse:100 " +response.body());
                     //todo hacer algo con los booleanos
                    boolean isSavetoken = ApiData.guardarData(context,
                            AppParams.PREFERENCES_DATA,
                            "Bearer " + response.body(),
                            AppParams.TOKEN_KEY);
                    //todo disparar mutable LOGIN OK
                    mLoginOk.setValue("Login ok");
                   } else {
                    mLoginError.setValue("Error de acceso");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mLoginMsgError.setValue("Error de conexión: " + throwable.getMessage());
                Log.e(TAG, "onFailure:112 " + throwable.getMessage());
            }
        });
    }

    /**
     *
     * @param email
     * @param password
     */
//    public void passwordRestore2(String email, String password) {
//        User user = new User(email, password);
//        Call<String> req = api.PostRestore(user);
//        req.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.d(TAG, "onResponse:139 " +response);
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "onResponse:100 " +response.body());
//                    //todo hacer algo con los booleanos
//
//                    //todo disparar mutable LOGIN OK
//                  //  mRestoreResult.setValue(response.body().toString());
//                } else {
//                    Log.d(TAG, "onResponse:100 " +response.body());
//                    mLoginError.setValue("Error Intente  Nuevamente");
//                }
//            }
//            @Override
//            public void onFailure(Call<String> call, Throwable throwable) {
//                mLoginMsgError.setValue("Error de conexión: " + throwable.getMessage());
//                Log.e(TAG, "onFailure:156 " + throwable.getMessage());
//            }
//        });
//    }
    public void passwordRestore(String email, String password) {
        User user = new User(email, password);
        Call<ResMsg> req = api.PostRestore(user);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                Log.d(TAG, "onResponse:139 " +response.message());
                Log.d(TAG, "onResponse:139 " +response.body().toString());
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "onResponse:100 " +response.body());
//                    //todo hacer algo con los booleanos
//                    boolean isSavetoken = ApiData.guardarData(context,
//                            AppParams.PREFERENCES_DATA,
//                            "Bearer " + response.body(),
//                            AppParams.TOKEN_KEY);
//                    //todo disparar mutable LOGIN OK
//                    mLoginOk.setValue("Login ok");
//                } else {
//                    mLoginError.setValue("Error de acceso");
//                }
            }
            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
                mLoginMsgError.setValue("Error de conexión: " + throwable.getMessage());
                Log.e(TAG, "onFailure:112 " + throwable.getMessage());
            }
        });
    }
}

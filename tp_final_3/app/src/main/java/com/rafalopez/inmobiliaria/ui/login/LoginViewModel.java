package com.rafalopez.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel  inicio de SESSION
 */
public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    // mutables
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<String> mLoginOk;
    private MutableLiveData<String> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private MutableLiveData<String> mRestoreResultOk;
    private MutableLiveData<Boolean> mShake;
    // acelrometro
    private float acelNewValue;
    private float acelOldValue;
    private float shake;


    /**
     * cnstructor de LoginViewModel
     *
     * @param application contexto de la app
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
    }

    /**
     * Obtiene MutableLiveData que contiene el propietario
     *
     * @return MutableLiveData de tipo Propietario
     */
    MutableLiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    /**
     * Obtiene MutableLiveData que indica si hubo un error en el inicio de SESSION
     *
     * @return MutableLiveData de tipo Boolean
     */
    MutableLiveData<String> getMLoginError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
        }
        return mLoginError;
    }


    /**
     * Obtiene MutableLiveData que indica si el inicio de SESSION fue exitoso
     *
     * @return MutableLiveData de tipo Boolean
     */
    MutableLiveData<String> getMLoginOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
        }
        return mLoginOk;
    }

    /**
     * Obtiene MutableLiveData que contiene el mensaje de error en el inicio de SESSIION
     *
     * @return MutableLiveData de tipo String
     */
    MutableLiveData<String> getMsgLoginError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }

    /**
     * Obtiene MutableLiveData que indica si hubo un error en el inicio de SESSION
     *
     * @return MutableLiveData de tipo Boolean
     */
    MutableLiveData<String> getMRestoreResultOk() {
        if (mRestoreResultOk == null) {
            mRestoreResultOk = new MutableLiveData<>();
        }
        return mRestoreResultOk;
    }

    MutableLiveData<Boolean> getmShake(){
        if(mShake==null){
            mShake= new MutableLiveData<Boolean>();
        }
        return mShake;
    }

    /**
     * Inicia SESSION para el usuario
     *
     * @param email    Correo del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {

        User user = new User(email, password);

        Call<String> req = api.PostLogin(user);
        req.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //todo hacer algo con los booleanos
                    String token = "Bearer " + response.body();
                    boolean isSavetoken = ApiData.setDataToken(context, token);
                    //todo disparar mutable LOGIN OK
                    mLoginOk.setValue("Login ok");
                } else {
                    mLoginError.setValue("Error de acceso");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mLoginMsgError.setValue("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /**
     * @param email
     */
    public void passwordRestore(String email) {

        User user = new User();
        user.setEmail(email);
        Call<ResMsg> req = api.PostRestore(user);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                ResMsg msg = response.body();

                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + msg.toString());
                    mRestoreResultOk.setValue("Se ha enviado un Email \n Verifique el codigo y link recibido");
                } else {
                    mRestoreResultOk.setValue("Error de Recupereracion");
                }
            }

            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
                mRestoreResultOk.setValue("Error de conexión: " + throwable.getMessage());
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void sensorShake() {
        SensorManager sensorShake = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensores = sensorShake.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!sensores.isEmpty()) {
            Sensor acelerometro = sensores.get(0);
            sensorShake.registerListener(new GetAcel(), acelerometro, SensorManager.SENSOR_DELAY_GAME);

        }


    }

    public class GetAcel implements SensorEventListener{
        private StringBuilder acelStr= new StringBuilder();
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float acelX=sensorEvent.values[0];
            float acelY=sensorEvent.values[1];
            float acelZ=sensorEvent.values[2];

            acelOldValue = acelNewValue;
            acelNewValue = (float) Math.sqrt(acelX * acelX + acelY * acelY + acelZ * acelZ);
            float delta = acelNewValue - acelOldValue;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
          //      Toast.makeText(context, acelX + " " + acelY + " " + acelZ + " " , Toast.LENGTH_LONG  ).show();
                Log.e(TAG, "onSensorChanged: " + acelX + " " + acelY + " " + acelZ   );
                mShake.setValue(true);          
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }
}


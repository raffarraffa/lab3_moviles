package com.rafalopez.inmobiliaria.ui.propietario;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropietarioViewModel extends AndroidViewModel {
    private ActionMutable actionMutable =new ActionMutable();
    private static final String TAG = "salida";
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;
    private MutableLiveData<String> mBtnAction;
    private MutableLiveData<ActionMutable> mBtnAction2;
    private final Context context;


    public PropietarioViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        this.api = ApiClient.getApiInmobiliaria();
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
     * GET LiveData
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
            mLoginError.setValue(false);
        }
        return mLoginError;
    }

    /**
     * GET LiveData
     *
     * @return LiveData de tipo Boolean
     */
    LiveData<Boolean> getMOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
        }
        return mLoginOk;
    }

    /**
     * GET LiveData  error
     *
     * @return LiveData de tipo String
     */
    LiveData<String> getMsgError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }
    /**
     * GET LiveData  BtnAction
     *
     * @return LiveData de tipo String
     */
    LiveData<String> getMBtnAction() {
        if (mBtnAction == null) {
            mBtnAction = new MutableLiveData<>();
        }
        return mBtnAction;
    }
    LiveData<ActionMutable> getMBtnAction2() {
        if (mBtnAction2 == null) {
            mBtnAction2 = new MutableLiveData<>();
        }
        return mBtnAction2;
    }

//
public void getProfile() {
       String token = ApiData.getDataToken(context);
    Call<Propietario> req = api.GetPerfil(token);
    req.enqueue(new Callback<Propietario>() {
        @Override
        public void onResponse(Call<Propietario> call, Response<Propietario> response) {
            Propietario propietario = response.body();
            boolean isPropietarioSaved = ApiData.guardarDataPropietario(context,AppParams.PREFERENCES_DATA, propietario);
            mPropietario.setValue(propietario);
//                String jsonInmuebles = new Gson().toJson(propietario.getInmuebles());
//                boolean isInmublesSaved=ApiData.guardarData(context,AppParams.PREFERENCES_DATA,jsonInmuebles, AppParams.INMUEBLE_KEY);
            Log.d(TAG, "onResponse: 41" + propietario);

        }

        @Override
        public void onFailure(Call<Propietario> call, Throwable throwable) {

        }
    });
}
//
    public void getPropietario(){
        Propietario propietario = ApiData.leerDataPropietario(context);
        Log.d(TAG, "getPropietario: 111" + propietario);
        if(propietario!=null){
            mPropietario.setValue(propietario);
            return;
        }
        mLoginMsgError.setValue("Error de session  logerse");
    }
    public void setActionBtn(String  action){
        switch (action){
            case "Editar":
                mBtnAction.setValue("Guardar");
                // acciones de edicion
                break;
            case "Guardar":

                mBtnAction.setValue("Editar");
                // acciones de guardado
                break;

        }
    }
    public void setActionBtn2(String  action, Propietario prop){
        Log.d(TAG, "setActionBtn2: " + action);
        switch (action){
           case "Editar":
               Log.d(TAG, "setActionBtn2: " + prop.toString());
                actionMutable.setAction("Guardar");
                actionMutable.setVisible(true);
                Log.d(TAG, "setActionBtn2: " + actionMutable.toString());
                mBtnAction2.setValue(actionMutable);
                // acciones de edicion
                break;
            case "Guardar":
                Log.d(TAG, "setActionBtn2: " + prop.toString());
                actionMutable.setVisible(false);
                actionMutable.setAction("Editar");
                mBtnAction2.setValue(actionMutable);
                // acciones de guardado
                updatePerfil(prop);

                break;
        }
    }
    public void updatePerfil(Propietario propToUpdate){
        Log.d(TAG, "updatePerfil: " + propToUpdate.toString());
        Call<Propietario> req = api.PatchPerfil(ApiData.getDataToken(context),propToUpdate);
        req.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body() +"");
                } else {
                    Log.e(TAG,
                            "Error: " + response.code() + " - " + response.message() + " - " + response.body());
                }
//                Toast.makeText(context,"uptade linea 159",Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onResponse:160 " + response.code());
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {

            }
        });

    }

    // TODO: Implement the ViewModel

}
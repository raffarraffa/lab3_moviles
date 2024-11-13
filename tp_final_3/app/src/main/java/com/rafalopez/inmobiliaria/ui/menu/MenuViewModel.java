package com.rafalopez.inmobiliaria.ui.menu;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel para gestionar la lógica de la interfaz de usuario del menu
 *
 * Esta clase se encarga de obtener el perfil del propietario y gestionar las perticones ala API
 */
public class MenuViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private final String token;
    private Propietario propietario;
    MutableLiveData<Propietario>mPropietario;

    /**
     * Constructor del MenuViewModel
     *
     * @param application La app que proporciona el contexto
     */
    public MenuViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
        token = ApiData.leerData(context, AppParams.PREFERENCES_DATA, AppParams.TOKEN_KEY);
    }

    /**
     *  Obtien instancia MytableLiveData
     *
     * @return  Inatancia MutalbeliveData tipo propietario
     */
    public  MutableLiveData<Propietario> getmPropietario (){
        if(mPropietario==null){
            mPropietario = new MutableLiveData<Propietario>();
        }
        return mPropietario;
    }

    /**
     * Obtiene el perfil del propietario desde la API
     *
     * Realiza una llamada a la API para obtener los datos del perfil del propietario
     * y guarda  en sharepreferencesde la app
     */

    public void getProfile() {
        Call<Propietario> req = api.GetPerfil(token);
        req.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                propietario = response.body();
                boolean isProrietarioSaved = ApiData.guardarDataPropietario(context, AppParams.PREFERENCES_DATA, propietario);
                mPropietario.postValue(propietario);
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
            }
        });

    }
}

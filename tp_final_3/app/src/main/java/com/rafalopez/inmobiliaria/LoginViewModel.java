package com.rafalopez.inmobiliaria;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.services.PropietarioServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends AndroidViewModel {
    private static final String PREFERENCES_NAME = "datos";
    private static final String TOKEN_KEY = "token";
    private static final String TAG = "LoginViewModel";
    private static Context context;
    private PropietarioServices propServ;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginOk;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        propServ = new PropietarioServices(context);
    }

    LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    LiveData<Boolean> getMLoginError() {
        if (mLoginError == null) {
            mLoginError = new MutableLiveData<>();
            mLoginError.setValue(false);
        }
        return mLoginError;
    }
    LiveData<Boolean> getMLoginOk() {
        if (mLoginOk == null) {
            mLoginOk = new MutableLiveData<>();
       //     mLoginOk.setValue(false);
        }
        return mLoginOk;
    }

    LiveData<String> getMsgLoginError() {
        if (mLoginMsgError == null) {
            mLoginMsgError = new MutableLiveData<>();
        }
        return mLoginMsgError;
    }
    public void loginUser(String email, String password){
        User user= new User(email,password);
        propServ.login(user, new Callback<String>(){

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response!=null || response.body()!=null) {
                                    Log.d(TAG, "onResponse: " + response.body());
                                    mLoginOk.setValue(true);
                                }else{
                                    Log.d(TAG, "onResponse:  eror");
                                }


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable throwable) {

                            }
                        });
    }
    public  void checkDataToken(){
        String token=propServ.obtenerToken();

        Toast.makeText(context,token,Toast.LENGTH_SHORT).show();
    }

}


/*
    public void checkToken(){
        String token = ApiData.leerData(context);
        if( token!=null){
            ApiClient.InmobiliariaServices api= ApiClient.getApiInmobiliaria();
            Call<Propietario> request = api.GetPerfil(token);
            Log.d("salida", "checkToken: 61 response.body()");
            request.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    Log.d("salida", "checkToken: 64 " + response.body());
                    if(response.body()==null){
                        ApiData.guardarData(context,"");
                    }
                    Log.d("salida", "checkToken: 68 " + response.body());

                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {

                }
            });
            ApiData.guardarData(context,"h");
            Log.d("salida", "checkToken: 78 " + token);
        }else{

        }
        Log.d("salida", "checkToken: 61 " + token);
    }
*/
/*

public void checkDataToken(){
    String token = ApiData.leerData(context, "datos", "token"){

    Log.d("salida", "checkDataToken: " + token);
    ApiClient.InmobiliariaServices api = ApiClient.getApiInmobiliaria();
    String auth = "Bearer " + token;
    Log.i("salida", "Código de estado 89: " + auth);
    Call<String> request = api.GetPerfil(auth);
    request.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            Log.i("salida", "Código de estado 94: " + response.body());
            Log.i("salida", "Código de estado 95: " + response.code());
            return null;
        }

        @Override
        public void onFailure(Call<String> call, Throwable throwable) {

        }
    });
}
*/
/*
    public void loginUser(String email, String password){

        User user= new User(email,password);
        ApiClient.InmobiliariaServices api = ApiClient.getApiInmobiliaria();
        Call<String> request =api.PostLogin(user);

        request.enqueue(new Callback<String>() {
    // gestion peticion
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() == null){
                    // no recibo token
                    mLoginMsgError.setValue("asdfaf");
                    Log.i("salida", "Código de estado 98: " + response.toString());
                }else{
                    //reibo token
                    Gson gson= new Gson();
                    Map<String, Object> map = gson.fromJson(response.body(), Map.class);
                    String token = (String) map.get("token");
                    Log.i("salida", "Código de estado 105: " + token);
                    Boolean resultado = ApiData.guardarData(context, );
                }
                return null;
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("salida", "loginUser: " + user.toString());
                Toast.makeText(context, "entro 114 ",Toast.LENGTH_LONG).show();
                mLoginError.setValue(!mLoginError.getValue());
            }
        });
    }

}
*/
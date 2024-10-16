package com.rafalopez.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mLoginError;
    private MutableLiveData<String> mLoginMsgError;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context =application.getApplicationContext();
    }
    LiveData<Propietario> getMPropietario (){
        if(mPropietario==null){
            mPropietario =new MutableLiveData<>();
        }
        return mPropietario;
    }
    LiveData<Boolean> getMLoginError (){
        if(mLoginError==null){
            mLoginError =new MutableLiveData<>();
            mLoginError.setValue(false);
        }
        return mLoginError;
    }
    LiveData<String> getMsgLoginError (){
        if(mLoginMsgError==null){
            mLoginMsgError =new MutableLiveData<>();
        }
        return mLoginMsgError;
    }
//    public void checkToken(){
//        String token = ApiData.leerData(context);
//        if( token!=null){
//            ApiClient.InmobiliariaServices api= ApiClient.getApiInmobiliaria();
//            Call<Propietario> request = api.GetPerfil(token);
//            Log.d("salida", "checkToken: 61 response.body()");
//            request.enqueue(new Callback<Propietario>() {
//                @Override
//                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
//                    Log.d("salida", "checkToken: 64 " + response.body());
//                    if(response.body()==null){
//                        ApiData.guardarData(context,"");
//                    }
//                    Log.d("salida", "checkToken: 68 " + response.body());
//
//                }
//
//                @Override
//                public void onFailure(Call<Propietario> call, Throwable throwable) {
//
//                }
//            });
//            ApiData.guardarData(context,"h");
//            Log.d("salida", "checkToken: 78 " + token);
//        }else{
//
//        }
//        Log.d("salida", "checkToken: 61 " + token);
//    }
public void checkDataToken(){
    String token = ApiData.leerData(context);

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
                    ApiData.guardarData(context,token);
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

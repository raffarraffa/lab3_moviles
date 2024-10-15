package com.rafalopez.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;

import retrofit2.Call;


public class LoginViewModel extends AndroidViewModel {
    private Context context;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context =application.getApplicationContext();
    }

    public void loginUser(String email, String password){
        User user=null;
        ApiClient.InmobiliariaServices api = ApiClient.getApiInmobiliaria();
        Call<String> request =api.PostLogin(user);

    }

}

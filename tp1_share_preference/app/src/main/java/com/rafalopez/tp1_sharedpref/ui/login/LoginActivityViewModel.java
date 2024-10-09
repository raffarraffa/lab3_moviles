package com.rafalopez.tp1_sharedpref.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.tp1_sharedpref.request.ApiCliente;
import com.rafalopez.tp1_sharedpref.ui.profile.ProfileActivity;
import com.rafalopez.tp1_sharedpref.model.Usuario;
import com.rafalopez.tp1_sharedpref.utils.UtilsValidation;

public class LoginActivityViewModel extends AndroidViewModel {
    private Intent intent;
    private Context context;
    MutableLiveData<String> mErrorMail;
    MutableLiveData<String> mErrorLogin;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }
    public LiveData<String> getMErrorMail(){
        if(mErrorMail==null){
            mErrorMail= new MutableLiveData<>();
        }
        return mErrorMail;
    }
    public LiveData<String> getMErrorLogin(){
        if(mErrorLogin==null){
            mErrorLogin= new MutableLiveData<>();
        }
        return mErrorLogin;
    }

    public void loginUser(Usuario user) {
        //validacion email
        if (!UtilsValidation.isValidEmail(user.getEmail())){
            Toast.makeText(context, "Email invalido ", Toast.LENGTH_SHORT).show();
            mErrorMail.setValue("Email invalido");
            return;
        }
        // validacion usuario
        Usuario usuarioValidado = ApiCliente.login(context,user.getEmail(),user.getPass());
        if(usuarioValidado == null){
            Toast.makeText(context, "Error de credenciales \n Intente nuevamente ", Toast.LENGTH_SHORT).show();
            mErrorLogin.setValue("Error Credenciales");
            return;
        }

        Log.d("salida", "loginUser: viewActivity l-69 " + user.toString());
            intent = new Intent(context, ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("login",true);
            context.startActivity(intent);

    }
    public void registerUser(){
        intent =new Intent(context, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
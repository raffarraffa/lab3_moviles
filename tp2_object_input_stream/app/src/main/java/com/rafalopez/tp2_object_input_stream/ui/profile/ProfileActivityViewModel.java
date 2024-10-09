package com.rafalopez.tp2_object_input_stream.ui.profile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rafalopez.tp2_object_input_stream.model.Usuario;
import com.rafalopez.tp2_object_input_stream.request.ApiCliente;
import com.rafalopez.tp2_object_input_stream.ui.login.LoginActivity;


public class ProfileActivityViewModel extends AndroidViewModel {
    private Context context;
    public MutableLiveData<Usuario> mUsuario =new MutableLiveData<>();
    public  MutableLiveData<Integer> mRegistroError;
    public ProfileActivityViewModel(@NonNull Application application){
        super(application);
        context=application.getApplicationContext();
    }
    public LiveData<Usuario> getMusuario(){
        if(mUsuario==null){
            mUsuario= new MutableLiveData<>();
        }
        return mUsuario;
    }

    public LiveData<Integer> getMRegistroError(){
        if(mRegistroError==null){
            mRegistroError= new MutableLiveData<>();
        }
        return mRegistroError;
    }

    public void setRegistro(Usuario u){
        if(!u.isValid()){
            mRegistroError.setValue(0);
            Toast.makeText(context, "Error de registro \n verifique datos",Toast.LENGTH_LONG).show();
            return;
        }
        ApiCliente.guardar(context,u);
        Toast.makeText(context, "Datos guardados \n Identifiquese",
                Toast.LENGTH_LONG).show();
        mUsuario.setValue(u);
        Intent intent=new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void getRegistro(boolean login){
        if(!login) return ;
        Usuario u = ApiCliente.leerDatos(context);
        if(u==null) return;
        mUsuario.setValue(u);
    }
    public int verifyDni(String dni ){
        if(dni.isEmpty()){
            return 0;
        }
        return  Integer.parseInt(dni);
    }
}
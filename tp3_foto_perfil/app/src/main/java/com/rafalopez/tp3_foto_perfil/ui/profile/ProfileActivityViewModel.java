package com.rafalopez.tp3_foto_perfil.ui.profile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.rafalopez.tp3_foto_perfil.model.Usuario;
import com.rafalopez.tp3_foto_perfil.request.ApiCliente;
import com.rafalopez.tp3_foto_perfil.ui.login.LoginActivity;


public class ProfileActivityViewModel extends AndroidViewModel {
    private Context context;
    public MutableLiveData<Usuario> mUsuario =new MutableLiveData<>();
    public  MutableLiveData<Integer> mRegistroError;
    public MutableLiveData<String> mImg;
    //public MutableLiveData<Boolean> mPermiso;
   // private ActivityResultLauncher<Intent> galeriaLauncher;
    private ActivityResultLauncher<String> permisoLauncher;

    public ProfileActivityViewModel(@NonNull Application application){
        super(application);
        context=application.getApplicationContext();
      //  initPermisoLauncher();
       // getPermiso();
    }
    private void initPermisoLauncher() {
   //     permisoLauncher = getActivityResultLauncher();
    }

//    private ActivityResultLauncher<String> getActivityResultLauncher() {
//        return new ActivityResultLauncher<String>() {
//            @Override
//            public void launch(String input, @Nullable ActivityOptionsCompat options) {
//                // Aquí se lanza el permiso
//            }
//        }
//    }

    //    private void initPermisoLauncher() {
//        permisoLauncher = new ActivityResultLauncher<String>() {
//            @Override
//            public void launch(String s, @Nullable ActivityOptionsCompat activityOptionsCompat) {
//                context.startActivity(new Intent(context, ProfileActivity.class));
//            }
//
//            @Override
//            public void onActivityResult(int resultCode, Intent data) {
//                // Este método no se usa en este contexto, se usa el onRequestPermissionsResult en la actividad principal
//                if (resultCode == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(context, "Permiso concedido", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show();
//                }
//            }
//
////            @Override
////            public void unregister() {
////
////            }
//
////            @NonNull
////            @Override
////            public ActivityResultContract<String, ?> getContract() {
////                return null;
////            }
//        }
//  }
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
    public LiveData<String> getMImg(){
        if(mImg==null){
            mImg=new MutableLiveData<>();
        }
        return mImg;
    }

    public void setRegistro(Usuario u){
     //   getPermiso();
        if(!u.isValid()){

            mRegistroError.setValue(0);
            Toast.makeText(context, "Error de registro \n verifique datos",Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("salida", "setRegistro: 59 " + u);
        ApiCliente.guardar(context,u);
        Toast.makeText(context, "Datos guardados \n Identifiquese",
                Toast.LENGTH_LONG).show();
        mUsuario.setValue(u);
        Intent intent=new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void getPermiso(){
        if(PermissionChecker.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)== PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(context,"permiso  77 -> " + PermissionChecker.PERMISSION_GRANTED ,
                    Toast.LENGTH_LONG).show();

        }else{
            permisoLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            Toast.makeText(context,"permiso 80  -> " + PermissionChecker.PERMISSION_GRANTED ,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void getRegistro(boolean login){
        if(!login) return ;
        Usuario u = ApiCliente.leerDatos(context);
        Log.d("salida", "usuario VM linea 70 : " + u.toString());

        if(u==null) return;
        mUsuario.setValue(u);
    }
    public int verifyDni(String dni ){
        if(dni.isEmpty()){
            return 0;
        }
        return  Integer.parseInt(dni);
    }
    public Uri sacarFoto(){

        Uri photoUri=null;
        Toast.makeText(context,"60 view model profile", Toast.LENGTH_LONG).show();
        return photoUri;
    }
    public void imgCapture(ActivityResult result){
        Log.d("salida", "imgCapture: " + result.toString());
    }
    public void iniciarGaleria(ActivityResult result){
        Uri imgPerfil = result.getData().getData();
        if(result!=null){
            mImg.setValue( result.getData().getData().toString());
        }


        Log.d("galeria", "iniciarGaleria: " + result.getData().getData().toString());
    }
}
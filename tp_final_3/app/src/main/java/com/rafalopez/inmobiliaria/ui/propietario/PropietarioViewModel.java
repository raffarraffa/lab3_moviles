package com.rafalopez.inmobiliaria.ui.propietario;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.request.ApiClient;
import com.rafalopez.inmobiliaria.utils.RealPathUtil;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private MutableLiveData<Boolean> mBtnAvatar;
    private MutableLiveData<ActionMutable> mBtnAction2;
    private MutableLiveData<Uri> mUriImage;
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
     *
     * @return instgancia mutable
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
    LiveData<Uri> getMUri() {
        if(mUriImage==null) {
            mUriImage = new MutableLiveData<>();
        }
        return mUriImage;
    }
    LiveData<Boolean> getMBtnAvatar() {
        if(mBtnAvatar==null) {
            mBtnAvatar = new MutableLiveData<>();
        }
        return mBtnAvatar;
    }
    public void getProfile() {
       String token = ApiData.getDataToken(context);
    Call<Propietario> req = api.GetPerfil(token);
    req.enqueue(new Callback<Propietario>() {
        @Override

        public void onResponse(Call<Propietario> call, Response<Propietario> response) {
            Propietario propietario = response.body();
            boolean isPropietarioSaved = ApiData.guardarDataPropietario(context,AppParams.PREFERENCES_DATA, propietario);
            mPropietario.setValue(propietario);
            Log.d(TAG, "onResponse: 41" + propietario);
        }
        @Override
        public void onFailure(Call<Propietario> call, Throwable throwable) {
        }
    });
}
    public void getPropietario(){
        Propietario propietario = ApiData.leerDataPropietario(context);
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
                actionMutable.setAction("Guardar");
                actionMutable.setVisible(true);
                mBtnAction2.setValue(actionMutable);
                break;
            case "Guardar":
                Log.d(TAG, "setActionBtn2: 184" + prop.toString());
                actionMutable.setVisible(false);
                actionMutable.setAction("Editar");
                mBtnAction2.setValue(actionMutable);
                updatePerfil(prop);
                break;
        }
    }
    public void updateAvatar(Uri uriImage){
        String token = ApiData.getDataToken(context);
        File file;
        String path=null;
        String mime;
        if (uriImage != null) {
            path = RealPathUtil.getRealPath(context, uriImage);
            mime = RealPathUtil.getMimeTypeFromUri2(context, uriImage);
            file = new File(path);
            if (file.length() != 0) {
                RequestBody fileBody = RequestBody.create(file, MediaType.parse(mime));
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", file.getName(), fileBody);
                Call<ResMsg> req = api.UploadAvatar(token, imagePart);
                req.enqueue(new Callback<ResMsg>() {
                    @Override
                    public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                        if (response.isSuccessful()) {
                            getProfile();
                            Log.d(TAG, "Inmueble creado con éxito: " + response.body());
                        } else {
                            Log.e(TAG, "Error en la respuesta: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResMsg> call, Throwable throwable) {
                        Log.e(TAG, "Error al hacer la llamada", throwable);
                    }
                });
            }
        }
    }
    public void updatePerfil(Propietario propToUpdate){
        Call<Propietario> req = api.PatchPerfil(ApiData.getDataToken(context),propToUpdate);
        req.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.postValue(response.body());
                    Log.d(TAG, "view model porpietario linea 213"+response.body() +"");
                } else {
                    Log.e(TAG,
                            "Error: " + response.code() + " - " + response.message() + " - " + response.body());
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
            }
        });
    }
    public void setImage(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            mUriImage.postValue(uri);
            updateAvatar(uri);
        }
    }
    public  void verificarAvatarEditable(){
            if(Objects.equals(actionMutable.getAction(), "Guardar")) {
                mBtnAvatar.setValue(true);
            }
    }
}
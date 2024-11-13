package com.rafalopez.inmobiliaria.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.entity.InmuebleDto;
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.request.ApiClient;
import com.rafalopez.inmobiliaria.utils.RealPathUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleNewViewModel extends AndroidViewModel {
    private static final String TAG = "salidaDebug";
    private final Context context;
    private final ApiClient.InmobiliariaServices api;
    private MutableLiveData<Boolean> mResultOk;
    private MutableLiveData<String> mMsg;
    private MutableLiveData<Boolean> mPermisoGaleria;
    private MutableLiveData<Uri> mUriImage;

    /**
     * cnstructor de InmuebleNewViewModel
     * @param application contexto de la app
     */

    public InmuebleNewViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = ApiClient.getApiInmobiliaria();
    }
    LiveData<Boolean> getMResultOk() {
        if (mResultOk == null) {
            mResultOk = new MutableLiveData<>();
        }
        return mResultOk;
    }
    LiveData<Boolean> getmPermisoGaleria() {
        if (mPermisoGaleria == null) {
            mPermisoGaleria = new MutableLiveData<>();
        }
        return mPermisoGaleria;
    }
    LiveData<String> getMMsg() {
        if (mMsg == null) {
            mMsg = new MutableLiveData<>();
        }
        return mMsg;
    }
    LiveData<Uri> getMUri() {
        if(mUriImage==null) {
            mUriImage = new MutableLiveData<>();
        }
        return mUriImage;
    }

    public void crearInmueble(InmuebleDto inmuebleDto, Uri uriImage) {
        String token = ApiData.getDataToken(context);
        String inmuebleStr;
        File file;
        // Se obtiene el path de la imagen
        String path = "drawable/logo_final.webp";
        String mime= null;
        if (uriImage != null) {
            path = RealPathUtil.getRealPath(context, uriImage);
            mime = RealPathUtil.getMimeTypeFromUri2(context,uriImage);
        }
        file = new File(path);

        // Convertir el objeto inmuebleDto a un String JSON
        inmuebleStr = new Gson().toJson(inmuebleDto);

        // Crear el RequestBody para el JSON del inmueble
        RequestBody inmueble = RequestBody.create(inmuebleStr, MediaType.parse("application/json"));

        // Crear el RequestBody para la imagen
        RequestBody fileBody = RequestBody.create(file, MediaType.parse(mime));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", file.getName(), fileBody);


        // Realizar la solicitud Retrofit
        Call<ResMsg> req = api.CreateInmueble(token, inmueble, imagePart);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Inmueble crerado con exito ",Toast.LENGTH_SHORT).show();
                    mResultOk.setValue(true);
                } else {
                    Toast.makeText(context,"Error creando inmueble ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
                Log.e(TAG, "Error al hacer la llamada", throwable);
            }
        });
    }

    public void setImage(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Uri uri = data.getData();
                mUriImage.setValue(uri);
            }
        }
}
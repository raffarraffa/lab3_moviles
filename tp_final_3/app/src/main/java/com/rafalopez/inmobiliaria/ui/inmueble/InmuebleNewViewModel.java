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
        Log.d(TAG, "crearInmueble: 94" + mime);

        file = new File(path);

        // Convertir el objeto inmuebleDto a un String JSON
        inmuebleStr = new Gson().toJson(inmuebleDto);

        // Crear el RequestBody para el JSON del inmueble
        RequestBody inmueble = RequestBody.create(inmuebleStr, MediaType.parse("application/json"));

        // Crear el RequestBody para la imagen
        RequestBody fileBody = RequestBody.create(file, MediaType.parse(mime));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", file.getName(), fileBody);

        // Verificar los valores
        Log.d(TAG, "crearInmueble: JSON inmueble: " + inmuebleStr);
        Log.d(TAG, "crearInmueble: Imagen: " + imagePart.headers());

        // Realizar la solicitud Retrofit
        Call<ResMsg> req = api.CreateInmueble(token, inmueble, imagePart);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                if (response.isSuccessful()) {
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



    /*
    public  void  crearInmueble( InmuebleDto inmuebleDto, Uri uriImage)
    {
        String token = ApiData.getDataToken(context);
        String inmuebleStr;
        File file;
//        String path ="res/drawable/logo_final.webp";
        String path ="drawable/logo_final.webp";
        if(uriImage !=null) {
            path = RealPathUtil.getRealPath(context, uriImage);
        }
        file = new File(path);
        inmuebleStr = new Gson().toJson(inmuebleDto);
        // ceate body con json
      //  RequestBody inmueble = RequestBody.create(inmuebleStr,MediaType.parse("text/plain"));
        RequestBody inmueble = RequestBody.create(inmuebleStr, MediaType.parse("application/json"));
        // create body imagen
        RequestBody fileBody = RequestBody.create(file,MediaType.parse("multipart/form-data"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", file.getName(), fileBody);

        Log.d(TAG, "crearInmueble: 98 \n" + inmuebleStr );
        Log.d(TAG, "crearInmueble: 99 \n" + inmueble.contentType() );
        Log.d(TAG, "crearInmueble: 100 \n" + imagePart.headers() );

        Call<ResMsg> req = api.CreateInmueble(token, inmueble,imagePart);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                if (response.isSuccessful()) {
                    // Respuesta exitosa
                    Log.d(TAG, "Inmueble creado con éxito: " + response.body());
                } else {
                    // Manejo de errores en la respuesta
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
                // Error al hacer la llamada
                Log.e(TAG, "onFailure: Error al hacer la llamada", throwable);
            }
        });
   }
   */

/*
    public void  crearInmueble(InmuebleDto inmueble, Uri uriImage){
    String token = ApiData.getDataToken(context);
    File file;
    String path ="res/drawable/logo_final.webp";
    if(uriImage !=null) {
        path = RealPathUtil.getRealPath(context, uriImage);
    }
    file = new File(path);
    RequestBody rBodyFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
    MultipartBody.Part imagen = MultipartBody.Part.createFormData("imagen", file.getName(), rBodyFile);
    String inmu = new Gson().toJson(inmueble);
   Log.d(TAG, "crearInmueble: 94" + inmu);
    String inmuebleJson2 = "{\"direccion\":\"Junin 67\",\"uso\":\"Residencial\",\"ambientes\":1,\"coordenadas\":null,\"precio\":123,\"descripcion\":\"asdasd\",\"ciudad\":\"Carpinteria\",\"tipo\":\"Casa\"}" ;
    RequestBody inmuebleJson = RequestBody.create(inmuebleJson2,MediaType.parse("application/json"));
        Call<ResMsg> req =api.CreateEntityJson(  token, "inmueble", inmuebleJson,  imagen);
        req.enqueue(new Callback<ResMsg>() {
            @Override
            public void onResponse(Call<ResMsg> call, Response<ResMsg> response) {
                if(response.isSuccessful()){
                    mResultOk.setValue(true);
                    Log.d(TAG, "onResponse: 68");
                }else{
                    Log.d(TAG, "onResponse: 71");
                }

                Log.d(TAG, "onResponse: " + response);
            }
            @Override
            public void onFailure(Call<ResMsg> call, Throwable throwable) {
                Log.d(TAG, "onResponse: 112");
            }
        });

   }
   */

    public void setImage(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Uri uri = data.getData();
                mUriImage.setValue(uri);
               // Toast.makeText(context, "uri: " + uri.toString() , Toast.LENGTH_SHORT).show();
            }
        }



}
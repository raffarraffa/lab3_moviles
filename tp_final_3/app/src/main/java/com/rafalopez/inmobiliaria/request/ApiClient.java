package com.rafalopez.inmobiliaria.request;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.Contrato;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.InmuebleDto;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.entity.ResMsg;
import com.rafalopez.inmobiliaria.entity.User;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {


    private static final String TAG = "ApiClient";
    @NonNull
    public static InmobiliariaServices getApiInmobiliaria(){
       Gson gson = new GsonBuilder()
                .setLenient()
      //          .registerTypeAdapter(Date.class, new DateCoverter())
                .create();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d(TAG, "Solicitud URL: " + request.url());
                        Log.d(TAG, "Solicitud Headers: " + request.headers());
                        Response response = chain.proceed(request);
                        Log.d(TAG, "Respuesta Headers: " + response.headers());
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            Log.d(TAG, "Cuerpo de la respuesta: " + responseBody);
                            ResponseBody newResponseBody = ResponseBody.create(
                                    MediaType.parse("application/json"),
                                    responseBody
                            );
                            return response.newBuilder()
                                    .body(newResponseBody)
                                    .build();
                        } else {
                            Log.d(TAG, "No se encontró cuerpo en la respuesta");
                            return response;
                        }
                    }

                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppParams.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmobiliariaServices.class);
    }


    public  interface InmobiliariaServices{
        @POST("login1")
        Call<User> PostLogin1(@Body User user);

        @POST("login")
        Call<String> PostLogin(@Body User user);

        @POST("login/passwordrestore")
        Call<ResMsg> PostRestore(@Body User user);

        @POST("login/acceptrestore")
        Call<ResMsg> PostAcceptRestore(@Body User user);

        @GET("propietario/perfil")
        Call<Propietario> GetPerfil(@Header("Authorization") String token);

        @PATCH("propietario/update")
        Call<Propietario> PatchPerfil(@Header("Authorization") String token,   @Body Propietario propietario);

        @GET("inmueble/listar")
        Call<List<Inmueble>> GetInmuebleList(@Header("Authorization") String token);

        @POST("inmueble/crear")
        Call<ResMsg> CreateInmueble(@Header("Authorization") String token, @Body InmuebleDto inmueble);

        @Multipart
        @POST("{entity}/{id}/imagen")
        Call<ResMsg> UploadImg( @Path("entity") String entity, @Path("id") String id, @Part MultipartBody.Part img );


        @Multipart
        @POST("{entity}/new3")
        Call<ResMsg> CreateEntityJson2(
                       @Header("Authorization") String token,
                        @Path("entity") String entity,
                        @Part("inmuebleJson") RequestBody inmuebleJson,
                        @Part MultipartBody.Part imagen
        );

        @Multipart
        @POST("inmueble/new")
        Call<ResMsg> CreateInmueble(
                @Header("Authorization") String token,
                @Part("inmueble") RequestBody inmueble,
                @Part MultipartBody.Part imagen
        );

        @GET("contrato/listar")
        Call<List<Contrato>> GetListContrato(@Header("Authorization") String token);


    }
}
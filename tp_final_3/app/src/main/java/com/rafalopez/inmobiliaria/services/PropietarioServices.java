package com.rafalopez.inmobiliaria.services;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.rafalopez.inmobiliaria.entity.User;
import com.rafalopez.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropietarioServices {
    private ApiClient.InmobiliariaServices api;
    private SharedPreferences sharedPreferences;

    private static final String PREFERENCES_NAME = "datos";
    private static final String TOKEN_KEY = "token";


    public PropietarioServices(Context contexto) {
        this.api = ApiClient.getApiInmobiliaria();
        this.sharedPreferences = contexto.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // guardar token
    public boolean guardarToken(@NonNull String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        return editor.commit();
    }

    // obtener token guardado
    @NonNull
    public String obtenerToken() {
        return sharedPreferences.getString(TOKEN_KEY, "");
    }

    // eliminar token (cerrar session)
    public boolean eliminarToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        return editor.commit();
    }

    // verifica  usuario esta autenicado
    public boolean isAutenticado() {
        String token = obtenerToken();
        if(token==null || token.isEmpty()) return false;
        return true;
    }

    /**
     * LOGIN Inicia SESSION del usuario con las credenciales proporcionadas.
     *
     * Realiza una llamada a la API para autenticar al usuario
     * si es exitoso  guarda el token de SESSION  en las preferencias compartidas.
     *
     * @param email    El correo del usuario.
     * @param password La contraseña del usuario.
     * @param callback El objeto Callback que maneja la respuesta de la API.
     *                 Este callback se invoca con el resultado  y obliga
     *                 imlemntacion de metodos cuando s eutilice
     *                 En caso de exito, se llama a onResponse con la respuesta,
     *                 y en caso de error, se llama a onFailure con el error.
     */
    public void login(String email, String password, Callback<String> callback) {
        User user = new User(email, password);
        Call<String> request = api.PostLogin(user);

        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    guardarToken(token);
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                callback.onFailure(call, throwable);
            }
        });
    }


    /**
     * Obtiene el perfil del usuario autenticado mediante el token.
     *
     * Rrealiza una llamada a la API para obtener el perfil del usuario.
     * El token de autenticación se incluye en la cabecera de la solicitud.
     *
     * @param token El token de autenticación del usuario. Debe ser un token válido
     *              que se ha obtenido previamente tras el inicio de sesión.
     *              En caso de exito, el perfil del usuario se puede procesar en
     *              el callback.
     *              En caso de error, se puede manejar el fallo en el callback.
     * @param callback Maneja la respuesta de la API.
     *                 Este callback se invoca con el resultado .
     *                 En caso de exito, se llama a onResponse con la respuesta,
     *                 y en caso de error, se llama a onFailure con el error.
     */
    public void obtenerPerfil(String token, Callback<String> callback) {
        Call<String> request = api.GetPerfil("Bearer " + token);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                //callback.onFailure(call, throwable);
            }
        });
    }

}

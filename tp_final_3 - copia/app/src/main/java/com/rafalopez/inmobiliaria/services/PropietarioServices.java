//package com.rafalopez.inmobiliaria.services;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonParser;
//import com.rafalopez.inmobiliaria.data.ApiData;
//import com.rafalopez.inmobiliaria.entity.Propietario;
//import com.rafalopez.inmobiliaria.entity.User;
//import com.rafalopez.inmobiliaria.request.ApiClient;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class PropietarioServices {
//    private final ApiClient.InmobiliariaServices api;
//    private boolean result;
//
//
//    private static final String PREFERENCES_NAME = "datos";
//    private static final String TOKEN_KEY = "token";
//    private static final String TAG = "PropietarioServices";
//    private final Context contexto;
//
//    /**
//     * Constructor de la clase
//     *
//     * @param contexto Contexto de la app, accede a recursos como SharedPreferences.
//     */
//    public PropietarioServices(Context contexto) {
//        this.api = ApiClient.getApiInmobiliaria();
//        this.contexto = contexto;
//    }
//
//    /**
//     * Guarda el token de autenticacion en SharedPreferences
//     *
//     * @param token El token JWT recibido de la API
//     * @return `true` si el token fue guardado, de lo contrario, `false`
//     */
//    public boolean guardarToken(@NonNull String token) {
//        String stringToken = JsonParser.parseString(token).getAsJsonObject().get("token").getAsString();
//        return ApiData.guardarData(contexto, PREFERENCES_NAME, stringToken, TOKEN_KEY);
//    }
//
//    /**
//     * Obtiene el token almacenado en SharedPreferences
//     *
//     * @return El token JWT almacenado. Si no existe, retorna una cadena caci
//     */
//    @NonNull
//    public String obtenerToken() {
//        return ApiData.leerData(contexto, PREFERENCES_NAME, TOKEN_KEY);
//    }
//
//    /**
//     * Elimina el token de  SharedPreferences
//     *
//     * @return `true` o `false` resultado poeracion
//     */
//    public boolean eliminarToken() {
//        return ApiData.deleteData(contexto, PREFERENCES_NAME, TOKEN_KEY);
//    }
//
//    /**
//     * Verifica si el usuario está autenticado al revisar si existe un token almacenado
//     * Puede no se valido, debe verificar contra el servidor
//     * *
//     * @return `true` o  `fasle` si exite un token
//     */
//    public boolean isAutenticado() {
//        String token = obtenerToken();
//        return token != null && !token.isEmpty();
//    }
//
//    /**
////     * Inicia SESSION
////     * @param user     Objeto de tipo User (nombre de usuario y contraseña)
////     * @param callback Objeto de tipo Callback que gestiona la respuesta de la API
////     * @return         Un valor booleano que indica si el token fue guardado correctamente
////     */
//
//    public void login(User user, Callback<User> callback) {
//        Call<User> request = api.PostLogin(user);
//        request.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    User  user2 = response.body();
//                    //TODO validar token,
//                    if(!guardarToken(user2.getToken())){
//                        errorRespuesta(response);
//                        callback.onResponse(call, null);
//                    }
//                    callback.onResponse(call, response);
//                } else {
//                    errorRespuesta(response);
//                    callback.onResponse(call, null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable throwable) {
//                errorConexion(throwable);
//                callback.onResponse(call, null);
//            }
//        });
//    }
////    public boolean login(User user) {
////        result = false;
////        Call<String> request = api.PostLogin(user);
////        // encola la solicitud
////        request.enqueue(new Callback<String>() {
////            @Override
////            public void onResponse(Call<String> call, Response<String> response) {
////                if (response.isSuccessful() && response.body() != null) {
////                    String token = response.body();
////                    result = guardarToken(token);
////                    call.onResponse(call, response);
////                } else {
////                    errorRespuesta(response);
////                    call.onResponse(call, null);
////                }
////            }
////
////            @Override
////            public void onFailure(Call<String> call, Throwable throwable) {
////                errorConexion(throwable);
////                call.onResponse(call, null);
////            }
////        });
////        return result;
////    }
//
//
//    /**
//     * Obtiene el perfil
//     *
//     * @param token    JWT
//     * @param callback manejador respuesta de la API, que incluye el perfil
//     */
//    public void obtenerPerfil(String token, Callback<String> callback) {
//        Call<Propietario> request = api.GetPerfil("Bearer " + token);
//// BUG ver de mapeo entodidad
////        // encola la solicitud
////        request.enqueue(new Callback<String>() {
////            @Override
////            public void onResponse(Call<String> call, Response<String> response) {
////                if (response.isSuccessful() && response.body() != null) {
////                    callback.onResponse(call, response);
////                } else {
////                    errorRespuesta(response);
////                    callback.onResponse(call, null);
////                }
////            }
////
////            @Override
////            public void onFailure(Call<String> call, Throwable throwable) {
////                errorConexion(throwable);
////                callback.onResponse(call, null);
////            }
////        });
//    }
//
//    /**
//     * gestionerrores
//     *
//     * @param response respuesta de la API con el erro
//     */
//    private void errorRespuesta(@NonNull Response<?> response) {
//        Log.e(TAG, "Error en la respuesta: " + response.message() + " Code: " + response.code());
//    }
//
//    /**
//     * gestionerrores
//     *
//     * @param throwable excepción o error
//     */
//    private void errorConexion(@NonNull Throwable throwable) {
//        Log.e(TAG, "Error de conexión: " + throwable.getMessage());
//    }
//
//
//}

package com.rafalopez.inmobiliaria.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.entity.Propietario;

/**
 * Clase para almacenamiento de datos con SharedPreferences.
 */
public class ApiData {
    private static final String TAG = "salidaDebug";
    private static SharedPreferences appData;
    private static String authToken = null;

    /**
     * Establece la conexión con SharedPreferences
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo
     * @return Instancia de SharedPreferences
     */
    private static SharedPreferences conectar(Context context, String archivo) {
        if (appData == null) {
            appData = context.getSharedPreferences(archivo, Context.MODE_PRIVATE);
        }
        return appData;
    }

    /**
     * Guarda un dato en SharedPreferences
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @param dato    Dato  a almacenar
     * @param tag     Etiqueta del dato
     * @return Booleano que indica si la resulkto exitosa
     */
    public static boolean guardarData(
            Context context,
            @NonNull String archivo,
            @NonNull String dato,
            @NonNull String tag) {
        SharedPreferences appData = conectar(context, archivo);
        SharedPreferences.Editor editor = appData.edit();
        editor.putString(tag, dato);
        return editor.commit();
    }

    /**
     * Guarda los datos de un propietario en SharedPreferencesg
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @param dato    Objeto Propietario a almacenar
     * @return Booleano que indica RESULTADO operacion
     */
    public static boolean guardarDataPropietario(
            Context context,
            @NonNull String archivo,
            @NonNull Propietario dato
            ) {
        SharedPreferences appData = conectar(context, archivo);
        SharedPreferences.Editor editor = appData.edit();
        editor.putInt("id", dato.getId());
        editor.putString("nombre", dato.getNombre());
        editor.putString("apellido", dato.getApellido());
        editor.putString("dni", dato.getDni());
        editor.putString("email", dato.getEmail());
        editor.putString("telefono", dato.getTelefono());
        editor.putString("avatar", dato.getAvatar());
        return (editor.commit());
}


    /**
     * Lee un dato de SharedPreferences.
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @param tag Etiqueta del dato a leer
     * @return Dato almacenado o null si no existe
     */
    @NonNull
    public static String leerData(Context context, String archivo, String tag) {
        SharedPreferences appData = conectar(context, archivo);
        return appData.getString(tag, null);
    }

    /**
     * Lee un dato de SharedPreferences.
     *
     * @param context Contexto de la app
     * @return Propeitario , null si fallo
     */
    @NonNull
    public static Propietario leerDataPropietario(Context context) {
        SharedPreferences appData = conectar(context, AppParams.PREFERENCES_DATA);
        int id= appData.getInt("id",-1);
        String nombre = appData.getString("nombre", "");
        String apellido = appData.getString("apellido", "");
        String dni = appData.getString("dni", "");
        String email = appData.getString("email", "");
        String telefono = appData.getString("telefono", "");
        String avatar = appData.getString("avatar", "");
        return new Propietario(id,nombre, apellido, dni, email, telefono, avatar);
    }

    /**
     * Borra un dato de SharedPreferences
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @param tag Etiqueta del dato a borrar
     * @return Booleano que indica si se resolvio con exito
     */
    public static boolean deleteData(Context context, String archivo, String tag) {
        SharedPreferences appData = conectar(context, archivo);
        SharedPreferences.Editor editor = appData.edit();
        editor.remove(tag);
        return editor.commit();
    }

    /**
     *  metodos especializados
     */
    public static boolean setDataToken(Context context, @NonNull String token) {
        authToken = token;
        SharedPreferences appData = conectar(context, AppParams.PREFERENCES_DATA );
        SharedPreferences.Editor editor = appData.edit();
        editor.putString(AppParams.TOKEN_KEY, token);
        return editor.commit();
    }

    /**
     *  GET tken modo sinlgeton, seteado en memoria o desde shared preference
     * @param context
     * @return
     */
    public  static String getDataToken(Context context){
        if (authToken == null) { 
            SharedPreferences appData = conectar(context, AppParams.PREFERENCES_DATA );
            authToken = appData.getString(AppParams.TOKEN_KEY, null);
        }
        Log.d(TAG, "getDataToken: " + authToken);
        return authToken;
    }
}

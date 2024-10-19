package com.rafalopez.inmobiliaria.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.rafalopez.inmobiliaria.entity.Propietario;

/**
 * Clase para almacenamiento de datos con SharedPreferences.
 */
public class ApiData {

    private static SharedPreferences appData;

    /**
     * Establece la conexión con SharedPreferences
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @return Instancia de SharedPreferences
     */
    private static SharedPreferences conectar(Context context, String archivo) {
        if (appData == null) {
            archivo = archivo + ".dat";
            appData = context.getSharedPreferences(archivo, Context.MODE_PRIVATE);
        }
        return appData;
    }

    /**
     * Guarda un dato en SharedPreferences
     *
     * @param context Contexto de la app
     * @param archivo Nombre del archivo (sin ext)
     * @param dato    Dato a almacenar
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

    public static boolean guardarData(
            Context context,
            @NonNull String archivo,
            @NonNull Propietario dato,
            @NonNull String tag) {
        SharedPreferences appData = conectar(context, archivo);
        SharedPreferences.Editor editor = appData.edit();
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
//    public static Propietario leerDataPropietario(Context context, String archivo, String tag) {
//        SharedPreferences appData = conectar(context, archivo);
//        Propietario propietario = new Propietario(
//        appData.getString("nombre",null),
//        appData.getString("apellido",null),
//        appData.getString("dni", null),
//        appData.getString("email", null),
//        appData.getString("telefono", null),
//        appData.getString("avatar", null)
//        );
//        return propietario;
//    }

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
}

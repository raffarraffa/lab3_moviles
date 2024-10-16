package com.rafalopez.inmobiliaria.data;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
public class ApiData {

    private static SharedPreferences propietarioData;
    private static SharedPreferences conectar(Context context) {
        if (propietarioData == null) {
            propietarioData = context.getSharedPreferences("datos", 0);
        }
        return propietarioData;
    }


    public static void guardarData(Context context, @NonNull String token){
        SharedPreferences propietarioData=conectar(context);
        SharedPreferences.Editor editor=propietarioData.edit();
        editor.putString("token",(token));
        editor.commit();
    }

    @NonNull
    public static String leerData(Context context){
        SharedPreferences propietarioData=conectar(context);
        String token=propietarioData.getString("token",null);

        return token;
    }
}

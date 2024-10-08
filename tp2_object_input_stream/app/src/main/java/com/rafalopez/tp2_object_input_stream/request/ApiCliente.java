
package com.rafalopez.tp2_object_input_stream.request;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.rafalopez.tp2_object_input_stream.model.Usuario;
public class ApiCliente {

    private static SharedPreferences usuarioReq;
    private static SharedPreferences conectar(Context context) {
        if (usuarioReq == null) {
            usuarioReq = context.getSharedPreferences("datos", 0);
        }
        return usuarioReq;
    }

    public static void guardar(Context context, @NonNull Usuario usuario){
        
//        SharedPreferences usuarioReq=conectar(context);
//        SharedPreferences.Editor editor=usuarioReq.edit();
//        editor.putInt("dni",(usuario.getDni()));
//        editor.putString("apellido",usuario.getApellido());
//        editor.putString("nombre",usuario.getNombre());
//        editor.putString("email",usuario.getEmail());
//        editor.putString("pass",usuario.getPass());
 //       editor.commit();
    }

    @NonNull
    public static Usuario leerDatos(Context context){
 //       SharedPreferences usuarioReq=conectar(context);
        int dni= usuarioReq.getInt("dni",-1);
        String apellido=usuarioReq.getString("apellido","-1");
        String nombre=usuarioReq.getString("nombre","-1");
        String email=usuarioReq.getString("email","-1");
        String pass=usuarioReq.getString("pass","-1");
        Usuario usuario=new Usuario(nombre, apellido, email,dni,pass);
        return usuario;
    }

    public static Usuario login(Context context,String mail,String password){
        Usuario usuario=null;
//        SharedPreferences usuarioReq=conectar(context);
        int dni= usuarioReq.getInt("dni",-1);
        String apellido=usuarioReq.getString("apellido","-1");
        String nombre=usuarioReq.getString("nombre","-1");
        String email=usuarioReq.getString("email","-1");
        String pass=usuarioReq.getString("pass","-1");

        if (email.equals(mail) && pass.equals(password)){
            usuario=new Usuario(nombre,apellido,email,dni,pass);
        }

        return usuario;
    }
}

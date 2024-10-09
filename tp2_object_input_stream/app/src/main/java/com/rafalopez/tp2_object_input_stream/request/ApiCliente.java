
package com.rafalopez.tp2_object_input_stream.request;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.rafalopez.tp2_object_input_stream.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


//    public static Usuario leer(Context context){
//        Usuario usuario = null;
//        File file = conectar(context);
//        try{
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            ObjectInputStream ois = new ObjectInputStream(bis);
//            usuario = (Usuario) ois.readObject();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(context, "Error al encontrar el archivo", Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            Toast.makeText(context, "Error entrada/salida", Toast.LENGTH_SHORT).show();
//        } catch (ClassNotFoundException e) {
//            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
//        }
//        return usuario;
//    }
//
//
//    public static boolean Guardar(Context context, Usuario usuario){
//        File file = conectar(context);
//        try{
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            ObjectOutputStream oos = new ObjectOutputStream(bos);
//            oos.writeObject(usuario);
//            bos.flush();
//            oos.close();
//            return true;
//        } catch (FileNotFoundException e) {
//            Toast.makeText(context, "Error de archivo", Toast.LENGTH_SHORT).show();
//            return false;
//        } catch (IOException e) {
//            Toast.makeText(context, "Error de enrada/salida", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }
//
//
//}

public class ApiCliente {


    private static File file;
    private static boolean result=false;
    private static File conectar(Context context) {
        if (file == null) {
            File directory = context.getFilesDir();
            file = new File(directory, "data.dat");
         }
        return file;
    }

    public static boolean guardar(Context context, @NonNull Usuario usuario) {
        boolean result = false;
        File file = conectar(context);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(usuario);
            result = true;
        } catch (FileNotFoundException e) {
            Log.d("salida", "linea91");
            Toast.makeText(context, "Error: archivo no encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("salida", "linea94 ");
            Toast.makeText(context, "Error de entrada/salida", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                Log.d("salida", "linea109 ");
                Toast.makeText(context, "Error al cerrar el archivo", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }


    @NonNull
    public static Usuario leerDatos(Context context){
        file=conectar(context);
        Usuario usuario = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
        } catch (FileNotFoundException e) {
         //   Toast.makeText(context, "Error al encontrar el archivo", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            Toast.makeText(context, "Error entrada/salida", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
        }finally {
            return usuario;
        }
    }

    public static Usuario login(Context context,String email,String pass){
        file=conectar(context);
        Usuario usuario = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error al encontrar el archivo", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            Toast.makeText(context, "Error entrada/salida", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
        }
        if(!usuario.isAuth(email,pass)) usuario = null;
        return usuario;

    }
}

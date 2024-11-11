package com.rafalopez.inmobiliaria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.rafalopez.inmobiliaria.databinding.ActivityMainBinding;
import com.rafalopez.inmobiliaria.ui.login.LoginActivity;
import com.rafalopez.inmobiliaria.ui.menu.MenuActivity;

/**
 * Punto de entrada de la applicación, gestiona la inicialización de la vista
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private MainViewModel mainViewModel;

    /**
     * Llama cuando la actividad on create al iniciar la app
     * incia  binding, configura el ViewModel y observa la validación
     * deltoken, el estado de la conexión a internet
     *
     * @param savedInstanceState  instancia anterior de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        mainViewModel.getMTokenInvalid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
             iniciarLogin();
             //   finish();
            }
        });

        mainViewModel.getMTokenValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                iniciarApp();
              //  finish();
            }
        });
        mainViewModel.getMInternet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getApplicationContext(), "Debe Activar internet", Toast.LENGTH_LONG).show();
            }
        });
        // verifics
        getPermisos();

      //  mainViewModel.isValidToken();
    }

    @Override
    protected void onResume() {
        super.onResume();
            Toast.makeText(this, "Has regresado ", Toast.LENGTH_LONG).show();

    }

    /**
     * iicia  actividad de inicio de sesión (LoginActivity) si el token no es valido
     */
    private void iniciarLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * inciia actividad de a aplicación (MenuActivity) si el token es valido
     */
    private void iniciarApp() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void getPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED) {

                mainViewModel.isValidToken();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            }
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                mainViewModel.isValidToken();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MenuActivity", "Permiso concedido");
                mainViewModel.isValidToken();
            } else {
                Toast.makeText(this, "Permiso denegado. La aplicacion necesita acceso a las imágenes.", Toast.LENGTH_LONG).show();
                mostrarMensajeConfiguracion();
            }
        }
    }
    private void mostrarMensajeConfiguracion() {
        Toast.makeText(this, "Los permisos son necesarios. Por favor, ve a la configuración de la aplicacion.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No se pudo abrir la configuracion de la aplicacion", Toast.LENGTH_SHORT).show();
        }
    }
}

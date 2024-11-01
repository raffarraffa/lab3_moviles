package com.rafalopez.inmobiliaria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
        mainViewModel.isValidToken();
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
}

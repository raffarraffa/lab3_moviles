package com.rafalopez.inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.rafalopez.inmobiliaria.databinding.ActivityLoginBinding;
import com.rafalopez.inmobiliaria.ui.menu.MenuActivity;

/**
 * clase para el inicio de SESSION usuario
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    /**
     * llamado al crear la actividad
     *
     * @param savedInstanceState ESTADO actividad guardado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())
                .create(LoginViewModel.class);

/** mutables */

        loginViewModel.getMLoginError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
            }
        });

        loginViewModel.getMLoginOk().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                iniciarApp();
                //finish();
            }
        });

        loginViewModel.getMsgLoginError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        });
/** binidngs */
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.loginUser(binding.inputUsuario.getText().toString(),
                        binding.inputPassword.getText().toString());
            }
        });
        binding.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //iniciarApp();
    }

    /**
     * iniciando la actividad login de  app
     */
    private void iniciarApp() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}

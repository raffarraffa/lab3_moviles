package com.rafalopez.inmobiliaria.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.rafalopez.inmobiliaria.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding y variables de binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final EditText email = binding.inputUsuario;
        final EditText password = binding.inputPassword;
        final Button btnLogin = binding.btnLogin;
        final Button btnRestore = binding.btnRestore;

        // viewmodel
        loginViewModel =  ViewModelProvider .AndroidViewModelFactory .getInstance(getApplication()).create(LoginViewModel.class);

        // listener de vista
        TextWatcher afterTextChangeListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(LoginActivity.this, "sasdadfssss", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Toast.makeText(LoginActivity.this, "sssss", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(LoginActivity.this, "sssss", Toast.LENGTH_SHORT).show();
                Log.e("salida", "afterTextChanged: " + s.toString()  );

            }
        };
            email.addTextChangedListener(afterTextChangeListener);

    }

}
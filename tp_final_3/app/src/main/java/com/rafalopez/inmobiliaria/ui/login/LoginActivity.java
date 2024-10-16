package com.rafalopez.inmobiliaria.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
        // observer de mutables
        loginViewModel.getMsgLoginError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                binding.btnLogin.setVisibility(View.VISIBLE);
//                binding.btnRestore.setVisibility(View.VISIBLE);
//                binding.progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Login error " + s,Toast.LENGTH_SHORT).show();
                Log.e("salida", "mutable 40: " + s.toString()  );
            }
        });
    loginViewModel.checkDataToken();
        // llamar al observer
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                binding.btnLogin.setEnabled(false);
//                binding.btnLogin.setVisibility(View.INVISIBLE);
//                binding.btnRestore.setVisibility(View.INVISIBLE);
//                binding.progressBar.setVisibility(View.VISIBLE);
                loginViewModel.loginUser(email.getText().toString(),password.getText().toString());
            }
        });
        btnRestore.setOnClickListener(new View.OnClickListener(){
            @Override
                 public void onClick(View v) {
                        loginViewModel.checkDataToken();
                  }
        });


    }

}
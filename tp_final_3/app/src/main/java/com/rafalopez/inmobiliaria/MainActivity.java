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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
        mainViewModel =ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        mainViewModel.getMTokenInvalid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
            iniciarLogin();
            }
        });
        mainViewModel.getMTokenValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                iniciarApp();
            }
        });
        mainViewModel.getMInternet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getApplicationContext(), "Debe Activar internet",Toast.LENGTH_LONG).show();
            }
        });
        Log.d("Salida", "onCreate: "+ mainViewModel.isInmobiliariaOk());
    }
    private void iniciarLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private void iniciarApp(){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}

package com.rafalopez.inmobiliaria.ui.restore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rafalopez.inmobiliaria.MainActivity;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.ActivityLoginBinding;
import com.rafalopez.inmobiliaria.databinding.ActivityRestoreBinding;
import com.rafalopez.inmobiliaria.databinding.RestoreFormBinding;
import com.rafalopez.inmobiliaria.ui.login.LoginActivity;
import com.rafalopez.inmobiliaria.ui.login.LoginViewModel;

public class RestoreActivity extends AppCompatActivity {
    private static final String TAG = "salidaDebug";
    private ActivityRestoreBinding binding;
    private RestoreViewModel restoreViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        String token = data.getQueryParameter("token");
        binding = ActivityRestoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restoreViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RestoreViewModel.class);
        restoreViewModel.getMAceptResultOk().observe(this, s ->  {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->{
            String codigo= binding.textCodigo.getText().toString();
            restoreViewModel.acceptRestore(token,codigo);
        });
        binding.btnCancel.setOnClickListener(v->{
            mostarSalirApp();
        });
   }
    private void mostarSalirApp() {
        new AlertDialog.Builder(this)
                .setTitle("Salir aplcacion")
                .setMessage("¿Estás seguro de que salir de la aplicación?")
                .setPositiveButton("Salir", (dialog, which) -> {
                    finishAffinity();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
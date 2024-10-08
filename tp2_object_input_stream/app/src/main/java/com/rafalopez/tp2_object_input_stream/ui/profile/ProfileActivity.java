package com.rafalopez.tp2_object_input_stream.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rafalopez.tp2_object_input_stream.databinding.ActivityProfileBinding;
import com.rafalopez.tp2_object_input_stream.model.Usuario;


public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean login= intent.getBooleanExtra("login",false);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileActivityViewModel.class);
        viewModel.getRegistro(login);
        viewModel.getMusuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
              binding.inputNombre.setText(usuario.getNombre());
              binding.inputApellido.setText(usuario.getApellido());
              binding.inputEmail.setText(usuario.getEmail());
              binding.inputTelefono.setText( usuario.getTelefono()+"");
              binding.inputPassword.setText(usuario.getPass());
              binding.textViewTitle.setText("Perfil");
            }
        });
        viewModel.getMRegistroError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.inputNombre.getText().toString();
                String apellido = binding.inputApellido.getText().toString();
                String email = binding.inputEmail.getText().toString();
                int telefono = viewModel.verifyDni(binding.inputTelefono.getText().toString());
                String pass= binding.inputPassword.getText().toString();
                Usuario usuario =new Usuario(nombre,apellido,email,telefono,pass);
                viewModel.setRegistro(usuario);
            }
        });
    }
}
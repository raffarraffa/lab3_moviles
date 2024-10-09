package com.rafalopez.tp3_foto_perfil.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rafalopez.tp3_foto_perfil.databinding.ActivityLoginBinding;
import com.rafalopez.tp3_foto_perfil.model.Usuario;

public class LoginActivity extends AppCompatActivity {
   private ActivityLoginBinding binding;
   private LoginActivityViewModel viewModel;
   private Usuario usuario = new Usuario();
   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       viewModel =ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
       viewModel.verifyData();
       viewModel.getMErrorMail().observe(this, new Observer<String>() {
           @Override
           public void onChanged(String s) {
               binding.inputUser.setBackgroundColor(Color.RED);
             //  binding.testMsgError.setBackgroundColor(Color.RED);
               binding.testMsgError.setText(s);
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       binding.inputUser.setBackgroundColor(Color.parseColor("#FFFDF6FE"));
                       binding.inputUser.setText("");
                       binding.testMsgError.setText("");
                   }
               }, 2000);
           }
       });
       viewModel.getMErrorLogin().observe(this, new Observer<String>() {
           @Override
           public void onChanged(String s) {
               binding.inputUser.setBackgroundColor(Color.RED);
               binding.inputPassw.setBackgroundColor(Color.RED);
               binding.testMsgError.setText(s);
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       binding.inputUser.setBackgroundColor(Color.parseColor("#FFFDF6FE"));
                       binding.inputPassw.setBackgroundColor(Color.parseColor("#FFFDF6FE"));
                       binding.inputUser.setText("");
                       binding.inputPassw.setText("");
                       binding.testMsgError.setText("");
                   }
               }, 2000);
           }
       });
       binding.btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               usuario.setEmail(binding.inputUser.getText().toString());
               usuario.setPass(binding.inputPassw.getText().toString());
               viewModel.loginUser(usuario);
           }
       });

       binding.btnRegistro.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               viewModel.registerUser();
           }
       });

    }
}
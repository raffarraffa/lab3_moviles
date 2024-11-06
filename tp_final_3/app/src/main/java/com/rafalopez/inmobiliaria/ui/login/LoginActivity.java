package com.rafalopez.inmobiliaria.ui.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.rafalopez.inmobiliaria.databinding.ActivityLoginBinding;
import com.rafalopez.inmobiliaria.databinding.RestoreFormBinding;
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
                String email=binding.inputUsuario.getText().toString();
                String password=binding.inputPassword.getText().toString();
                loginViewModel.loginUser(email, password);
            }
        });
        binding.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordRestore();
            }
        });
        getPermisos();
        //iniciarApp();
    }

    /**
     * iniciando la actividad login de  app
     */
    private void iniciarApp() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }
    /**
     * Dilogo d restrucaion pass
     */
    private void  passwordRestore1(){
        LayoutInflater inflater = getLayoutInflater();
        RestoreFormBinding formBinding = RestoreFormBinding.inflate(inflater);

        new AlertDialog.Builder(this)
//                .setTitle("Rsetaurr Contrasñea")
                .setView(formBinding.getRoot())
//                .setMessage("¿Esta seguro de resetear contraseña?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción a realizar si el usuario confirma
                        Toast.makeText(getApplicationContext(), "Confirmado", Toast.LENGTH_SHORT).show();
                        loginViewModel.passwordRestore(formBinding.restoreEmail.getText().toString(),formBinding.restorePass.getText().toString());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción a realizar si el usuario cancela
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void passwordRestore() {
        LayoutInflater inflater = getLayoutInflater();
        RestoreFormBinding formBinding = RestoreFormBinding.inflate(inflater);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(formBinding.getRoot())
                .setPositiveButton(android.R.string.yes, null) // Dejar null para manejar el clic manualmente
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = formBinding.restoreEmail.getText().toString();
                        String newPassword = formBinding.restorePass.getText().toString();

                        // Llama al ViewModel para restaurar la contraseña
                        loginViewModel.passwordRestore(email, newPassword);

                        // Observa el resultado de la restauración de la contraseña
                        loginViewModel.getMRestoreResultOk().observe(LoginActivity.this,  new Observer<String>() {
                            @Override
                            public void onChanged(String result) {
                                Toast.makeText(getApplicationContext(), result,
                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
        dialog.show();
    }

    private void getPermisos() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, 1);
            }
        } else {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
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
            } else {
                Toast.makeText(this, "Permiso denegado. La aplicación necesita acceso a las imágenes.", Toast.LENGTH_LONG).show();
            }
        }
    }

}

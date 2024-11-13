package com.rafalopez.inmobiliaria.ui.login;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

        loginViewModel.getmShake().observe(this, shake->{
            Toast.makeText(getApplicationContext(),"Llamando a tel:123456789",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:123456789"));
            startActivity(intent);
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
        binding.btnSalir.setOnClickListener(v->{
            mostarSalirApp();
        });
        getPermisos();
        //solicitarPermisos();
        loginViewModel.sensorShake();
    }

    /**
     * iniciando la actividad login de  app
     */
    private void iniciarApp() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }
    /**
     * Dilogo de restore pass
     */
     private void passwordRestore() {
        LayoutInflater inflater = getLayoutInflater();
        RestoreFormBinding formBinding = RestoreFormBinding.inflate(inflater);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(formBinding.getRoot())
                .setPositiveButton(android.R.string.yes, null)
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

                        // llama al ViewModel para restaurar la pass
                        loginViewModel.passwordRestore(email);

                        // observer resultado de la restauración de pass
                        loginViewModel.getMRestoreResultOk().observe(LoginActivity.this,  new Observer<String>() {
                            @Override
                            public void onChanged(String result) {
                                Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MenuActivity", "Permiso concedido");
            } else {
                Toast.makeText(this, "Permiso denegado. \nLa aplicación necesita acceso Para llamar .", Toast.LENGTH_LONG).show();
            }
        }
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

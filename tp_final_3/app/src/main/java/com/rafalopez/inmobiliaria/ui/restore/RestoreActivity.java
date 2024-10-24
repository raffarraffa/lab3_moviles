package com.rafalopez.inmobiliaria.ui.restore;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        binding = ActivityRestoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        restoreViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())
                .create(RestoreViewModel.class);
        setContentView(binding.getRoot());

        Toast.makeText(getApplication(),"asd",Toast.LENGTH_SHORT).show();
        passwordRestore();

    }

    private void passwordRestore() {
        LayoutInflater inflater = getLayoutInflater();
        ActivityRestoreBinding restoreBinding = ActivityRestoreBinding.inflate(inflater);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(restoreBinding.getRoot())
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
                        String email = restoreBinding.restoreEmail.getText().toString();
                        String newPassword = restoreBinding.restorePass.getText().toString();
                        // Llama al ViewModel para restaurar la contraseña
                        Log.d(TAG, "onClick: ");

                        restoreViewModel.acceptRestore(email, newPassword);
                        // Observa el resultado de la restauración de la contraseña
//                        loginViewModel.getMRestoreResultOk().observe(LoginActivity.this,  new Observer<String>() {
//                            @Override
//                            public void onChanged(String result) {
//                                Toast.makeText(getApplicationContext(), result,
//                                        Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                            }
//                        });
                    }
                });
            }
        });
        dialog.show();
    }
}
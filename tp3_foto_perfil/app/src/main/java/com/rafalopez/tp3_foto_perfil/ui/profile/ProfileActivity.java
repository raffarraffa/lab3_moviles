package com.rafalopez.tp3_foto_perfil.ui.profile;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import androidx.core.content.ContextCompat;

import com.rafalopez.tp3_foto_perfil.databinding.ActivityProfileBinding;
import com.rafalopez.tp3_foto_perfil.model.Usuario;

public class ProfileActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private ActivityProfileBinding binding;
    private ProfileActivityViewModel viewModel;
    private ActivityResultLauncher<Intent> imgProfileLanzador;
    private ActivityResultLauncher<String> permisoLanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding y ViewModel
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileActivityViewModel.class);

        // lanzaodr permisos
        //TODO hacerloc n mutable par evitar el fi
        permisoLanzador = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        abrirGaleria();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // lanzador para avatar
        imgProfileLanzador = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        viewModel.iniciarGaleria(result);
                    }
                }
        );

        //cambios en el ViewModel usuaro
        viewModel.getMusuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.inputNombre.setText(usuario.getNombre());
                binding.inputApellido.setText(usuario.getApellido());
                binding.inputEmail.setText(usuario.getEmail());
                binding.inputTelefono.setText(String.valueOf(usuario.getTelefono()));
                binding.inputPassword.setText(usuario.getPass());
                binding.textViewTitle.setText("Perfil");
                Log.d("galeria", "imagen desde usuario -> " + usuario.getPhotoUri());
                binding.imageView.setImageURI(usuario.getPhotoUri());
            }
        });

        viewModel.getMRegistroError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                //TODO  poenr alertas de erorres
            }
        });

        viewModel.getMImg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Uri img = Uri.parse(s);
                binding.imageView.setImageURI(img);
                binding.imageView.setTag(img);
            }
        });

        // listener click en imagen
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seleccionar imagen", Toast.LENGTH_SHORT).show();
                // verifica permiso y abre  galeria
                //TODO hacelrocn mutable
                verificarPermiso();

            }
        });

        // listener click guardar
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.inputNombre.getText().toString();
                String apellido = binding.inputApellido.getText().toString();
                String email = binding.inputEmail.getText().toString();
                int telefono = viewModel.verifyDni(binding.inputTelefono.getText().toString());
                String pass = binding.inputPassword.getText().toString();
                Uri photoUri = (Uri) binding.imageView.getTag();
                Log.d("usuario", "onClick: 108" + photoUri);
                Usuario usuario = new Usuario(nombre, apellido, email, telefono, pass, photoUri);
                viewModel.setRegistro(usuario);
            }
        });

        boolean login = getIntent().getBooleanExtra("login", false);
        viewModel.getRegistro(login);
    }

    private void verificarPermiso() {

        //TODO sacar el if ver con unb mutable
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permisoLanzador.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            abrirGaleria();
        }
    }

    // abri galería
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imgProfileLanzador.launch(intent);
    }
}

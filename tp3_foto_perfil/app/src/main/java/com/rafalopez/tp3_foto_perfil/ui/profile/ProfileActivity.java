package com.rafalopez.tp3_foto_perfil.ui.profile;

import android.content.Intent;
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

import com.rafalopez.tp3_foto_perfil.databinding.ActivityProfileBinding;
import com.rafalopez.tp3_foto_perfil.model.Usuario;

public class ProfileActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private ActivityProfileBinding binding;
    private ProfileActivityViewModel viewModel;
    private ActivityResultLauncher<Intent> imgProfileLanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding y ViewModel
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileActivityViewModel.class);

        // launcher para avatar
        imgProfileLanzador = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        viewModel.iniciarGaleria(result);
//                        if (result.getResultCode() == RESULT_OK) {
//                            Intent imgData = result.getData();
                            //getGaleria(result);
//                            if (imgData != null) {
//                                Uri selectedImageUri = imgData.getData();
//                                binding.imageView.setImageURI(selectedImageUri);
//                                binding.imageView.setTag(selectedImageUri);
//                                viewModel.iniciarGaleria(result);
//                            }
                        //}
                    }
                }
        );

        // Observa cambios en el ViewModel
        viewModel.getMusuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.inputNombre.setText(usuario.getNombre());
                binding.inputApellido.setText(usuario.getApellido());
                binding.inputEmail.setText(usuario.getEmail());
                binding.inputTelefono.setText(String.valueOf(usuario.getTelefono()));
                binding.inputPassword.setText(usuario.getPass());
                binding.textViewTitle.setText("Perfil");
                Log.d("galeria","imagen desde  usuario -> "+ usuario.getPhotoUri());
                binding.imageView.setImageURI(usuario.getPhotoUri());

            }
        });

        viewModel.getMRegistroError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // Manejar errores de registro si es necesario
            }
        });
        viewModel.getMImg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Uri img= Uri.parse(s);
                binding.imageView.setImageURI(img);
                binding.imageView.setTag(img);
            }
        });

        // Configura el listener para el evento CLICK cambio de imagen
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seleccionar imagen", Toast.LENGTH_SHORT).show();
                getGaleria(); // Llama a la función que selecciona la imagen
            }
        });


        // Configura el listener para guardar los datos del usuario
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

        // Llamadas al ViewModel para obtener el registro
        boolean login = getIntent().getBooleanExtra("login", false);
        viewModel.getRegistro(login);
    }

    private void getGaleria() {
        // Crea y lanza el intent para abrir la galería
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imgProfileLanzador.launch(intent);
    }
}

package com.rafalopez.inmobiliaria.ui.propietario;

import static android.widget.Toast.LENGTH_LONG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentPropietarioBinding;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Propietario;

import java.io.File;
import java.io.IOException;

/**
 * Fragmt  info  propietario
 *
 */
public class PropietarioFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 200;
    private static final String TAG = "salida";
    private PropietarioViewModel mViewModel;
    private FragmentPropietarioBinding binding;
    private ActivityResultLauncher<Intent> imgProfileLanzador;
    private ActivityResultLauncher<String> permisoLanzador;
    private Uri imageUri;

    /**
     *  nueva instancia de PropietarioFragment
     *
     * @return Una nueva instancia de PropietarioFragment
     */
    public static PropietarioFragment newInstance() {
        return new PropietarioFragment();
    }

    /**
     * Infla  layout del fragmento y configura los observer del ViewModel
     *
     * @param inflater           LayoutInflater para inflar el layout
     * @param container          ViewGroup que contiene el fragmento
     * @param savedInstanceState estado guardado de la instancia
     * @return  vista inflada del fragmento
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPropietarioBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PropietarioViewModel.class);
        mViewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {

            @Override
            public void onChanged(Propietario propietario) {
                Log.d(TAG, "onChanged:40 " + propietario);
                binding.inputNombre.setText(propietario.getNombre());
                binding.inputApellido.setText(propietario.getApellido());
                binding.inputEmail.setText(propietario.getEmail());
                binding.inputDni.setText(propietario.getDni());
                binding.inputTelefono.setText(propietario.getTelefono());
                Glide.with(getContext())
                        .load(AppParams.URL_BASE_IMG_AVATAR  + propietario.getAvatar())
                        .error(R.drawable.no_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgAvatar);
                binding.imgAvatar.setTag(propietario.getAvatar());
                Log.e(TAG, "onChanged: " + propietario.getAvatar().toString() );

            }
        });

        mViewModel.getMBtnAction().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });
        mViewModel.getMBtnAction2().observe(getViewLifecycleOwner(), new Observer<ActionMutable>() {
            @Override
            public void onChanged(ActionMutable actionMutable) {
                binding.btnEditar.setText(actionMutable.getAction());
                inputEditable(actionMutable.isVisible());
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Propietario prop = new Propietario();
                prop.setApellido(binding.inputApellido.getText().toString());
                prop.setNombre(binding.inputNombre.getText().toString());
                prop.setEmail(binding.inputEmail.getText().toString());
                prop.setTelefono(binding.inputTelefono.getText().toString());
                prop.setPassword(binding.inputPassword.getText().toString());
                prop.setAvatar(binding.imgAvatar.getTag().toString());
                prop.setDni(binding.inputDni.getText().toString());
                Toast.makeText(getContext(), "Propietario Editado", LENGTH_LONG).show();
                mViewModel.setActionBtn2(binding.btnEditar.getText().toString(), prop);
            }
        });
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.imgAvatar.getTag() != null) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // Solicitar el permiso para usar la cámara
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    } else {
                        // Abrir la cámara si el permiso ya está concedido
                        openCamera();
                    }}

            }
        });
        mViewModel.getProfile();
        verificarPermisos();
        return binding.getRoot();
    }

    /**
     * limpia el binding cuando la vista es destruida
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * cambia los campos de entrada son editables o no
     *
     * @param editable true  si los campos deben ser editables, falso de lo contrario
     */
    private void inputEditable(boolean editable) {
        binding.inputNombre.setFocusable(editable);
        binding.inputNombre.setFocusableInTouchMode(editable);
        binding.inputApellido.setFocusable(editable);
        binding.inputApellido.setFocusableInTouchMode(editable);
        binding.inputEmail.setFocusable(editable);
        binding.inputEmail.setFocusableInTouchMode(editable);
        binding.inputTelefono.setFocusable(editable);
        binding.inputTelefono.setFocusableInTouchMode(editable);
        binding.inputPassword.setFocusable(editable);
        binding.inputPassword.setFocusableInTouchMode(editable);
    }
    private void verificarPermisos(){
        permisoLanzador = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // abrirGaleria();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void openCamera() {
        try {
            File photoFile = File.createTempFile("temp_image", ".jpg", requireActivity().getExternalFilesDir(null));
            imageUri = FileProvider.getUriForFile(requireContext(), "com.rafalopez.inmobiliaria.fileprovider", photoFile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al crear el archivo de imagen", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            binding.imgAvatar.setImageURI(imageUri);
            Log.e(TAG, "Imagen cargada: " + imageUri);
        }
    }

}

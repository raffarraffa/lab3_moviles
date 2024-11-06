package com.rafalopez.inmobiliaria.ui.inmueble;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleNewBinding;
import com.rafalopez.inmobiliaria.entity.InmuebleDto;
public class InmuebleNewFragment extends Fragment {

    private static String TAG= AppParams.TAG;
    private FragmentInmuebleNewBinding binding;
    private Intent intent;
    private InmuebleNewViewModel mViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public InmuebleNewFragment () { }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,  @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleNewBinding.inflate(inflater,container, false);
        mViewModel = new  ViewModelProvider(this).get(InmuebleNewViewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // observers de mutables
        mViewModel.getMResultOk().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        // reempalzando fuinciones anoniam con fucnioen lambda
        mViewModel.getmPermisoGaleria().observe(getViewLifecycleOwner(), permiso -> {
                Toast.makeText(getContext(),"permiso " + permiso,Toast.LENGTH_SHORT).show();
               Log.d(TAG, "onViewCreated: 55");
                });
        mViewModel.getMUri().observe(getViewLifecycleOwner(), uriImg ->{
          //  Toast.makeText(getContext()," uri" + uriImg.toString(),Toast.LENGTH_SHORT).show();
            binding.detailImage.setImageURI(uriImg);
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InmuebleDto inmueble = new InmuebleDto();
                inmueble.setDireccion(binding.txtDireccion.getText().toString());
                inmueble.setCiudad(binding.txtCiudad.getSelectedItem().toString());
                inmueble.setTipo(binding.txtTipo.getSelectedItem().toString());
                inmueble.setAmbientes(Byte.parseByte(binding.txtAmbientes.getSelectedItem().toString()));
                inmueble.setPrecio(binding.txtPrecio.getText().toString());
                inmueble.setUso(binding.txtUso.getSelectedItem().toString());
                inmueble.setDescripcion(binding.txtDescripcion.getText().toString());
                Uri uriImage = (Uri) binding.detailImage.getTag();

                Toast.makeText(getContext()," uri" + binding.detailImage.getDrawable().toString(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Inmueble creado: " + inmueble);
                // Envía el objeto al ViewModel
                mViewModel.crearInmueble(inmueble, uriImage );
           //     Navigation.findNavController(view).navigateUp();
            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel. setImage(result);
            }
        });

        binding.detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 92");
                Toast.makeText(getContext() , "apreto imagen", Toast.LENGTH_SHORT).show();
                abrirGaleria();
            }
        });
   }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }


}

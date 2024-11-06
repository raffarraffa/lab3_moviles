package com.rafalopez.inmobiliaria.ui.inmueble;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
    private InmuebleNewViewModel mViewModel;
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
                Log.d(TAG, "Inmueble creado: " + inmueble);
                // Envía el objeto al ViewModel
                mViewModel.crearInmueble(inmueble);  // Método en ViewModel para guardar
           //     Navigation.findNavController(view).navigateUp();
            }
        });

        binding.detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 92");
                Toast.makeText(getContext() , "apreto imagen", Toast.LENGTH_SHORT).show();
            }
        });
   }
}

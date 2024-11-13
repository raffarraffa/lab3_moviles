package com.rafalopez.inmobiliaria.ui.inmueble;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleDetailBinding;
import com.rafalopez.inmobiliaria.entity.Inmueble;


public class InmuebleDetailFragment extends Fragment {
    private static String TAG= AppParams.TAG;
    private FragmentInmuebleDetailBinding binding;
    private  InmuebleDetailViewModel mViewModel;
    private boolean banderaDisponible;


    public InmuebleDetailFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentInmuebleDetailBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(InmuebleDetailViewModel.class);
        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                banderaDisponible = inmueble.getDisponible();
              //  binding.detailDomicilio.setText(inmueble.getDireccion());
                binding.txtDireccion.setText(inmueble.getDireccion());
                binding.txtCiudad.setText(inmueble.getCiudad());
                binding.txtTipo.setText(inmueble.getTipo());
                binding.txtAmbientes.setText(inmueble.getAmbientes()+"");
                binding.txtUso.setText(inmueble.getUso());
                binding.txtPrecio.setText("$ "+inmueble.getPrecio());
                binding.txtDescripcion.setText(inmueble.getDescripcion());
                binding.switch1.setChecked(banderaDisponible);
                binding.switch1.setText(inmueble.getEstado());
                String urlImg = AppParams.URL_BASE_IMG_INMU + inmueble.getUrlImg();
                Glide
                        .with(getContext())
                        .load(urlImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .transform(new RoundedCorners(30))
                        .into(binding.detailImage);

            }
        });
        mViewModel.getMSwitch().observe(getViewLifecycleOwner(), msg->{
            Log.e(TAG, "onCreateView: " + msg );
            mostrarConfirmacionDialogo(banderaDisponible,msg);
        });

        mViewModel.getBundle(getArguments());
        binding.switch1.setOnCheckedChangeListener((v, isChecked) -> {
            mViewModel.verificaEstado(banderaDisponible,isChecked);
        });
        return binding.getRoot();
    }

    private void mostrarConfirmacionDialogo(boolean banderaDisponible, String msg) {

        new AlertDialog.Builder(getContext())
                .setTitle("Cambiar estado Inmueble")
                .setMessage(banderaDisponible + "")
             //   .setMessage("¿Estas seguro ?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    Inmueble inmueble = mViewModel.getmInmueble().getValue();
                    binding.switch1.setText(msg);
                    mViewModel.cambiarEstado(inmueble.getId(), msg);
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton("No", (dialog, which)->{
                    binding.switch1.setChecked(banderaDisponible);
                })
                .show();
    }


}
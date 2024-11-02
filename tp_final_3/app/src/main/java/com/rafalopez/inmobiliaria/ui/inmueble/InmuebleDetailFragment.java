package com.rafalopez.inmobiliaria.ui.inmueble;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleDetailBinding;
import com.rafalopez.inmobiliaria.entity.Inmueble;


public class InmuebleDetailFragment extends Fragment {
    private FragmentInmuebleDetailBinding binding;
    private  InmuebleDetailViewModel mViewModel;


    public InmuebleDetailFragment() {

    }


     public static InmuebleDetailFragment newInstance() {
        return new InmuebleDetailFragment();
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
                //Inmueble{
                // id=1,
                // direccion='san martin 45',*
                // uso='Comercial',*
                // idTipo=1,*
                // ambientes=9,*
                // coordenadas='-32.414566613131946, -65.00877119828924',*
                // precio=1.26,*
                // propietarioId=4,*
                // estado='Disponible',*
                // idCiudad=1,*
                // idZona=2,*
                // borrado=false,*
                // descripcion='Casa  de 2 ambientes',*
                // urlImg='qwerty.jpg'*
                // }
                binding.detailDomicilio.setText(inmueble.getDireccion());
                String urlImg = AppParams.URL_BASE_IMG_INMU + inmueble.getId() +"/"+ inmueble.getUrlImg();
                Glide
                        .with(getContext())
                        .load(urlImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .into(binding.detailImage);

            }
        });
        mViewModel.getBundle(getArguments());



        return binding.getRoot();
    }


}
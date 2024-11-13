package com.rafalopez.inmobiliaria.ui.contrato;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.databinding.FragmentContratoDetailBinding;
import com.rafalopez.inmobiliaria.databinding.FragmentContratoDetailBinding;
import com.rafalopez.inmobiliaria.entity.Contrato;


public class ContratoDetailFragment extends Fragment {
    private static String TAG= AppParams.TAG;
    private FragmentContratoDetailBinding binding;
    private ContratoDetailViewModel mViewModel;


    public ContratoDetailFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentContratoDetailBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ContratoDetailViewModel.class);
        mViewModel.getmContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                 Log.d(TAG, "onChanged: detail fragment 78 ");

            }
        });
        mViewModel.getBundle(getArguments());



        return binding.getRoot();
    }


}
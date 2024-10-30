package com.rafalopez.inmobiliaria.ui.inmueble;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.rafalopez.inmobiliaria.databinding.FragmentPropietarioBinding;

public class InmuebleFragment extends Fragment {
    private FragmentInmuebleBinding binding;
    private InmuebleViewModel mViewModel;

    public static InmuebleFragment newInstance() {
        return new InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding=FragmentInmuebleBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);
        mViewModel.getInmuebles();
        return binding.getRoot();
    }



}
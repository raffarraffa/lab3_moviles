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

public class InmuebleNewFragment extends Fragment {

    private InmuebleNewViewModel mViewModel;

    public static InmuebleNewFragment newInstance() {
        return new InmuebleNewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inmueble_new, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmuebleNewViewModel.class);
        // TODO: Use the ViewModel
    }

}
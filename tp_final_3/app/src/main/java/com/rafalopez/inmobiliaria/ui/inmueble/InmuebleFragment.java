package com.rafalopez.inmobiliaria.ui.inmueble;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.rafalopez.inmobiliaria.databinding.FragmentPropietarioBinding;
import com.rafalopez.inmobiliaria.entity.Inmueble;

import java.util.ArrayList;
import java.util.List;

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
        mViewModel.getmListInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter inmuebleAdapter = new InmuebleAdapter(inmuebles,getContext());
                GridLayoutManager grid = new GridLayoutManager(getContext(),1,
                        GridLayoutManager.VERTICAL,false);
                binding.listaInmuebles.setLayoutManager(grid);
                binding.listaInmuebles.setAdapter(inmuebleAdapter);
                Toast.makeText(getContext(),"inmu fragment 41",Toast.LENGTH_LONG).show();
              //  Log.d(AppParams.TAG, "onChanged: fragment inmueble 43" + inmuebles);
            }
        });
        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Toast.makeText(getContext().getApplicationContext(), "Clic en: " + inmueble.getDireccion(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.getInmuebles();
        binding.btnNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"nuevo inmueble",Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }
}
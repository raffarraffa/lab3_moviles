package com.rafalopez.inmobiliaria.ui.inmueble;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.rafalopez.inmobiliaria.utils.GridSpace;

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
                GridLayoutManager grid = new GridLayoutManager(
                        getContext(),
                        1,
                        GridLayoutManager.VERTICAL,
                        false
                );
                binding.listaInmuebles.setLayoutManager(grid);
                binding.listaInmuebles.addItemDecoration(new GridSpace(8));
                int padding = getResources().getDimensionPixelSize(R.dimen.padding_8);
                binding.listaInmuebles.setPadding(padding, padding, padding, padding);
                binding.listaInmuebles.setAdapter(inmuebleAdapter);
            }
        });
        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
            }
        });
        mViewModel.getInmuebles();
        binding.btnNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_inmueble_to_inmuebleNewFragment);
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
     //   mViewModel.getInmuebles();

    }
}
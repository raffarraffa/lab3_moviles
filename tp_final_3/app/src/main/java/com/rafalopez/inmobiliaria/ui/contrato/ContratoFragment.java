package com.rafalopez.inmobiliaria.ui.contrato;

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

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentContratoBinding;
import com.rafalopez.inmobiliaria.utils.GridSpace;

public class ContratoFragment extends Fragment {
    private FragmentContratoBinding binding;

    private ContratoViewModel mViewModel;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,   @Nullable Bundle savedInstanceState)
    {
        binding  = FragmentContratoBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(ContratoViewModel.class);
        mViewModel.getmContratos().observe(getViewLifecycleOwner(), contratos->{
            ContratoAdapter contratoAdapter = new ContratoAdapter(contratos,getContext());
           GridLayoutManager grid =new GridLayoutManager(
                    getContext(),
                    1,
                    GridLayoutManager.VERTICAL,
                    false
            );
           binding.listaContratos.setLayoutManager(grid);
            binding.listaContratos.addItemDecoration(new GridSpace(8));
            int padding = getResources().getDimensionPixelSize(R.dimen.padding_8);
            binding.listaContratos.setPadding(padding, padding, padding, padding);
            binding.listaContratos.setAdapter(contratoAdapter);

            Log.e(AppParams.TAG, "onCreateView: " +contratos.size());
        });

        getContratos(mViewModel);
        return binding.getRoot();
        
    }
    private void getContratos(ContratoViewModel mViewModel){
        mViewModel.getContratos();
    }


}
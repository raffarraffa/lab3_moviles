package com.rafalopez.inmobiliaria.ui.contrato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentPagoDetailBinding;


public class PagoDetailFragment extends Fragment {
    private FragmentPagoDetailBinding binding;
    private Bundle bundle;


    public PagoDetailFragment() {

    }


    public static PagoDetailFragment newInstance() {  return new PagoDetailFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding =  FragmentPagoDetailBinding.inflate(inflater,container,false);
        String pagos= getArguments().getString("pagos");
        int contratoId=getArguments().getInt("idContrato");
        String title = "Contrato N°: "  + contratoId;
        binding.textContratoId.setText(title);
        binding.textPagosDetail.setText(pagos);
        return  binding.getRoot();
    }
}
package com.rafalopez.inmobiliaria.ui.propietario;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentPropietarioBinding;
import com.rafalopez.inmobiliaria.entity.Propietario;

public class PropietarioFragment extends Fragment {
    private PropietarioViewModel mViewModel;
    private FragmentPropietarioBinding binding;
    public static PropietarioFragment newInstance() {
        return new PropietarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentPropietarioBinding.inflate(inflater, container,false);
        mViewModel = new ViewModelProvider(this).get(PropietarioViewModel.class);
        mViewModel.getMPropietario().observe(getViewLifecycleOwner(),new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.inputNombre.setText(propietario.getNombre());
                binding.inputApellido.setText(propietario.getApellido());
                binding.inputEmail.setText(propietario.getEmail());
                binding.inputTelefono.setText(propietario.getTelefono());
                Glide.with(getContext())
                        .load(AppParams.URL_BASE_FILE+propietario.getAvatar())
                        .error(R.drawable.no_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgAvatar);
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.verSiAnda();
            }
        });
        mViewModel.verSiAnda();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
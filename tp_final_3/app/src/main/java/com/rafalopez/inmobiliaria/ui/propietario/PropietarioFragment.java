package com.rafalopez.inmobiliaria.ui.propietario;

import static android.widget.Toast.LENGTH_LONG;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentPropietarioBinding;
import com.rafalopez.inmobiliaria.entity.ActionMutable;
import com.rafalopez.inmobiliaria.entity.Propietario;

public class PropietarioFragment extends Fragment {
    private static final String TAG = "salidaDebug";
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
                binding.inputDni.setText(propietario.getDni());
                binding.inputTelefono.setText(propietario.getTelefono());
                Glide.with(getContext())
                        .load(AppParams.URL_BASE_FILE+propietario.getAvatar())
                        .error(R.drawable.no_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgAvatar);
                binding.imgAvatar.setTag(propietario.getAvatar());
            }
        });
        mViewModel.getMBtnAction().observe(getViewLifecycleOwner(),new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });
        mViewModel.getMBtnAction2().observe(getViewLifecycleOwner(),new Observer<ActionMutable>() {
            @Override
            public void onChanged(ActionMutable actionMutable) {
                binding.btnEditar.setText(actionMutable.getAction());
                inputEditable(actionMutable.isVisible());


            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Propietario prop = new Propietario();
                prop.setApellido(binding.inputApellido.getText().toString());
                prop.setNombre(binding.inputNombre.getText().toString());
                prop.setEmail(binding.inputEmail.getText().toString());
                prop.setTelefono(binding.inputTelefono.getText().toString());
                prop.setPassword(binding.inputPassword.getText().toString());
                prop.setAvatar(binding.imgAvatar.getTag().toString());
                Toast.makeText(getContext(),"Propitario freament Edita", LENGTH_LONG).show();
              //  Log.d(TAG, "onClick: ");
                mViewModel.setActionBtn2(binding.btnEditar.getText().toString(), prop);

//                binding.btnEditar.setVisibility(View.INVISIBLE);
//                mViewModel.verSiAnda();
            }
        });
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),binding.imgAvatar.getTag().toString(), LENGTH_LONG).show();
                Log.d(TAG, "onClick: " + binding.imgAvatar.getTag().toString());
            }
        });
        // llama propietario logeado
        mViewModel.getPropietario();

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void inputEditable(boolean editable) {
        binding.inputNombre.setFocusable(editable);
        binding.inputNombre.setFocusableInTouchMode(editable);
        binding.inputApellido.setFocusable(editable);
        binding.inputApellido.setFocusableInTouchMode(editable);
        binding.inputEmail.setFocusable(editable);
        binding.inputEmail.setFocusableInTouchMode(editable);
        binding.inputTelefono.setFocusable(editable);
        binding.inputTelefono.setFocusableInTouchMode(editable);
        binding.inputPassword.setFocusable(editable);
        binding.inputPassword.setFocusableInTouchMode(editable);
    }

}
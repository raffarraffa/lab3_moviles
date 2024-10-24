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

/**
 * Fragmt  info  propietario
 *
 */
public class PropietarioFragment extends Fragment {
    private static final String TAG = "salidaDebug";
    private PropietarioViewModel mViewModel;
    private FragmentPropietarioBinding binding;

    /**
     *  nueva instancia de PropietarioFragment
     *
     * @return Una nueva instancia de PropietarioFragment
     */
    public static PropietarioFragment newInstance() {
        return new PropietarioFragment();
    }

    /**
     * Infla  layout del fragmento y configura los observer del ViewModel
     *
     * @param inflater           LayoutInflater para inflar el layout
     * @param container          ViewGroup que contiene el fragmento
     * @param savedInstanceState estado guardado de la instancia
     * @return  vista inflada del fragmento
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPropietarioBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PropietarioViewModel.class);
        mViewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                Log.d(TAG, "onChanged:40 " + propietario);
                binding.inputNombre.setText(propietario.getNombre());
                binding.inputApellido.setText(propietario.getApellido());
                binding.inputEmail.setText(propietario.getEmail());
                binding.inputDni.setText(propietario.getDni());
                binding.inputTelefono.setText(propietario.getTelefono());
                Glide.with(getContext())
                        .load(AppParams.URL_BASE_FILE  + propietario.getAvatar())
                        .error(R.drawable.no_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgAvatar);
                binding.imgAvatar.setTag(propietario.getAvatar());
            }
        });
        mViewModel.getMBtnAction().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });
        mViewModel.getMBtnAction2().observe(getViewLifecycleOwner(), new Observer<ActionMutable>() {
            @Override
            public void onChanged(ActionMutable actionMutable) {
                binding.btnEditar.setText(actionMutable.getAction());
                inputEditable(actionMutable.isVisible());
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Propietario prop = new Propietario();
                prop.setApellido(binding.inputApellido.getText().toString());
                prop.setNombre(binding.inputNombre.getText().toString());
                prop.setEmail(binding.inputEmail.getText().toString());
                prop.setTelefono(binding.inputTelefono.getText().toString());
                prop.setPassword(binding.inputPassword.getText().toString());
                prop.setAvatar(binding.imgAvatar.getTag().toString());
                prop.setDni(binding.inputDni.getText().toString());
                Toast.makeText(getContext(), "Propietario Editado", LENGTH_LONG).show();
                mViewModel.setActionBtn2(binding.btnEditar.getText().toString(), prop);
            }
        });
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), binding.imgAvatar.getTag().toString(), LENGTH_LONG).show();
                Log.d(TAG, "onClick: " + binding.imgAvatar.getTag().toString());
            }
        });
        mViewModel.getProfile();
        return binding.getRoot();
    }

    /**
     * limpia el binding cuando la vista es destruida
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * cambia los campos de entrada son editables o no
     *
     * @param editable true  si los campos deben ser editables, falso de lo contrario
     */
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

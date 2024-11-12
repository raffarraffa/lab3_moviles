package com.rafalopez.inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rafalopez.inmobiliaria.data.ApiData;

public class LogoutFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostrarLogoutDialogo();
    }
    private void mostrarLogoutDialogo() {

        new AlertDialog.Builder(getContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    accionLogout();
                })
               .setNegativeButton("No", (dialog, which)->{
                requireActivity().getSupportFragmentManager().popBackStack();
                })
                .show();
    }

    private void accionLogout() {

        // eliminar tokens
        ApiData.delDataToken(getContext());
        Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

      // vivle a login
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

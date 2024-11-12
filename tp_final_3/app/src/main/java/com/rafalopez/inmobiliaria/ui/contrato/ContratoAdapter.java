package com.rafalopez.inmobiliaria.ui.contrato;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.entity.Contrato;
import com.rafalopez.inmobiliaria.entity.Inmueble;
import com.rafalopez.inmobiliaria.entity.Inquilino;
import com.rafalopez.inmobiliaria.entity.Pago;
import com.rafalopez.inmobiliaria.utils.Utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder>  {
    private static final String TAG = "salida";
    private List<Contrato> contratos;
    private  Context contexto;
    public ContratoAdapter(List<Contrato> contratos, Context contexto){
        this.contratos =contratos;
        this.contexto=contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrato, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {
        Contrato contrato = contratos.get(position);
        holder.direccion.setText(contrato.toString());
      //  holder.direccion.setText(contrato.getInmueble().getDireccion().toString() + contrato.getInquilino().getApellido().toString());
   //     holder.precio.setText(contrato.getMonto()+"");
   //     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
   //     String fecha = Utils.stringToDate(contrato.getFechaFin());
     //   holder.vence.setText(fecha.toString());
        Log.e(TAG, "onBindViewHolder: " + contrato.toString());
        if (contrato.getPagos().isEmpty()) {
             holder.btnPagos.setVisibility(View.GONE);
        } else {
                holder.btnPagos.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pagoDetail(contrato.getId(),contrato.getPagos(),v);
                    }
                });
          holder.btnPagos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
                return contratos.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
   //     TextView precio;
        TextView direccion;
  //      TextView vence;
        Button btnPagos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  precio=itemView.findViewById(R.id.textPrecio);
            direccion = itemView.findViewById(R.id.textDireccion);

            btnPagos =itemView.findViewById(R.id.btnPagos);
        }
    }
    private void pagoDetail(int id, List<Pago> pagos, View v) {
        Bundle bundle = new Bundle();
        StringBuilder infoPagos = new StringBuilder();
        pagos.forEach(p ->{
            infoPagos.append(p.toString());
        });
        bundle.putString("pagos", infoPagos.toString());
        bundle.putInt("idContrato",id);
        Log.e(TAG, "pagoDetail:\n" + infoPagos );
       // Navigation.findNavController(v).navigate(R.id.action_nav_contrato_to_pagosDetailFragment,bundle);
        Navigation.findNavController(v).navigate(R.id.action_nav_contrato_to_pagoDetailFragment,bundle);

        Log.d(TAG, "Navegación realizada hacia pagosDetailFragment con datos: \n" + infoPagos);

    }
}

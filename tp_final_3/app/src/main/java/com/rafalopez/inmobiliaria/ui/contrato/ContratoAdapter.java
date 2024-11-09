package com.rafalopez.inmobiliaria.ui.contrato;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

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
        holder.precio.setText(contrato.getMonto()+"");

       /* String urlImg = AppParams.URL_BASE_IMG_INMU + contrato.getId() +"/"+ contrato.getUrlImg();
        if (urlImg != null && !urlImg.isEmpty()) {
            Glide
                    .with(contexto)
                    .load(urlImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .into(holder.img);
        } else {
            Log.d(TAG, "Cargando imagen por defecto");
            holder.img.setImageResource(R.drawable.logo7);
        }
        Log.d(TAG, "onBindViewHolder: 47" + urlImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contratoDetail(contrato,v);
                Log.d(TAG, "onBindViewHolder: position linea 40 " + inmueble);
            }
        });*/
    }

    @Override
    public int getItemCount() {
                return contratos.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView precio;
        TextView direccion;
        TextView vence;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            precio=itemView.findViewById(R.id.textPrecio);
            direccion = itemView.findViewById(R.id.textDireccion);
            vence = itemView.findViewById(R.id.textVence);
        }
    }
    private void contratoDetail(Contrato contrato , View v) {
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("contrato",contrato);
        //Navigation.findNavController(v).navigate(R.id.action_nav_inmueble_to_inmuebleDetailFragment,bundle);
    }
}

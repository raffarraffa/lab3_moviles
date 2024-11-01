package com.rafalopez.inmobiliaria.ui.inmueble;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.entity.Inmueble;

import java.io.Serializable;
import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder>  {
    private static final String TAG = "salida";
    private List<Inmueble> inmuebles;
    private  Context contexto;
    public InmuebleAdapter(List<Inmueble> inmuebles,Context contexto){
        this.inmuebles =inmuebles;
        this.contexto=contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.direccion.setText(inmueble.getDireccion());
        holder.precio.setText("$ " + inmueble.getPrecio()+"");
        String urlImg = AppParams.URL_BASE_IMG_INMU + inmueble.getId() +"/"+ inmueble.getUrlImg();
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
                inmuebleDetail(inmueble,v);
                Log.d(TAG, "onBindViewHolder: position linea 40 " + inmueble);
            }
        });
    }

    @Override
    public int getItemCount() {
                return inmuebles.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView precio;
        TextView direccion;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            precio= itemView.findViewById(R.id.textPrecio);
            direccion=itemView.findViewById(R.id.textDomicilio);
            img=itemView.findViewById(R.id.imageInmueble);
        }
    }
    private void inmuebleDetail(Inmueble inmueble, View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("inmueble",inmueble);
        Navigation.findNavController(v).navigate(R.id.action_nav_inmueble_to_inmuebleDetailFragment,bundle);
    }
}

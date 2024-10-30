package com.rafalopez.inmobiliaria.ui.inmueble;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.rafalopez.inmobiliaria.entity.Inmueble;

import java.util.ArrayList;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {
    private static final String TAG = "salida";
    private ArrayList<Inmueble> inmuebles;
    private LayoutInflater li;
    Context contexto;
    public InmuebleAdapter(ArrayList<Inmueble> inmuebles,Context contexto,LayoutInflater li){
        this.inmuebles =inmuebles;
        this.contexto=contexto;
        this.li=li;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_inmueble,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.precio.setText(inmueble.getPrecio()+"");
        Log.d(TAG, "onBindViewHolder: ");


    }

    @Override
    public int getItemCount() {
                return inmuebles.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView precio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            precio= itemView.findViewById(R.id.textPrecio);


        }
    }
}

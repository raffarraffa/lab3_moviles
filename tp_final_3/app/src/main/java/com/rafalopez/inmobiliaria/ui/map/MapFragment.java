package com.rafalopez.inmobiliaria.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.R;

public class MapFragment extends Fragment {
    private GoogleMap mMap;
    private Marker mMarker;

    /**
     * Callback que se llama cuando el mapa
     *
     * @param googleMap El objeto GoogleMap
     */
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng mapPosicion = new LatLng(AppParams.MAP_LAT, AppParams.MAP_LON);
            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(mapPosicion)
                    .title(AppParams.MAP_TITLE)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPosicion, AppParams.MAP_ZOOM));
        }
    };

    /**
     * Infla  fragmento
     *
     * @param inflater  infldor
     * @param container contenedor
     * @param savedInstanceState  instancia
     * @return  vistainflada
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**
     *  fragmento de mapa concallback
     *
     * @param view vista creada en onCreateView
     * @param savedInstanceState  instancia
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nav_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}

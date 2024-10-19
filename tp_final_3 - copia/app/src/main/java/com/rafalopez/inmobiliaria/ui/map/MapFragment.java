package com.rafalopez.inmobiliaria.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rafalopez.inmobiliaria.R;

public class MapFragment extends Fragment {

    /**
     * Callback que se llama cuando el mapa
     *
     * @param googleMap El objeto GoogleMap
     */
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            GoogleMap mMap = googleMap;
            LatLng laPunta = new LatLng(-33.18661420561766, -66.31147456212325);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(laPunta)
                    .title("API")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            float zoomLevel = 18.0f;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laPunta, zoomLevel));
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

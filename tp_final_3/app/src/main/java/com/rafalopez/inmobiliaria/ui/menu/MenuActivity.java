package com.rafalopez.inmobiliaria.ui.menu;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.databinding.ActivityMenuBinding;

/**
 * Gestion navegación del menu
 */
public class MenuActivity extends AppCompatActivity {

    /**
     * Confg barra de aplicaciones.
     */
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * Binding  de la actividad con el diseño.
     */
    private ActivityMenuBinding binding;

    /**
     * creacionb de actividad y configurcion interfaz de usuario
     * @param savedInstanceState estado de la actividad guardada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain2.toolbar);
        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder
                (
                        R.id.nav_map,
                        R.id.nav_propietario,
                        R.id.nav_inmueble,
                        R.id.nav_contrato,
                        R.id.nav_salir
                )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * infaldo del menu en barra e
     * @param menu menu a inflar
     * @return resultado del infalte
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
        return true;
    }

    /**
     * madoir de  navegacion hacia arriba
     * @return True si se navego hacia arriba
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * cuando la actividad se reanuda
     */
    @Override
    protected void onResume() {
        super.onResume();
    }
}

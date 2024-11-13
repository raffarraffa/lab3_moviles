package com.rafalopez.inmobiliaria.ui.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.rafalopez.inmobiliaria.AppParams;
import com.rafalopez.inmobiliaria.MainActivity;
import com.rafalopez.inmobiliaria.R;
import com.rafalopez.inmobiliaria.data.ApiData;
import com.rafalopez.inmobiliaria.databinding.ActivityMenuBinding;
import com.rafalopez.inmobiliaria.entity.Propietario;
import com.rafalopez.inmobiliaria.ui.login.LoginActivity;

/**
 * Gestion navegación del menu
 */
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "salida";
    private MenuViewModel menuViewModel;
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
     *
     * @param savedInstanceState estado de la actividad guardada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        menuViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())
                .create(MenuViewModel.class);
      //  getPermisos();

        // oberver mutables
        //TODO bug en mutable
        menuViewModel.getmPropietario().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                //todo mostrar dato sperfil en head menu
                View headerView = binding.navView.getHeaderView(0);
                Glide.with(getApplication())
                        .load(AppParams.URL_BASE_IMG_AVATAR + propietario.getAvatar())
                        .error(R.drawable.no_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) headerView.findViewById(R.id.imageProfile));
                TextView headerText = headerView.findViewById(R.id.headProfileName);
                headerText.setText(propietario.getApellido() +" " + propietario.getNombre());

            }

        });
        menuViewModel.getProfile();
        setSupportActionBar(binding.appBarMain2.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder
                (
                        R.id.nav_map,
                        R.id.nav_propietario,
                        R.id.nav_inmueble,
                        R.id.nav_contrato,
                        R.id.nav_logout
                )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        Log.d(TAG, "onCreate: 94");
    }


    /**
     * infaldo del menu en barra e
     *
     * @param menu menu a inflar
     * @return resultado del infalte
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
        return true;
    }



    /**
     * contoller de  navegacion hacia arriba
     *
     * @return True si se navego hacia arriba
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);


        Log.d(TAG, "onSupportNavigateUp: kkk");
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    /**
     * cuando la actividad se reanuda
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


}

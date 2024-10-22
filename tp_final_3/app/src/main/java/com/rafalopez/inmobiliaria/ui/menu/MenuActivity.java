package com.rafalopez.inmobiliaria.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
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
        // button flotante
        menuViewModel.getProfile();

        setSupportActionBar(binding.appBarMain2.toolbar);
//        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });

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
        // listenerSalir
        //TODO aca poner u dialog d econfirmacion
        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_salir) {
                    // Borrar el token
//                    ApiData.deleteData(
//                            getApplication(),
//                            AppParams.PREFERENCES_DATA,
//                            AppParams.TOKEN_KEY
//                    );
                    Toast.makeText(MenuActivity.this, "SALIR", Toast.LENGTH_SHORT).show();

                    // Redirigir a la actividad de inicio
//                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
//                    // 'InicioActivity' por tu actividad de inicio
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Para limpiar el historial de actividades
//                    startActivity(intent);


                //    drawer.closeDrawers();
                    finish(); // Cierra la actividad actual para evitar volver atrás
                    return true;

                }
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });*/
//        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: tag");
//            }
//        });
        Log.d(TAG, "onCreate: 94");

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
     * contoller de  navegacion hacia arriba
     * @return True si se navego hacia arriba
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        Log.d(TAG, "onSupportNavigateUp: ");
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

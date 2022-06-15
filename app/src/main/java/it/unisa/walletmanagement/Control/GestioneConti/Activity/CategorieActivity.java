package it.unisa.walletmanagement.Control.GestioneConti.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.CategorieAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Adapter.ContoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaCategoriaDialog;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaMovimentoGenericoDialog;
import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.R;

public class CategorieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listViewCategorie;
    CategorieAdapter categorieAdapter;
    FloatingActionButton floatingActionButton;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_categorie);

        // navigation drawer
        drawerLayout = findViewById(R.id.drawer_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        floatingActionButton = findViewById(R.id.fab_crea_categoria);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreaCategoriaDialog creaCategoriaDialog = new CreaCategoriaDialog();
                creaCategoriaDialog.show(getSupportFragmentManager(), "Crea categoria");
            }
        });

        // list view
        listViewCategorie = findViewById(R.id.list_view_categorie);
        categorieAdapter = new CategorieAdapter(this, R.layout.list_view_categoria_element, new ArrayList<String>());
        listViewCategorie.setAdapter(categorieAdapter);

        for (int i = 0; i<10; i++){
            String test = "Prova";
            categorieAdapter.add(test);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;

        switch(item.getItemId())
        {
            case R.id.home:
                i = new Intent(CategorieActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.movimenti:
                i = new Intent(CategorieActivity.this, MovimentiActivity.class);
                startActivity(i);
                break;
            case R.id.categorie:
                break;
            case R.id.calcolatrice:
                break;
            case R.id.grafici:
                break;
            case R.id.listaSpesa:
                break;
            case R.id.impostazioni:
                break;
            case R.id.logout:
                break;
        }
        return true;
    }
}
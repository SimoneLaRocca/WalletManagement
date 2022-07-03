package it.unisa.walletmanagement.Control.ListaSpesa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Control.GestioneConti.Activity.HomeActivity;
import it.unisa.walletmanagement.Control.GestioneConti.Activity.MovimentiActivity;
import it.unisa.walletmanagement.Control.GestioneConti.Adapter.CategorieAdapter;
import it.unisa.walletmanagement.Model.Dao.ListaCategorieDAO;
import it.unisa.walletmanagement.Model.Dao.ListaSpesaDAO;
import it.unisa.walletmanagement.Model.Entity.ListaSpesa;
import it.unisa.walletmanagement.R;

public class ListaSpesaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AggiungiVoceDialog.ListaSpesaListener {

    private ListView listViewListaSpesa;
    private ListaSpesaAdapter listaSpesaAdapter;
    private ListaSpesaDAO listaSpesaDAO;

    private FloatingActionButton floatingActionButton;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_lista_spesa);

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

        floatingActionButton = findViewById(R.id.fab_aggiungi_voce);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiVoceDialog aggiungiVoceDialog = new AggiungiVoceDialog();
                aggiungiVoceDialog.show(getSupportFragmentManager(), "Aggiungi voce");
            }
        });

        listViewListaSpesa = findViewById(R.id.list_view_lista_spesa);
        listaSpesaAdapter = new ListaSpesaAdapter(this, R.layout.list_view_voce_element, new ArrayList<String>());
        listViewListaSpesa.setAdapter(listaSpesaAdapter);

        listaSpesaDAO = new ListaSpesaDAO(getApplicationContext());
        ListaSpesa listaSpesa = listaSpesaDAO.doRetrieveListaSpesa();
        if(listaSpesa != null){
            for(String item : listaSpesa.getLista()){
                listaSpesaAdapter.add(item);
            }
        }
    }

    // aggiungi nuova voce
    @Override
    public void sendVoce(String voce) {
        listaSpesaDAO.insertVoce(voce);
        listaSpesaAdapter.add(voce);
        listaSpesaAdapter.notifyDataSetChanged();
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
                i = new Intent(ListaSpesaActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.movimenti:
                i = new Intent(ListaSpesaActivity.this, MovimentiActivity.class);
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
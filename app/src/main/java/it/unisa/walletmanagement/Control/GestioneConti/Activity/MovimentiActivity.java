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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.MovimentoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.MovimentoDialog;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class MovimentiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listViewMovEntrate;
    ListView listViewMovUscite;
    MovimentoAdapter movimentoAdapterEntrate;
    MovimentoAdapter movimentoAdapterUscite;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_movimenti);

        // navigation drawer code
        drawerLayout = findViewById(R.id.drawer_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // list view entrate
        listViewMovEntrate = findViewById(R.id.list_view_movimenti_entrate);
        movimentoAdapterEntrate = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovEntrate.setAdapter(movimentoAdapterEntrate);
        for (int i = 0; i<10; i++){
            Movimento test = new Movimento(1, "Prova", null, 1, 1000, "Lavoro");
            movimentoAdapterEntrate.add(test);
        }

        // list view uscite
        listViewMovUscite = findViewById(R.id.list_view_movimenti_uscite);
        movimentoAdapterUscite = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovUscite.setAdapter(movimentoAdapterUscite);
        for (int i = 0; i<10; i++){
            Movimento test = new Movimento(1, "Prova", null, 0, 1000, "Lavoro");
            movimentoAdapterUscite.add(test);
        }

        listViewMovEntrate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // recupera il movimento, aggiungilo al bundle, crea il fragment, chiama show()
                Movimento movimento = (Movimento) listViewMovEntrate.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movimento", movimento);
                MovimentoDialog dialog = new MovimentoDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "Movimento");
            }
        });

        listViewMovUscite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // recupera il movimento, aggiungilo al bundle, crea il fragment, chiama show()
                Movimento movimento = (Movimento) listViewMovUscite.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movimento", movimento);
                MovimentoDialog dialog = new MovimentoDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "Movimento");
            }
        });
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
                i = new Intent(MovimentiActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.movimenti:
                break;
            case R.id.categorie:
                i = new Intent(MovimentiActivity.this, CategorieActivity.class);
                startActivity(i);
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
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

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.ContoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaContoDialog;
import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.R;

// Activity home usata per visualizzare la lista completa
// dei conti dell'utente
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ContoAdapter contoAdapter;
    ListView listViewConto;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_home);

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

        listViewConto = findViewById(R.id.list_view_conti);
        contoAdapter = new ContoAdapter(this, R.layout.list_view_conto_element, new ArrayList<Conto>());
        listViewConto.setAdapter(contoAdapter);

        for (int i = 0; i<10; i++){
            Conto test = new Conto("Lavoro", 2000f, null, "");
            contoAdapter.add(test);
        }

        listViewConto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conto conto = (Conto) listViewConto.getItemAtPosition(i);
                Intent intent = new Intent(HomeActivity.this, ContoActivity.class);
                intent.putExtra("conto", conto);
                startActivity(intent);
            }
        });
    }

    public void creaConto(View view) {
        CreaContoDialog creaContoDialog = new CreaContoDialog();
        creaContoDialog.show(getSupportFragmentManager(), "Crea conto");

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
                break;
            case R.id.movimenti:
                i = new Intent(HomeActivity.this, MovimentiActivity.class);
                startActivity(i);
                break;
            case R.id.categorie:
                i = new Intent(HomeActivity.this, CategorieActivity.class);
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
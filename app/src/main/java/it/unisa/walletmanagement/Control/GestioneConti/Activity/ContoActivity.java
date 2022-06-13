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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.MovimentoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.MovimentoDialog;
import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ContoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MovimentoDialog.MovimentoListener {

    MovimentoAdapter movimentoAdapter;
    ListView listViewMovimenti;
    TextView tvNomeConto, tvSaldoConto;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_conto);

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

        // imposta le info specifiche del conto selezionato
        // saldo totale: somma iniziale + somma di tutti i movimenti
        Conto conto = (Conto) getIntent().getSerializableExtra("conto");
        tvNomeConto = findViewById(R.id.text_view_nome_conto);
        tvSaldoConto = findViewById(R.id.text_view_saldo_conto);
        tvNomeConto.setText("CONTO: "+conto.getNome());

        listViewMovimenti = findViewById(R.id.list_view_movimenti_conto);
        movimentoAdapter = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovimenti.setAdapter(movimentoAdapter);

        for (int i = 0; i<10; i++){
            Random random = new Random();
            int x = random.nextInt(2);
            Movimento test = new Movimento(1, "Prova", null, x, 1000, "Lavoro");
            movimentoAdapter.add(test);
        }

        listViewMovimenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // recupera il movimento, aggiungilo al bundle, crea il fragment, chiama show()
                Movimento movimento = (Movimento) listViewMovimenti.getItemAtPosition(i);
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
                i = new Intent(ContoActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.movimenti:
                i = new Intent(ContoActivity.this, MovimentiActivity.class);
                startActivity(i);
                break;
            case R.id.categorie:
                i = new Intent(ContoActivity.this, CategorieActivity.class);
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

    @Override
    public void sendMovimento(Movimento movimento) {
        // listener MovimentoFragment
    }
}
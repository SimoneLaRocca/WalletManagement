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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.MovimentoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaMovimentoGenericoDialog;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.ModificaMovimentoDialog;
import it.unisa.walletmanagement.Model.Dao.ContoDAO;
import it.unisa.walletmanagement.Model.Dao.MovimentoDAO;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class MovimentiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ModificaMovimentoDialog.ModificaMovimentoListener, CreaMovimentoGenericoDialog.CreaMovimentoGenericoListener {

    ListView listViewMovEntrate;
    ListView listViewMovUscite;
    MovimentoAdapter movimentoAdapterEntrate;
    MovimentoAdapter movimentoAdapterUscite;
    ArrayList<Movimento> lista_entrate;
    ArrayList<Movimento> lista_uscite;
    MovimentoDAO movimentoDAO;

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

        listViewMovEntrate = findViewById(R.id.list_view_movimenti_entrate);
        movimentoAdapterEntrate = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovEntrate.setAdapter(movimentoAdapterEntrate);

        movimentoDAO = new MovimentoDAO(getApplicationContext());
        lista_entrate = (ArrayList<Movimento>) movimentoDAO.doRetrieveByType(1);
        if(lista_entrate != null){
            for(Movimento m : lista_entrate){
                movimentoAdapterEntrate.add(m);
            }
        }

        listViewMovUscite = findViewById(R.id.list_view_movimenti_uscite);
        movimentoAdapterUscite = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovUscite.setAdapter(movimentoAdapterUscite);

        lista_uscite = (ArrayList<Movimento>) movimentoDAO.doRetrieveByType(0);
        if(lista_uscite != null){
            for(Movimento m : lista_uscite){
                movimentoAdapterUscite.add(m);
            }
        }

        listViewMovEntrate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // recupera il movimento, aggiungilo al bundle, crea il fragment, chiama show()
                Movimento movimento = (Movimento) listViewMovEntrate.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movimento", movimento);
                ModificaMovimentoDialog dialog = new ModificaMovimentoDialog();
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
                ModificaMovimentoDialog dialog = new ModificaMovimentoDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "Movimento");
            }
        });
    }

    public void creaMovimentoGenerico(View view) {
        // mostra toast customizzato: per aggiungere movimenti devi prima creare un conto
        ContoDAO contoDAO = new ContoDAO(getApplicationContext());
        if(contoDAO.doCount() < 1){
            showToastCustomizzato();
        }else {
            CreaMovimentoGenericoDialog creaMovimentoGenericoDialog = new CreaMovimentoGenericoDialog();
            creaMovimentoGenericoDialog.show(getSupportFragmentManager(), "Crea movimento generico");
        }
    }

    @Override
    public void sendUpdatedMovimento(Movimento oldMovimento, Movimento newMovimento) {
        if(oldMovimento.getTipo() == 1)
            movimentoAdapterEntrate.remove(oldMovimento);
        else
            movimentoAdapterUscite.remove(oldMovimento);

        if(newMovimento.getTipo() == 1){
            movimentoDAO.updateMovimento(newMovimento);
            movimentoAdapterEntrate.add(newMovimento);
            movimentoAdapterEntrate.notifyDataSetChanged();
        }else {
            movimentoDAO.updateMovimento(newMovimento);
            movimentoAdapterUscite.add(newMovimento);
            movimentoAdapterUscite.notifyDataSetChanged();
        }
    }

    @Override
    public void sendNewMovimentoGenerico(Movimento movimento, String nome_conto) {
        movimentoDAO.insertMovimento(movimento, nome_conto);
        if(movimento.getTipo() == 1){
            movimentoAdapterEntrate.add(movimento);
            movimentoAdapterEntrate.notifyDataSetChanged();
        }else {
            movimentoAdapterUscite.add(movimento);
            movimentoAdapterUscite.notifyDataSetChanged();
        }
    }

    public void showToastCustomizzato() {
        Toast toast = new Toast(getApplicationContext());
        toast.setView(getLayoutInflater().inflate(R.layout.custom_toast, null));
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
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
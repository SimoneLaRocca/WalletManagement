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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Control.GestioneConti.Adapter.MovimentoAdapter;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaMovimentoDialog;
import it.unisa.walletmanagement.Control.GestioneConti.Fragment.ModificaMovimentoDialog;
import it.unisa.walletmanagement.Model.Dao.ContoDAO;
import it.unisa.walletmanagement.Model.Dao.ListaCategorieDAO;
import it.unisa.walletmanagement.Model.Dao.MovimentoDAO;
import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ContoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            ModificaMovimentoDialog.ModificaMovimentoListener,
                CreaMovimentoDialog.CreaMovimentoListener, MovimentoAdapter.MovimentoListener {

    MovimentoAdapter movimentoAdapter;
    ListView listViewMovimenti;
    Conto conto;
    ContoDAO contoDAO;
    MovimentoDAO movimentoDAO;
    ListaCategorieDAO listaCategorieDAO;

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

        // Imposta le info specifiche del conto selezionato
        // saldo totale: somma iniziale + somma di tutti i movimenti
        conto = (Conto) getIntent().getSerializableExtra("conto");
        tvNomeConto = findViewById(R.id.text_view_nome_conto);
        tvSaldoConto = findViewById(R.id.text_view_saldo_conto);

        listViewMovimenti = findViewById(R.id.list_view_movimenti_conto);
        movimentoAdapter = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>(), this);
        listViewMovimenti.setAdapter(movimentoAdapter);

        contoDAO = new ContoDAO(getApplicationContext());
        movimentoDAO = new MovimentoDAO(getApplicationContext());
        Conto conto2 = contoDAO.doRetrieveByName(conto.getNome());
        if(conto2 != null) {
            for (Movimento movimento : conto2.getMovimenti()) {
                movimentoAdapter.add(movimento);
            }
        }

        tvNomeConto.setText("CONTO: " + conto.getNome());
        tvSaldoConto.setText("Saldo corrente: " + movimentoDAO.doRetrieveCurrentBalance(conto.getNome()));

        listaCategorieDAO = new ListaCategorieDAO(getApplicationContext());

        listViewMovimenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // recupera il movimento, aggiungilo al bundle, crea il fragment, chiama show()
                if(listaCategorieDAO.doRetrieveListaCategorie() == null) {
                    showToastCustomizzato(R.layout.custom_toast_categoria);
                } else {
                    Movimento movimento = (Movimento) listViewMovimenti.getItemAtPosition(i);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movimento", movimento);
                    ModificaMovimentoDialog dialog = new ModificaMovimentoDialog();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "Movimento");
                }
            }
        });
    }

    public void creaMovimento(View view) {
        if(listaCategorieDAO.doRetrieveListaCategorie() == null) {
            showToastCustomizzato(R.layout.custom_toast_categoria);
        } else {
            CreaMovimentoDialog creaMovimentoDialog = new CreaMovimentoDialog();
            creaMovimentoDialog.show(getSupportFragmentManager(), "Crea movimento");
        }
    }

    // modifica movimento
    @Override
    public void sendUpdatedMovimento(Movimento oldMovimento, Movimento newMovimento) {
        movimentoAdapter.remove(oldMovimento);
        movimentoDAO.updateMovimento(newMovimento);
        movimentoAdapter.add(newMovimento);
        movimentoAdapter.notifyDataSetChanged();
        tvSaldoConto.setText("Saldo corrente: " + movimentoDAO.doRetrieveCurrentBalance(conto.getNome()));
    }

    // aggiunta nuovo movimento
    @Override
    public void sendNewMovimento(Movimento movimento) {
        movimentoDAO.insertMovimento(movimento, conto.getNome());
        movimentoAdapter.add(movimento);
        movimentoAdapter.notifyDataSetChanged();
        tvSaldoConto.setText("Saldo corrente: " + movimentoDAO.doRetrieveCurrentBalance(conto.getNome()));
    }

    // cancellazione movimento
    @Override
    public void deleteMovimento(Movimento movimento) {
        tvSaldoConto.setText("Saldo corrente: " + movimentoDAO.doRetrieveCurrentBalance(conto.getNome()));
    }

    public void showToastCustomizzato(int layout) {
        Toast toast = new Toast(getApplicationContext());
        toast.setView(getLayoutInflater().inflate(layout, null));
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
}
package it.unisa.walletmanagement.Control.Impostazioni;

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

import com.google.android.material.navigation.NavigationView;

import it.unisa.walletmanagement.Control.GestioneConti.Activity.CategorieActivity;
import it.unisa.walletmanagement.Control.GestioneConti.Activity.HomeActivity;
import it.unisa.walletmanagement.Control.GestioneConti.Activity.MovimentiActivity;
import it.unisa.walletmanagement.Control.Impostazioni.Fragment.CambiaPasswordDialog;
import it.unisa.walletmanagement.Control.Impostazioni.Fragment.ImpostaPasswordDialog;
import it.unisa.walletmanagement.Control.Impostazioni.Fragment.RimuoviPasswordDialog;
import it.unisa.walletmanagement.R;

public class ImpostazioniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            ImpostaPasswordDialog.PasswordListener,
                CambiaPasswordDialog.PasswordListener,
                    RimuoviPasswordDialog.PasswordListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SecurityManager securityManager = new SecurityManager(getApplicationContext());

        if(!securityManager.isSecurityEnabled()){
            setContentView(R.layout.nav_activity_impostazioni);
        }else {
            setContentView(R.layout.nav_activity_impostazioni2);
        }

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

    }

    public void setPassword(View view) {
        ImpostaPasswordDialog impostaPasswordDialog = new ImpostaPasswordDialog();
        impostaPasswordDialog.show(getSupportFragmentManager(), "Imposta password");
    }

    public void changePassword(View view) {
        CambiaPasswordDialog cambiaPasswordDialog = new CambiaPasswordDialog();
        cambiaPasswordDialog.show(getSupportFragmentManager(), "Cambia password");
    }

    public void removePassword(View view) {
        RimuoviPasswordDialog rimuoviPasswordDialog = new RimuoviPasswordDialog();
        rimuoviPasswordDialog.show(getSupportFragmentManager(), "Rimuovi password");
    }

    // imposta password
    @Override
    public void sendPassword(String password) {
        SecurityManager securityManager = new SecurityManager(getApplicationContext());
        securityManager.doSavePassword(password);
    }

    // cambia password
    @Override
    public void sendNewPassword(String password) {
        SecurityManager securityManager = new SecurityManager(getApplicationContext());
        securityManager.doSavePassword(password);
    }

    // rimuovi password
    @Override
    public void sendDeletedPassword(String password) {
        SecurityManager securityManager = new SecurityManager(getApplicationContext());
        securityManager.doRemovePassword(password);
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
                i = new Intent(ImpostazioniActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.movimenti:
                i = new Intent(ImpostazioniActivity.this, MovimentiActivity.class);
                startActivity(i);
                break;
            case R.id.categorie:
                i = new Intent(ImpostazioniActivity.this, CategorieActivity.class);
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
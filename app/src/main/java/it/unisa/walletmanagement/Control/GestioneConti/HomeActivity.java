package it.unisa.walletmanagement.Control.GestioneConti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.R;

// Activity home usata per visualizzare la lista completa
// dei conti dell'utente
public class HomeActivity extends AppCompatActivity {

    ContoAdapter contoAdapter;
    ListView listViewConto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        // apri fragment dialog per inserire input

    }
}
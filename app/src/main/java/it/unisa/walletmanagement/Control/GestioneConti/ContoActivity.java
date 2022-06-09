package it.unisa.walletmanagement.Control.GestioneConti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ContoActivity extends AppCompatActivity {

    MovimentoAdapter movimentoAdapter;
    ListView listViewMovimenti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conto);

        listViewMovimenti = findViewById(R.id.list_view_movimenti_conto);
        movimentoAdapter = new MovimentoAdapter(this, R.layout.list_view_movimento_element, new ArrayList<Movimento>());
        listViewMovimenti.setAdapter(movimentoAdapter);

        for (int i = 0; i<10; i++){
            Random random = new Random();
            int x = random.nextInt(2);
            Movimento esempio_movimento = new Movimento(1, "Prova", null, x, 1000, "Lavoro");
            movimentoAdapter.add(esempio_movimento);
        }

        listViewMovimenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // test
            }
        });
    }
}
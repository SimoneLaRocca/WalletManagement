package it.unisa.walletmanagement.Control.GestioneConti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ContoActivity extends AppCompatActivity {

    MovimentoAdapter movimentoAdapter;
    ListView listViewMovimenti;
    TextView tvNomeConto, tvSaldoConto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conto);

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
                MovimentoFragment dialog = new MovimentoFragment();
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Movimento");
            }
        });
    }
}
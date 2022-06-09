package it.unisa.walletmanagement.Control.GestioneConti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.R;

public class ContoAdapter extends ArrayAdapter<Conto> {
    public ContoAdapter(@NonNull Context context, int resource, @NonNull List<Conto> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_conto_element, parent, false);
        }
        Conto conto = getItem(position);

        TextView tvNome = convertView.findViewById(R.id.tv_nome_conto);
        TextView tvSaldo = convertView.findViewById(R.id.tv_saldo_conto);
        ImageView ivCancella = convertView.findViewById(R.id.image_view_cancella_conto);
        ivCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContoAdapter.this.remove(getItem(position));
                ContoAdapter.this.notifyDataSetChanged();
                // cancella conto
            }
        });

        tvNome.setText(conto.getNome());
        tvSaldo.setText("€ "+Float.toString(conto.getSaldo()));

        return convertView;
    }
}

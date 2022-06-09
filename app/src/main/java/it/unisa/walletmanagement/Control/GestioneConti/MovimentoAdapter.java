package it.unisa.walletmanagement.Control.GestioneConti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class MovimentoAdapter extends ArrayAdapter<Movimento> {

    public MovimentoAdapter(@NonNull Context context, int resource, @NonNull List<Movimento> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_movimento_element, parent, false);
        }
        Movimento movimento = getItem(position);

        FrameLayout flColor = convertView.findViewById(R.id.frame_layout_color);
        TextView tvCategoria = convertView.findViewById(R.id.tv_categoria_movimento);
        TextView tvValore = convertView.findViewById(R.id.tv_valore_movimento);
        ImageView ivCancella = convertView.findViewById(R.id.image_view_cancella_movimento);
        ivCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovimentoAdapter.this.remove(getItem(position));
                MovimentoAdapter.this.notifyDataSetChanged();
                // cancella movimento
            }
        });

        if(movimento.getTipo() == 0){
            flColor.setBackgroundColor(0xFFFF0000);
        }else {
            flColor.setBackgroundColor(0xFF4CAF50);
        }

        tvCategoria.setText(movimento.getCategoria());
        tvValore.setText("€ "+Float.toString(movimento.getValore()));

        return convertView;
    }
}

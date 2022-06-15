package it.unisa.walletmanagement.Control.GestioneConti.Adapter;

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

public class CategorieAdapter extends ArrayAdapter<String> {

    public CategorieAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_categoria_element, parent, false);
        }

        String categoria = getItem(position);

        TextView tvCategoria = convertView.findViewById(R.id.text_view_categoria);
        ImageView ivCancella = convertView.findViewById(R.id.image_view_cancella_categoria);
        ivCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategorieAdapter.this.remove(getItem(position));
                CategorieAdapter.this.notifyDataSetChanged();
                // ToDo: cancella categoria
            }
        });

        tvCategoria.setText(categoria);

        return convertView;
    }
}

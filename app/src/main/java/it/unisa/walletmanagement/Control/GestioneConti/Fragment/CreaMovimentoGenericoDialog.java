package it.unisa.walletmanagement.Control.GestioneConti.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import it.unisa.walletmanagement.R;

public class CreaMovimentoGenericoDialog extends DialogFragment {

    TextView tvCancel, tvOK;
    EditText etNome, etImporto;
    Spinner dropdown_conto;
    Spinner dropdown_categoria;
    ArrayAdapter<String> adapter_categorie;
    ArrayAdapter<String> adapter_conti;
    String[] categorie;
    String[] conti;
    Button entrata, uscita;

    public CreaMovimentoGenericoDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crea_movimento_generico, container, false);

        dropdown_categoria = view.findViewById(R.id.spinner_categoria);
        dropdown_conto = view.findViewById(R.id.spinner_conto);

        // ToDo: popolare con la lista delle categorie
        categorie = new String[]{"Lavoro", "Banca", "Spesa"};
        adapter_categorie = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, categorie);
        dropdown_categoria.setAdapter(adapter_categorie);

        // ToDo: popolare con la lista dei conti
        conti = new String[]{"Visa", "BancoPosta", "Mastercard"};
        adapter_conti = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, conti);
        dropdown_conto.setAdapter(adapter_conti);

        etNome = view.findViewById(R.id.edit_text_nome_movimento);
        etImporto = view.findViewById(R.id.edit_text_importo_movimento);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOK = view.findViewById(R.id.tv_ok);
        entrata = view.findViewById(R.id.button_entrata_movimento);
        uscita = view.findViewById(R.id.button_uscita_movimento);

        entrata.setTag(false);
        uscita.setTag(false);

        uscita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag().equals(false)){
                    Drawable buttonDrawable = view.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, 0xFFFF0000);
                    view.setBackground(buttonDrawable);
                    view.setTag(true);

                    buttonDrawable = entrata.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, 0xFF8F8F8F);
                    entrata.setBackground(buttonDrawable);
                    entrata.setTag(false);
                }
            }
        });

        entrata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag().equals(false)){
                    Drawable buttonDrawable = view.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, 0xFF4CAF50);
                    view.setBackground(buttonDrawable);
                    view.setTag(true);

                    buttonDrawable = uscita.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, 0xFF8F8F8F);
                    uscita.setBackground(buttonDrawable);
                    uscita.setTag(false);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo: crea il movimento o restituisci l'input all'activity
                getDialog().dismiss();
            }
        });

        return view;
    }
}
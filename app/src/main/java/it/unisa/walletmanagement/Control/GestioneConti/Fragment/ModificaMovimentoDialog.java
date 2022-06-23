package it.unisa.walletmanagement.Control.GestioneConti.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import java.util.GregorianCalendar;

import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ModificaMovimentoDialog extends androidx.fragment.app.DialogFragment {

    TextView tvCancel, tvOK;
    EditText etNome, etImporto;
    Spinner dropdown;
    ArrayAdapter<String> adapter;
    String[] categorie;
    Button entrata, uscita;
    Movimento movimento;

    // interfaccia usata per inviare dati all'activity
    public interface MovimentoListener{
        void sendUpdatedMovimento(Movimento oldMovimento, Movimento newMovimento);
    }

    public MovimentoListener movimentoListener;

    public ModificaMovimentoDialog() {
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
        // Possibile riutilizzare il layout fragment_movimento, cambiando la text view iniziale
        View view = inflater.inflate(R.layout.fragment_modifica_movimento, container, false);

        movimento = (Movimento) this.getArguments().getSerializable("movimento");

        dropdown = view.findViewById(R.id.spinner1);
        // ToDo: popolare con la lista delle categorie
        categorie = new String[]{"Lavoro", "Banca", "Spesa"};
        adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, categorie);
        dropdown.setAdapter(adapter);

        etNome = view.findViewById(R.id.edit_text_nome_movimento);
        etImporto = view.findViewById(R.id.edit_text_importo_movimento);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOK = view.findViewById(R.id.tv_ok);
        entrata = view.findViewById(R.id.button_entrata_movimento);
        uscita = view.findViewById(R.id.button_uscita_movimento);

        // imposta i campi con i valori dell'item movimento selezionato
        etNome.setText(movimento.getNome());
        etImporto.setText("" + movimento.getImporto());
        // imposta categoria
        int spinner_position = adapter.getPosition(movimento.getCategoria());
        dropdown.setSelection(spinner_position);

        if(movimento.getTipo() == 0){
            Drawable buttonDrawable = uscita.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, 0xFFFF0000);
            uscita.setBackground(buttonDrawable);
            uscita.setTag(true);
            entrata.setTag(false);
        }else {
            Drawable buttonDrawable = entrata.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, 0xFF4CAF50);
            entrata.setBackground(buttonDrawable);
            entrata.setTag(true);
            uscita.setTag(false);
        }

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
                Movimento newMovimento = new Movimento();
                newMovimento.setId(movimento.getId());
                newMovimento.setNome(etNome.getText().toString());
                newMovimento.setImporto(Float.parseFloat(etImporto.getText().toString()));
                newMovimento.setCategoria((String) dropdown.getSelectedItem());
                newMovimento.setData(movimento.getData());
                if(entrata.getTag().equals(true)){
                    newMovimento.setTipo(1);
                }else {
                    newMovimento.setTipo(0);
                }
                movimentoListener.sendUpdatedMovimento(movimento, newMovimento);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            movimentoListener = (MovimentoListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
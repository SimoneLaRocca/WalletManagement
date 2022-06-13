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

import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class MovimentoDialog extends androidx.fragment.app.DialogFragment {

    TextView tvCancel, tvOK;
    EditText etNome, etValore;
    Spinner dropdown;
    ArrayAdapter<String> adapter;
    String[] categorie;
    Button entrata, uscita;

    // interfaccia usata per inviare dati all'activity
    public interface MovimentoListener{
        void sendMovimento(Movimento movimento);
    }

    public MovimentoListener movimentoListener;

    public MovimentoDialog() {
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
        View view = inflater.inflate(R.layout.fragment_movimento, container, false);

        Movimento movimento = (Movimento) this.getArguments().getSerializable("movimento");

        dropdown = view.findViewById(R.id.spinner1);
        // popolare con la lista delle categorie
        categorie = new String[]{"Lavoro", "Banca", "Spesa"};
        adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, categorie);
        dropdown.setAdapter(adapter);

        etNome = view.findViewById(R.id.edit_text_nome_movimento);
        etValore = view.findViewById(R.id.edit_text_valore_movimento);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOK = view.findViewById(R.id.tv_ok);
        entrata = view.findViewById(R.id.button_entrata_movimento);
        uscita = view.findViewById(R.id.button_uscita_movimento);

        // imposta i campi con i valori dell'item movimento selezionato
        etNome.setText(movimento.getNome());
        etValore.setText("" + movimento.getValore());
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
                // modifica i campi dell'oggetto movimento
                // usa il listener per inviare l'input inserito
                // dall'utente all'activity se necessario
                // movimentoListener.sendMovimento(movimento);
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
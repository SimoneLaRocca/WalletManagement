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

import it.unisa.walletmanagement.Model.Dao.ListaCategorieDAO;
import it.unisa.walletmanagement.Model.Entity.ListaCategorie;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class ModificaMovimentoDialog extends androidx.fragment.app.DialogFragment {

    private TextView tvCancel, tvOK;
    private EditText etNome, etImporto;
    private Spinner dropdown;
    private ArrayAdapter<String> adapter;
    private ListaCategorie categorie;
    private Button entrata, uscita;
    private Movimento movimento;

    // interfaccia usata per inviare dati all'activity
    public interface ModificaMovimentoListener {
        void sendUpdatedMovimento(Movimento oldMovimento, Movimento newMovimento);
    }

    private ModificaMovimentoListener modificaMovimentoListener;

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

        ListaCategorieDAO listaCategorieDAO = new ListaCategorieDAO(getActivity().getApplicationContext());
        categorie = listaCategorieDAO.doRetrieveListaCategorie();
        adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, categorie.getCategorie());
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
                if(CheckAllFields()){
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
                    modificaMovimentoListener.sendUpdatedMovimento(movimento, newMovimento);
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    private boolean CheckAllFields() {
        if (etNome.getText().toString().length() == 0) {
            etNome.setError("Questo campo è richiesto");
            return false;
        }

        if (etImporto.getText().toString().length() == 0) {
            etImporto.setError("Questo campo è richiesto");
            return false;
        } else if(Float.parseFloat(etImporto.getText().toString()) < 0){
            etImporto.setError("L'importo deve essere positivo");
            return false;
        }

        if (entrata.getTag().equals(false) && uscita.getTag().equals(false)) {
            return false;
        }

        // after all validation return true.
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            modificaMovimentoListener = (ModificaMovimentoListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
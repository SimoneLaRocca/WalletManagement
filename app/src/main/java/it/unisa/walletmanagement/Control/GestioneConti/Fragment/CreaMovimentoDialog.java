package it.unisa.walletmanagement.Control.GestioneConti.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.GregorianCalendar;

import it.unisa.walletmanagement.Model.Dao.ListaCategorieDAO;
import it.unisa.walletmanagement.Model.Entity.ListaCategorie;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.R;

public class CreaMovimentoDialog extends DialogFragment {

    private TextView tvCancel, tvOK;
    private EditText etNome, etImporto;
    private Spinner dropdown;
    private ArrayAdapter<String> adapter;
    private ListaCategorie categorie;
    private Button entrata, uscita;

    public interface CreaMovimentoListener{
        void sendNewMovimento(Movimento movimento);
    }

    private CreaMovimentoListener creaMovimentoListener;

    public CreaMovimentoDialog() {
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
        View view = inflater.inflate(R.layout.fragment_crea_movimento, container, false);

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
                Movimento m = new Movimento();
                m.setNome(etNome.getText().toString());
                m.setImporto(Float.parseFloat(etImporto.getText().toString()));
                m.setCategoria((String) dropdown.getSelectedItem());
                m.setData(new GregorianCalendar());
                if(entrata.getTag().equals(true)){
                    m.setTipo(1);
                }else {
                    m.setTipo(0);
                }
                creaMovimentoListener.sendNewMovimento(m);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            creaMovimentoListener = (CreaMovimentoListener) getActivity();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
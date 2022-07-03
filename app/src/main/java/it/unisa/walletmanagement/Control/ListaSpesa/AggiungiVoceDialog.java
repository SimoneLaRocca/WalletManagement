package it.unisa.walletmanagement.Control.ListaSpesa;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.unisa.walletmanagement.Control.GestioneConti.Fragment.CreaCategoriaDialog;
import it.unisa.walletmanagement.R;

public class AggiungiVoceDialog extends DialogFragment {

    private TextView tvCancel, tvOK;
    private EditText etVoce;

    public interface ListaSpesaListener{
        void sendVoce(String voce);
    }

    private ListaSpesaListener listaSpesaListener;

    public AggiungiVoceDialog() {
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
        View view = inflater.inflate(R.layout.fragment_aggiungi_voce_dialog, container, false);

        etVoce = view.findViewById(R.id.edit_text_voce);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOK = view.findViewById(R.id.tv_ok);

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
                    String voce = etVoce.getText().toString();
                    listaSpesaListener.sendVoce(voce);
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    // validazione input
    private boolean CheckAllFields() {
        if (etVoce.getText().toString().length() == 0) {
            etVoce.setError("Questo campo è richiesto");
            return false;
        }

        // after all validation return true.
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listaSpesaListener = (ListaSpesaListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
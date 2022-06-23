package it.unisa.walletmanagement.Model.Dao;

import android.content.Context;

import it.unisa.walletmanagement.Model.Entity.ListaCategorie;
import it.unisa.walletmanagement.Model.Entity.ListaSpesa;
import it.unisa.walletmanagement.Model.Storage.FileManager;

public class ListaSpesaDAO {

    private String fileName;
    private Context context;

    public ListaSpesaDAO(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public ListaSpesaDAO(Context context) {
        this.context = context;
        this.fileName = "lista_spesa.txt";
    }

    public boolean insertVoce(String voce){
        if(FileManager.writeToFile(context, fileName, voce, true)){
            return true;
        }
        return false;
    }

    public void deleteVoce(String voce){
        FileManager.deleteRecordFromFile(context, fileName, voce);
    }

    public ListaSpesa doRetrieveListaSpesa(){
        ListaSpesa listaSpesa = new ListaSpesa();
        listaSpesa.setLista(FileManager.readListFromFile(context, fileName));
        return listaSpesa;
    }
}

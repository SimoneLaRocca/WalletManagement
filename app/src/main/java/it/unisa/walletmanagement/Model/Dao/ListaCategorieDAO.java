package it.unisa.walletmanagement.Model.Dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import it.unisa.walletmanagement.Model.Entity.ListaCategorie;
import it.unisa.walletmanagement.Model.Storage.FileManager;

public class ListaCategorieDAO {

    private String fileName;
    private Context context;

    public ListaCategorieDAO(Context context) {
        this.context = context;
        this.fileName = "categorie.txt";
    }

    public ListaCategorieDAO(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public boolean insertCategoria(String nome){
        if(FileManager.writeRecordToFile(context, fileName, nome, true)){
            return true;
        }
        return false;
    }

    public void deleteCategoria(String nome){
        FileManager.deleteRecordFromFile(context, fileName, nome);
    }

    public ListaCategorie doRetrieveListaCategorie(){
        ListaCategorie listaCategorie = new ListaCategorie();
        List<String> list = FileManager.readListFromFile(context, fileName);
        if(list == null || list.size() == 0){
            return null;
        }
        listaCategorie.setCategorie(list);
        return listaCategorie;
    }
}

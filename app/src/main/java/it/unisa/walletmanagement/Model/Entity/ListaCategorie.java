package it.unisa.walletmanagement.Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListaCategorie implements Serializable {
    private List<String> categorie;

    public ListaCategorie(List<String> categorie) {
        this.categorie = categorie;
    }

    public ListaCategorie(String ... list) {
        this.categorie = new ArrayList<String>();
        for (String item : list){
            this.categorie.add(item);
        }
    }

    public List<String> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<String> categorie) {
        this.categorie = categorie;
    }

    public void addCategoria(String nome){
        this.categorie.add(nome);
    }

    public boolean removeCategoria(String nome){
        if(this.categorie.contains(nome)){
            this.categorie.remove(nome);
            return true;
        }
        return false;
    }

    public boolean containsCategoria(String nome){
        if(this.categorie.contains(nome)){
            return true;
        }
        return false;
    }
}

package it.unisa.walletmanagement.Model.Entity;

import java.util.Set;
import java.util.TreeSet;

public class ListaCategorie {
    private Set<String> categorie;

    public ListaCategorie(Set<String> categorie) {
        this.categorie = categorie;
    }

    public ListaCategorie(String ... list) {
        this.categorie = new TreeSet<>();
        for (String item : list){
            this.categorie.add(item);
        }
    }

    public Set<String> getCategorie() {
        return categorie;
    }

    public void setCategorie(Set<String> categorie) {
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

    public boolean searchCategoria(String nome){
        if(this.categorie.contains(nome)){
            return true;
        }
        return false;
    }
}

package it.unisa.walletmanagement.Model.Entity;

import java.util.List;

public class ListaSpesa {
    private int id;
    private List<String> lista;

    public ListaSpesa(int id, List<String> lista) {
        this.id = id;
        this.lista = lista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public void addVoce(String voce){
        lista.add(voce);
    }

    public void removeVoce(String voce){
        lista.remove(voce);
    }
}

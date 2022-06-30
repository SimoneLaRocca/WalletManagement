package it.unisa.walletmanagement.Model.Entity;

import java.io.Serializable;
import java.util.List;

public class ListaSpesa implements Serializable {
    private List<String> lista;

    public ListaSpesa(List<String> lista) {
        this.lista = lista;
    }

    public ListaSpesa() {
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
}

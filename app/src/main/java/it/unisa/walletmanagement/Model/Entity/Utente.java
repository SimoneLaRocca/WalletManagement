package it.unisa.walletmanagement.Model.Entity;

import java.util.List;

public class Utente {
    private String nome;
    private String cognome;
    private List<Conto> conti;

    public Utente(String nome, String cognome, List<Conto> conti) {
        this.nome = nome;
        this.cognome = cognome;
        this.conti = conti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<Conto> getConti() {
        return conti;
    }

    public void setConti(List<Conto> conti) {
        this.conti = conti;
    }

    public void addConto(Conto c){
        this.conti.add(c);
    }

    public boolean removeConto(String nome){
        for (Conto item: this.conti){
            if(item.getNome().equals(nome)){
                this.conti.remove(item);
                return true;
            }
        }
        return false;
    }
}

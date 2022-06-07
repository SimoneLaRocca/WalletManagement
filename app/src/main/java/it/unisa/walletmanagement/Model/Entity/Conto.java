package it.unisa.walletmanagement.Model.Entity;

import java.util.List;

public class Conto {
    private String nome;
    private float saldo;
    private List<Movimento> movimenti;
    private String descrizione;

    public Conto(String nome, float saldo, List<Movimento> movimenti, String descrizione) {
        this.nome = nome;
        this.saldo = saldo;
        this.movimenti = movimenti;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public List<Movimento> getMovimenti() {
        return movimenti;
    }

    public void setMovimenti(List<Movimento> movimenti) {
        this.movimenti = movimenti;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Movimento> searchMovimento(){
        // quale criterio di ricerca?
        return null;
    }

    public void addMovimento(Movimento m){
        this.movimenti.add(m);
    }

    public boolean removeMovimento(Movimento m){
        if(this.movimenti.contains(m)){
            this.movimenti.remove(m);
            return true;
        }
        return false;
    }
}

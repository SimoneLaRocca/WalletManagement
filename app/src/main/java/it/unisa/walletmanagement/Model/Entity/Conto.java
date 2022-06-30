package it.unisa.walletmanagement.Model.Entity;

import java.io.Serializable;
import java.util.List;

public class Conto implements Serializable {
    private String nome;
    private float saldo;
    private List<Movimento> movimenti;

    public Conto() {
    }

    public Conto(String nome, float saldo, List<Movimento> movimenti) {
        this.nome = nome;
        this.saldo = saldo;
        this.movimenti = movimenti;
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
}

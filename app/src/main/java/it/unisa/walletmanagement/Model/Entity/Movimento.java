package it.unisa.walletmanagement.Model.Entity;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Movimento implements Serializable {
    private int id;
    private String nome;
    private GregorianCalendar data;
    private int tipo; // 0: uscita , 1: entrata
    private float valore;
    private String categoria;

    public Movimento(int id, String nome, GregorianCalendar data, int tipo, float valore, String categoria) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
        this.valore = valore;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getValore() {
        return valore;
    }

    public void setValore(float valore) {
        this.valore = valore;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

package it.unisa.walletmanagement.Model.Entity;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Movimento implements Serializable {
    private int id; // id < 0: movimento non memorizzato nel db
    private String nome;
    private float importo;
    private int tipo; // 0 = uscita , 1 = entrata
    private GregorianCalendar data;
    private String categoria;

    public Movimento() {
    }

    public Movimento(int id, String nome, float importo, int tipo, GregorianCalendar data, String categoria) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
        this.importo = importo;
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

    public float getImporto() {
        return importo;
    }

    public void setImporto(float importo) {
        this.importo = importo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

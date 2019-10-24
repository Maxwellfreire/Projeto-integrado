package br.com.integrador.adm.model;

import java.io.Serializable;

public class Cupomdesconto implements Serializable {

    private Integer id;
    private String name;
    private String valor;

    public Cupomdesconto() {
    }

    public Cupomdesconto(String name, String valor) {
        this.name = name;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Cupomdesconto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}

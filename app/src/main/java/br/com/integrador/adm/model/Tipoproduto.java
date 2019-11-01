package br.com.integrador.adm.model;

import java.io.Serializable;

public class Tipoproduto implements Serializable {

    private Integer id;
    private String name;

    public Tipoproduto() {
    }

    public Tipoproduto(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return  name ;
    }
}

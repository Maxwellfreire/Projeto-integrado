package br.com.integrador.adm.model;

import java.io.Serializable;

public class Liga implements Serializable {

    private Integer id;
    private String name;

    public Liga() {
    }

    public Liga(String name) {
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
        return "Marca{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

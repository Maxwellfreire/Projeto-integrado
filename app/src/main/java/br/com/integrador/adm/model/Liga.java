package br.com.integrador.adm.model;

import java.io.Serializable;

public class Liga implements Serializable {

    private Integer ligaId;
    private String name;

    public Liga() {
    }

    public Liga(String name) {
        this.name = name;
    }

    public Integer getLigaId() {
        return ligaId;
    }

    public void setLigaId(Integer ligaId) {
        this.ligaId = ligaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ligaId + " - " + name;
    }
}

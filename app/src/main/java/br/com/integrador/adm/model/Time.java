package br.com.integrador.adm.model;

import java.io.Serializable;

public class Time implements Serializable {

    private Integer idTime;
    private String nameTime;
    private String regiao;
    private String estado;
    private String pais;
    private String tipoTime;
    private Integer ligaId;


    public Time() {
    }

    public Time(String nameTime, String regiao, String estado, String pais, String tipoTime, Integer ligaId) {
        this.nameTime = nameTime;
        this.regiao = regiao;
        this.estado = estado;
        this.pais = pais;
        this.tipoTime = tipoTime;
        this.ligaId = ligaId;
    }

    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime(Integer idTime) {
        this.idTime = idTime;
    }

    public String getNameTime() {
        return nameTime;
    }

    public void setNameTime(String nameTime) {
        this.nameTime = nameTime;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoTime() {
        return tipoTime;
    }

    public void setTipoTime(String tipoTime) {
        this.tipoTime = tipoTime;
    }

    public Integer getLigaId() {
        return ligaId;
    }

    public void setLigaId(Integer ligaId) {
        this.ligaId = ligaId;
    }

    @Override
    public String toString() {
        return  nameTime ;
    }
}

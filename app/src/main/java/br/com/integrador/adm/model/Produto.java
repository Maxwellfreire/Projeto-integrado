package br.com.integrador.adm.model;

import java.io.Serializable;

public class Produto implements Serializable {

    private Integer idProduto;
    private String imagem;
    private String nameProduto;
    private String desc;
    private Integer preco;
    private String tipoGola;
    private String tipoManga;
    private String tamanho;
    private Integer idTime;
    private Integer idTipoProduto;
    private Integer idMarca;

    public Produto() {
    }

    public Produto(String imagem, String nameProduto, String desc, Integer preco, String tipoGola, String tipoManga, String tamanho, Integer idTime, Integer idTipoProduto, Integer idMarca) {
        this.imagem = imagem;
        this.nameProduto = nameProduto;
        this.desc = desc;
        this.preco = preco;
        this.tipoGola = tipoGola;
        this.tipoManga = tipoManga;
        this.tamanho = tamanho;
        this.idTime = idTime;
        this.idTipoProduto = idTipoProduto;
        this.idMarca = idMarca;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNameProduto() {
        return nameProduto;
    }

    public void setNameProduto(String nameProduto) {
        this.nameProduto = nameProduto;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public String getTipoGola() {
        return tipoGola;
    }

    public void setTipoGola(String tipoGola) {
        this.tipoGola = tipoGola;
    }

    public String getTipoManga() {
        return tipoManga;
    }

    public void setTipoManga(String tipoManga) {
        this.tipoManga = tipoManga;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime(Integer idTime) {
        this.idTime = idTime;
    }

    public Integer getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", imagem='" + imagem + '\'' +
                ", nameProduto='" + nameProduto + '\'' +
                ", desc='" + desc + '\'' +
                ", preco=" + preco +
                ", tipoGola='" + tipoGola + '\'' +
                ", tipoManga='" + tipoManga + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", idTime=" + idTime +
                ", idTipoProduto=" + idTipoProduto +
                ", idMarca=" + idMarca +
                '}';
    }
}

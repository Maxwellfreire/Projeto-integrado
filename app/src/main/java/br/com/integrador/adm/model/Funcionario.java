package br.com.integrador.adm.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private Integer matricula;
    private String nameFuncionario;
    private String cpf;
    private String sexo;
    private String email;
    private String telefone;
    private String celular;
    private String senha;
    private String confirmarSenha;
    private String dataNascimento;
    private Integer cargoId;

    public Funcionario() {
    }

    public Funcionario(String nameFuncionario, String cpf, String sexo, String email, String telefone, String celular, String senha, String confirmarSenha, String dataNascimento, Integer cargoId) {
        this.nameFuncionario = nameFuncionario;
        this.cpf = cpf;
        this.sexo = sexo;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.dataNascimento = dataNascimento;
        this.cargoId = cargoId;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNameFuncionario() {
        return nameFuncionario;
    }

    public void setNameFuncionario(String nameFuncionario) {
        this.nameFuncionario = nameFuncionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "matricula=" + matricula +
                ", nameFuncionario='" + nameFuncionario + '\'' +
                ", cpf='" + cpf + '\'' +
                ", sexo='" + sexo + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", senha='" + senha + '\'' +
                ", confirmarSenha='" + confirmarSenha + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", cargoId=" + cargoId +
                '}';
    }
}

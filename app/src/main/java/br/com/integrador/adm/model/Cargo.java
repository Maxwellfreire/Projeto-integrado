package br.com.integrador.adm.model;

import java.io.Serializable;

public class Cargo implements Serializable {

    private Integer id;
    private String name;
    private String salary;

    public Cargo() {
    }

    public Cargo(String name, String salary) {
        this.name = name;
        this.salary = salary;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return  name ;
    }
}

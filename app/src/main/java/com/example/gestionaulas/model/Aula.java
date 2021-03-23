package com.example.gestionaulas.model;

public class Aula {
    private String codigo,nombre,id;


    public Aula() {
    }

    public Aula(String id,String codigo, String nombre) {
        this.id=id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

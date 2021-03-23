package com.example.gestionaulas.model;

import java.util.Date;

public class Horario {
    private String codigoAula,id;
    private int periodo;
    private int dia;
    private String prof,curso,asig;

    public Horario() {
    }

    public Horario(String codigoAula, String id, int periodo, int dia, String prof, String curso, String asig) {
        this.codigoAula = codigoAula;
        this.id = id;
        this.periodo = periodo;
        this.dia = dia;
        this.prof = prof;
        this.curso = curso;
        this.asig = asig;
    }

    public String getCodigoAula() {
        return codigoAula;
    }

    public void setCodigoAula(String codigoAula) {
        this.codigoAula = codigoAula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getAsig() {
        return asig;
    }

    public void setAsig(String asig) {
        this.asig = asig;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "codigoAula='" + codigoAula + '\'' +
                ", id='" + id + '\'' +
                ", periodo=" + periodo +
                ", dia=" + dia +
                ", prof='" + prof + '\'' +
                ", curso='" + curso + '\'' +
                ", asig='" + asig + '\'' +
                '}';
    }
}

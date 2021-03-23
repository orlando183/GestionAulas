package com.example.gestionaulas.model;



public class Reserva {
    private String codigoAula,id;
    private int periodo;
    private int dia;
    private String prof,curso,asig;
    private String fechafin;

    public Reserva() {
    }

    public Reserva(String codigoAula, String id, int periodo, int dia, String prof, String curso, String asig, String fechafin) {
        this.codigoAula = codigoAula;
        this.id = id;
        this.periodo = periodo;
        this.dia = dia;
        this.prof = prof;
        this.curso = curso;
        this.asig = asig;
        this.fechafin = fechafin;
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

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "codigoAula='" + codigoAula + '\'' +
                ", id='" + id + '\'' +
                ", periodo=" + periodo +
                ", dia=" + dia +
                ", prof='" + prof + '\'' +
                ", curso='" + curso + '\'' +
                ", asig='" + asig + '\'' +
                ", fechafin=" + fechafin +
                '}';
    }
}

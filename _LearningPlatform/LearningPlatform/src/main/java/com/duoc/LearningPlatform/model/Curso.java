package com.duoc.LearningPlatform.model;

public class Curso {

    private int id;
    private String nombre;
    private String docente;
    private boolean activo;

    public Curso() {
    }

    public Curso(int id, String nombre, String docente, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.docente = docente;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
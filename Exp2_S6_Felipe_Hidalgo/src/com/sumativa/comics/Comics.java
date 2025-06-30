/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.comics;

/**
 *
 * @author pipe-
 */
public class Comics {
    private String id;
    private String titulo;
    private String autor;
    private String estado;

    public Comics(String id, String titulo, String autor, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }

    
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getEstado() { return estado; }

    
    public void setId(String id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    } 
}

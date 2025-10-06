/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class Pelicula {
    private Integer id;
    private String  titulo;
    private String  director;
    private int     anio;
    private int     duracion;
    private String  genero;

    public Pelicula() {}

    public Pelicula(Integer id, String titulo, String director, int anio, int duracion, String genero) {
        this.id = id; this.titulo = titulo; this.director = director;
        this.anio = anio; this.duracion = duracion; this.genero = genero;
    }

    // getters & setters
    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public int getAnio() { return anio; }
    public int getDuracion() { return duracion; }
    public String getGenero() { return genero; }
    public void setId(Integer id) { this.id = id; }
    public void setTitulo(String v) { this.titulo = v; }
    public void setDirector(String v) { this.director = v; }
    public void setAnio(int v) { this.anio = v; }
    public void setDuracion(int v) { this.duracion = v; }
    public void setGenero(String v) { this.genero = v; }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evaluacion.veahiculo;

/**
 *
 * @author pipe-
 */
public abstract class Vehiculo {
     private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private int diasArriendo;
    private double precioDia;

    public Vehiculo() {}

    public Vehiculo(String patente, String marca, String modelo, int anio, int diasArriendo, double precioDia) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.diasArriendo = diasArriendo;
        this.precioDia = precioDia;
    }

    // Getters y setters
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getDiasArriendo() { return diasArriendo; }
    public void setDiasArriendo(int diasArriendo) { this.diasArriendo = diasArriendo; }
    public double getPrecioDia() { return precioDia; }
    public void setPrecioDia(double precioDia) { this.precioDia = precioDia; }

    public abstract void mostrarDatos();
}
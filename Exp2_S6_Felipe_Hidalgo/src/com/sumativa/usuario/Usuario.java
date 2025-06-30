/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.usuario;

import com.sumativa.comics.Comics;
import java.util.ArrayList;

/**
 *
 * @author pipe-
 */
public class Usuario {
    private String nombre;
    private String rut;
    private ArrayList<Comics> historial; // Aquí se guardan los cómics comprados o reservados

    public Usuario(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
        this.historial = new ArrayList<>();
    }

    public Usuario() {
    }
    

    public String getNombre() { return nombre; }
    public String getRut() { return rut; }

    // Para agregar un cómic comprado o reservado al historial
    public void agregarComic(Comics comics) {
        historial.add(comics);
    }

    // Obtener el historial completo
    public ArrayList<Comics> getHistorial() {
        return historial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setHistorial(ArrayList<Comics> historial) {
        this.historial = historial;
    }
    

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", historial=" + historial +
                '}';
    }
}

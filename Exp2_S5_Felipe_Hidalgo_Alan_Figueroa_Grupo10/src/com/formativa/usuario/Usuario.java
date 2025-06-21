/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.formativa.usuario;

/**
 *
 * @author pipe-
 */
public class Usuario {
     private String nombre;
    private String rut;

    public Usuario() {
    }

    public Usuario(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", rut=" + rut + '}';
    }
    
    public String getNombre() { return nombre; }
    public String getRut() { return rut; }
    
}

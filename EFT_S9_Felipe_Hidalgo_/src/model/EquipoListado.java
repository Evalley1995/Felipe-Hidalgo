/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class EquipoListado {
    public int id;
    public String tipo;  
    public String modelo;
    public String cpu;
    public int discoMb;
    public int ramGb;
    public int precio;

    
    public Integer fuenteW;
    public String  factor;
    public Double  pantallaIn;
    public Boolean esTouch;
    public Integer usbCount;

    @Override public String toString() {
        return "EquipoListado{id=" + id + ", tipo=" + tipo + ", modelo='" + modelo + "'}";
    }
}

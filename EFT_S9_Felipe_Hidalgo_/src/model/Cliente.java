/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class Cliente {
   private String rut;
    private String nombre;
    private String direccion;
    private String comuna;
    private String correo;
    private String telefono;

    public Cliente() {}

    public Cliente(String rut, String nombre, String direccion, String comuna, String correo, String telefono) {
        this.rut = rut; this.nombre = nombre; this.direccion = direccion;
        this.comuna = comuna; this.correo = correo; this.telefono = telefono;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override public String toString() {
        return "Cliente{rut='" + rut + "', nombre='" + nombre + "'}";
    }
   
}

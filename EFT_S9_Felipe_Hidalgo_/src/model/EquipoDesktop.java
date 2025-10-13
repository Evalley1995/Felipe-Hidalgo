/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class EquipoDesktop {
   private Integer id; 
    private String modelo;
    private String cpu;
    private int discoMb;
    private int ramGb;
    private int precio;
    private int fuenteW;
    private String factor;

    public EquipoDesktop() {}

    public EquipoDesktop(Integer id, String modelo, String cpu, int discoMb, int ramGb, int precio, int fuenteW, String factor) {
        this.id = id; this.modelo = modelo; this.cpu = cpu; this.discoMb = discoMb;
        this.ramGb = ramGb; this.precio = precio; this.fuenteW = fuenteW; this.factor = factor;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }
    public int getDiscoMb() { return discoMb; }
    public void setDiscoMb(int discoMb) { this.discoMb = discoMb; }
    public int getRamGb() { return ramGb; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }
    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
    public int getFuenteW() { return fuenteW; }
    public void setFuenteW(int fuenteW) { this.fuenteW = fuenteW; }
    public String getFactor() { return factor; }
    public void setFactor(String factor) { this.factor = factor; }
}

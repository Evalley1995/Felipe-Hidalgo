/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class EquipoLaptop {
     private Integer id; 
    private String modelo;
    private String cpu;
    private int discoMb;
    private int ramGb;
    private int precio;
    private double pantallaIn;
    private boolean esTouch;
    private int usbCount;

    public EquipoLaptop() {}

    public EquipoLaptop(Integer id, String modelo, String cpu, int discoMb, int ramGb, int precio,
                        double pantallaIn, boolean esTouch, int usbCount) {
        this.id = id; this.modelo = modelo; this.cpu = cpu; this.discoMb = discoMb; this.ramGb = ramGb;
        this.precio = precio; this.pantallaIn = pantallaIn; this.esTouch = esTouch; this.usbCount = usbCount;
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
    public double getPantallaIn() { return pantallaIn; }
    public void setPantallaIn(double pantallaIn) { this.pantallaIn = pantallaIn; }
    public boolean isEsTouch() { return esTouch; }
    public void setEsTouch(boolean esTouch) { this.esTouch = esTouch; }
    public int getUsbCount() { return usbCount; }
    public void setUsbCount(int usbCount) { this.usbCount = usbCount; }
}

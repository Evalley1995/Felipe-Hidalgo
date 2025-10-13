/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Timestamp;

/**
 *
 * @author pipe-
 */
public class Venta {
    private Integer id;        // null si nueva
    private Timestamp fecha;   // lo llena la BD
    private String clienteRut;
    private int equipoId;
    private int precioFinal;

    public Venta() {}

    public Venta(Integer id, Timestamp fecha, String clienteRut, int equipoId, int precioFinal) {
        this.id = id; this.fecha = fecha; this.clienteRut = clienteRut; this.equipoId = equipoId; this.precioFinal = precioFinal;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getClienteRut() { return clienteRut; }
    public void setClienteRut(String clienteRut) { this.clienteRut = clienteRut; }
    public int getEquipoId() { return equipoId; }
    public void setEquipoId(int equipoId) { this.equipoId = equipoId; }
    public int getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(int precioFinal) { this.precioFinal = precioFinal; }
}

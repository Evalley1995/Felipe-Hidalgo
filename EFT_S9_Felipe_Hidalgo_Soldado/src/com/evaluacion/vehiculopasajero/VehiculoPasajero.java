/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evaluacion.vehiculopasajero;

import com.evaluacion.veahiculo.Vehiculo;
import com.evaluacion.vehiculocarga.ICalculoBoleta;

/**
 *
 * @author pipe-
 */
public class VehiculoPasajero extends Vehiculo implements ICalculoBoleta {
    private int cantidadPasajeros;

    public VehiculoPasajero(String patente, String marca, String modelo, int anio, int diasArriendo, double precioDia, int cantidadPasajeros) {
        super(patente, marca, modelo, anio, diasArriendo, precioDia);
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public static double getIVA() {
        return IVA;
    }

    public static double getDESCUENTO_CARGA() {
        return DESCUENTO_CARGA;
    }

    public static double getDESCUENTO_PASAJEROS() {
        return DESCUENTO_PASAJEROS;
    }
    

    public int getCantidadPasajeros() { return cantidadPasajeros; }
    public void setCantidadPasajeros(int cantidadPasajeros) { this.cantidadPasajeros = cantidadPasajeros; }

    @Override
    public void mostrarDatos() {
        System.out.println("Patente: " + getPatente());
        System.out.println("Marca: " + getMarca());
        System.out.println("Modelo: " + getModelo());
        System.out.println("Año: " + getAnio());
        System.out.println("Días de arriendo: " + getDiasArriendo());
        System.out.println("Precio por día: " + getPrecioDia());
        System.out.println("Cantidad de pasajeros: " + cantidadPasajeros);
    }

    @Override
    public void calcularYMostrarBoleta() {
        double subtotal = getPrecioDia() * getDiasArriendo();
        double descuento = subtotal * DESCUENTO_PASAJEROS;
        double neto = subtotal - descuento;
        double iva = neto * IVA;
        double total = neto + iva;
        System.out.println("===== Boleta Vehículo de Pasajeros =====");
        System.out.println("Patente: " + getPatente());
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Descuento (12%): -" + descuento);
        System.out.println("IVA (19%): +" + iva);
        System.out.println("TOTAL A PAGAR: " + total);
    }
}
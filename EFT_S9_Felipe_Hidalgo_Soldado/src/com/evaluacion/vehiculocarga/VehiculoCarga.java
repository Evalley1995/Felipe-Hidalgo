/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evaluacion.vehiculocarga;

import static com.evaluacion.vehiculocarga.ICalculoBoleta.DESCUENTO_CARGA;
import static com.evaluacion.vehiculocarga.ICalculoBoleta.IVA;
import com.evaluacion.veahiculo.Vehiculo;

/**
 *
 * @author pipe-
 */
public class VehiculoCarga extends Vehiculo implements ICalculoBoleta {
     private double capacidadToneladas;

    public VehiculoCarga(String patente, String marca, String modelo, int anio, int diasArriendo, double precioDia, double capacidadToneladas) {
        super(patente, marca, modelo, anio, diasArriendo, precioDia);
        this.capacidadToneladas = capacidadToneladas;
        
        
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

    public double getCapacidadToneladas() { return capacidadToneladas; }
    public void setCapacidadToneladas(double capacidadToneladas) { this.capacidadToneladas = capacidadToneladas; }

    @Override
    public void mostrarDatos() {
        System.out.println("Patente: " + getPatente());
        System.out.println("Marca: " + getMarca());
        System.out.println("Modelo: " + getModelo());
        System.out.println("Año: " + getAnio());
        System.out.println("Días de arriendo: " + getDiasArriendo());
        System.out.println("Precio por día: " + getPrecioDia());
        System.out.println("Capacidad de carga: " + capacidadToneladas + " toneladas");
    }

     @Override
    public void calcularYMostrarBoleta() {
        double subtotal = getPrecioDia() * getDiasArriendo();
        double descuento = subtotal * DESCUENTO_CARGA;
        double neto = subtotal - descuento;
        double iva = neto * IVA;
        double total = neto + iva;
        System.out.println("===== Boleta Vehículo de Carga =====");
        System.out.println("Patente: " + getPatente());
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Descuento (7%): -" + descuento);
        System.out.println("IVA (19%): +" + iva);
        System.out.println("TOTAL A PAGAR: " + total);
    }
}

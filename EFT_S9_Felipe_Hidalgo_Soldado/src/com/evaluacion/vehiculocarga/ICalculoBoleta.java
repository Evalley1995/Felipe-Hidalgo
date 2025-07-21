package com.evaluacion.vehiculocarga;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author pipe-
 */
public interface ICalculoBoleta {
    double IVA = 0.19;
    double DESCUENTO_CARGA = 0.07;
    double DESCUENTO_PASAJEROS = 0.12;
    void calcularYMostrarBoleta();
}

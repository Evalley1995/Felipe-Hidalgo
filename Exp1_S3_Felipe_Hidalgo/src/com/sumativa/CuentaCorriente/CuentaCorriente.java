/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.CuentaCorriente;

import com.sumativa.Cliente.Cliente;

/**
 *
 * @author pipe-
 */
public class CuentaCorriente extends Cliente {
       private String numeroCuenta;
       private int girarCuentaCorriente;
    private int depositarCuentaCorriente;
    private int saldo = 0;

    public CuentaCorriente(String numeroCuenta, int girarCuentaCorriente, int depositarCuentaCorriente, String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.numeroCuenta = numeroCuenta;
        this.girarCuentaCorriente = girarCuentaCorriente;
        this.depositarCuentaCorriente = depositarCuentaCorriente;
    }
    
    public void depositar(int monto) {
    if (monto > 0) {
        saldo += monto;
        System.out.println("️ Depósito exitoso. Nuevo saldo: $" + saldo);
    } else {
        System.out.println(" Monto inválido.");
    }
}

    public void girar(int monto) {
    if (monto <= 0) {
        System.out.println(" Monto inválido.");
    } else if (monto > saldo) {
        System.out.println(" Saldo insuficiente.");
    } else {
        saldo -= monto;
        System.out.println("️ Giro exitoso. Nuevo saldo: $" + saldo);
    }
}

    public int getSaldo() {
    return saldo;
    }

    public CuentaCorriente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.girarCuentaCorriente = 0;
        this.depositarCuentaCorriente = 0;
    }

   
}

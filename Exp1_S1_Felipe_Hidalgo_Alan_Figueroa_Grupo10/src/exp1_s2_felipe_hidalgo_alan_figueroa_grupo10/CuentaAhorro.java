/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp1_s1_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
public class CuentaAhorro extends Cliente {

    private int depositar;
    private int girar;
    private int saldo = 0;

    public CuentaAhorro(int depositar, int girar, String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.depositar = depositar;
        this.girar = girar;
    }
    public void depositar(int monto) {
    if (monto > 0) {
        saldo += monto;
        System.out.println(" Depósito exitoso. Nuevo saldo: $" + saldo);
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

    public CuentaAhorro(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.girar = 0;
        this.depositar = 0;
    } 
}
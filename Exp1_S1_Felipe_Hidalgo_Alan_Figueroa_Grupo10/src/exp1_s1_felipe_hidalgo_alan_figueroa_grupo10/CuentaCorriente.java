/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp1_s1_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
import java.util.Scanner;

// Clase CuentaCorriente
class CuentaCorriente {
    private String numeroCuenta;
    private int saldo;

    public CuentaCorriente(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0; // Inicia en 0
    }

    public boolean depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito realizado con éxito.");
            return true;
        } else {
            System.out.println("Monto inválido. Debe ser mayor a 0.");
            return false;
        }
    }

    public boolean girar(int monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido. Debe ser mayor a 0.");
            return false;
        } else if (monto > saldo) {
            System.out.println("Fondos insuficientes.");
            return false;
        } else {
            saldo -= monto;
            System.out.println("Giro realizado con éxito.");
            return true;
        }
    }

    public int getSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}

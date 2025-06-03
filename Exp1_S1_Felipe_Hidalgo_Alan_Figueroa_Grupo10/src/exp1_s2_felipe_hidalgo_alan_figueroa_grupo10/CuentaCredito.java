/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp1_s2_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
public class CuentaCredito extends Cliente {
     private int deuda;
    private final int limiteAvance = 200000;

    public CuentaCredito(int deuda, String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.deuda = deuda;
    }

    public CuentaCredito(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        super(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        this.deuda = 0;
    }  
    
    public boolean hacerAvance(int monto) {
        if (monto <= 0) {
            System.out.println(" Monto inválido. Debe ser mayor a 0.");
            return false;
        } else if (deuda + monto > limiteAvance) {
            System.out.println(" Límite de crédito excedido. Tu deuda actual es $" + deuda + " y no puedes superar $200.000.");
            return false;
        } else {
            deuda += monto;
            System.out.println("✅ Avance realizado. Deuda actual: $" + deuda);
            return true;
        }
    }
     
    public boolean pagarDeuda(int monto) {
        if (deuda == 0) {
            System.out.println(" No tienes deudas que pagar.");
            return false;
        } else if (monto <= 0) {
            System.out.println(" Monto inválido. Debe ser mayor a 0.");
            return false;
        } else if (monto >= deuda) {
            System.out.println(" Pagaste toda la deuda de $" + deuda + ". Deuda ahora es $0.");
            deuda = 0;
        } else {
            deuda -= monto;
            System.out.println(" Pago parcial realizado. Deuda restante: $" + deuda);
        }
        return true;
    }
     
      public int getDeuda() {
        return deuda;
    }
}
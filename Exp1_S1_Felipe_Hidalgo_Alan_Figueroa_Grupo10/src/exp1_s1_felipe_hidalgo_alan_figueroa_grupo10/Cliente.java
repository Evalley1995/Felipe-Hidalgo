/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp1_s1_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
// Clase Cliente
class Cliente {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private CuentaCorriente cuenta;

    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String domicilio, String comuna, String telefono, String numeroCuenta) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = new CuentaCorriente(numeroCuenta);
    }

    public void mostrarDatos() {
        System.out.println("RUT: " + rut);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido Paterno: " + apellidoPaterno);
        System.out.println("Apellido Materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Cuenta Corriente: " + cuenta.getNumeroCuenta());
    }

    public CuentaCorriente getCuenta() {
        return cuenta;
    }

    public String getRut() {
        return rut;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp1_s1_felipe_hidalgo_alan_figueroa_grupo10;

import java.util.Scanner;

/**
 *
 * @author pipe-
 */
// Clase principal
public class Main {
    private static Cliente cliente = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void mostrarMenu() {
        int opcion;

        do {
            System.out.println("=== Menú Bank Boston ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos de cliente");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            opcion = -1; // opción inválida
            }

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    verDatosCliente();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarGiro();
                    break;
                case 5:
                    consultarSaldo();
                    break;
                case 6:
                    System.out.println("Gracias por usar Bank Boston. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println(); // Separador visual

        } while (opcion != 6);
    }

    public static void main(String[] args) {
        mostrarMenu();
    }

    public static void registrarCliente() {
        String rut;
        while (true) {
            System.out.print("Ingrese RUT (formato XX.XXX.XXX-X, solo números): ");
            rut = scanner.nextLine();

            if (rut.contains(".") && rut.contains("-")) {
                String[] partes = rut.split("-");
                if (partes.length == 2) {
                    String cuerpo = partes[0];
                    String verificador = partes[1];

                    if (cuerpo.matches("[0-9.]+") && verificador.matches("[0-9]")) {
                        break;
                    }
                }
            }

            System.out.println("❌ RUT inválido. Debe tener formato XX.XXX.XXX-X y solo números.");
        }

        String nombre;
        while (true) {
            System.out.print("Ingrese nombre: ");
            nombre = scanner.nextLine();

            boolean contieneNumero = false;
            for (int i = 0; i < nombre.length(); i++) {
                char c = nombre.charAt(i);
                if (c >= '0' && c <= '9') {
                    contieneNumero = true;
                    break;
                }
            }
            if (!contieneNumero) break;
            else System.out.println("❌ El nombre no debe contener números.");
        }

        String apellidoPaterno;
        while (true) {
            System.out.print("Ingrese apellido paterno: ");
            apellidoPaterno = scanner.nextLine();

            boolean contieneNumero = false;
            for (int i = 0; i < apellidoPaterno.length(); i++) {
                char c = apellidoPaterno.charAt(i);
                if (c >= '0' && c <= '9') {
                    contieneNumero = true;
                    break;
                }
            }
            if (!contieneNumero) break;
            else System.out.println("❌ El apellido no debe contener números.");
        }

        String apellidoMaterno;
        while (true) {
            System.out.print("Ingrese apellido materno: ");
            apellidoMaterno = scanner.nextLine();

            boolean contieneNumero = false;
            for (int i = 0; i < apellidoMaterno.length(); i++) {
                char c = apellidoMaterno.charAt(i);
                if (c >= '0' && c <= '9') {
                    contieneNumero = true;
                    break;
                }
            }
            if (!contieneNumero) break;
            else System.out.println("❌ El apellido no debe contener números.");
        }

        String domicilio;
        while (true) {
            System.out.print("Ingrese domicilio: ");
            domicilio = scanner.nextLine();

            boolean contieneNumero = false;
            for (int i = 0; i < domicilio.length(); i++) {
                char c = domicilio.charAt(i);
                if (c >= '0' && c <= '9') {
                    contieneNumero = true;
                    break;
                }
            }
            if (!contieneNumero) break;
            else System.out.println("❌ El domicilio no debe contener números.");
        }

        String comuna;
        while (true) {
            System.out.print("Ingrese comuna: ");
            comuna = scanner.nextLine();

            boolean contieneNumero = false;
            for (int i = 0; i < comuna.length(); i++) {
                char c = comuna.charAt(i);
                if (c >= '0' && c <= '9') {
                    contieneNumero = true;
                    break;
                }
            }
            if (!contieneNumero) break;
            else System.out.println("❌ La comuna no debe contener números.");
        }

        String telefono;
        while (true) {
            System.out.print("Ingrese teléfono (9 dígitos): ");
            telefono = scanner.nextLine();
            if (telefono.length() == 9) {
                boolean soloNumeros = true;
                for (int i = 0; i < telefono.length(); i++) {
                    if (telefono.charAt(i) < '0' || telefono.charAt(i) > '9') {
                        soloNumeros = false;
                        break;
                    }
                }
                if (soloNumeros) break;
            }
            System.out.println("Número inválido. Debe tener 9 dígitos numéricos.");
        }

        System.out.print("Ingrese número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, numeroCuenta);
        System.out.println("Cliente registrado con éxito.");
    }

    public static void verDatosCliente() {
        if (cliente == null) {
            System.out.println("No hay cliente registrado.");
        } else {
            cliente.mostrarDatos();
        }
    }

    public static void realizarDeposito() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente primero.");
        } else {
            System.out.print("Ingrese monto a depositar: ");
            int monto = scanner.nextInt();
            scanner.nextLine();
            cliente.getCuenta().depositar(monto);
        }
    }

    public static void realizarGiro() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente primero.");
        } else {
            System.out.print("Ingrese monto a girar: ");
            int monto = scanner.nextInt();
            scanner.nextLine();
            cliente.getCuenta().girar(monto);
        }
    }

    public static void consultarSaldo() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente primero.");
        } else {
            int saldo = cliente.getCuenta().getSaldo();
            System.out.println("Saldo actual: $" + saldo);
        }
    }
}
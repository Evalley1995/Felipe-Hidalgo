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

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> verDatosCliente();
                case 3 -> realizarDeposito();
                case 4 -> realizarGiro();
                case 5 -> consultarSaldo();
                case 6 -> System.out.println("Gracias por usar Bank Boston. ¡Hasta pronto!");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println(); // Separación entre acciones
        } while (opcion != 6);
    }

    private static void mostrarMenu() {
        System.out.println("=== Menú Bank Boston ===");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ver datos de cliente");
        System.out.println("3. Depositar");
        System.out.println("4. Girar");
        System.out.println("5. Consultar saldo");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarCliente() {
        if (cliente != null) {
            System.out.println("Ya hay un cliente registrado. Reinicie la aplicación para registrar otro.");
            return;
        }

        System.out.print("Ingrese RUT con guión y puntos : ");
        String rut = scanner.nextLine();
        if (rut.length() < 11 || rut.length() > 12) {
            System.out.println("RUT inválido. Debe tener entre 9 caracteres.");
            return;
        }

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Apellido Materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine();
        System.out.print("Comuna: ");
        String comuna = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Número de cuenta (9 dígitos): ");
        String numeroCuenta = scanner.nextLine();

        if (numeroCuenta.length() != 9 || !numeroCuenta.matches("\\d+")) {
            System.out.println("Número de cuenta inválido.");
            return;
        }

        cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno,
                domicilio, comuna, telefono, numeroCuenta);
        System.out.println("Cliente registrado exitosamente.");
    }

    private static void verDatosCliente() {
        if (cliente == null) {
            System.out.println("No hay cliente registrado.");
            return;
        }

        cliente.mostrarDatos();
    }

    private static void realizarDeposito() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente antes de hacer depósitos.");
            return;
        }

        System.out.print("Ingrese monto a depositar: ");
        int monto = scanner.nextInt();
        cliente.getCuenta().depositar(monto);
    }

    private static void realizarGiro() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente antes de hacer giros.");
            return;
        }

        System.out.print("Ingrese monto a girar: ");
        int monto = scanner.nextInt();
        cliente.getCuenta().girar(monto);
    }

    private static void consultarSaldo() {
        if (cliente == null) {
            System.out.println("Debe registrar un cliente antes de consultar saldo.");
            return;
        }

        System.out.println("Saldo actual: $" + cliente.getCuenta().getSaldo());
    }
}
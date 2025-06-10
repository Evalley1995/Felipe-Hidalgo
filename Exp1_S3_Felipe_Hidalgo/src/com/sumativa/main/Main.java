/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.main;

import com.sumativa.Cliente.Cliente;
import com.sumativa.CuentaCorriente.CuentaCorriente;
import com.sumativa.cuentaAhorro.CuentaAhorro;
import com.sumativa.cuentaCredito.CuentaCredito;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class Main {
     private static Scanner scanner = new Scanner(System.in);
    private static Cliente cliente;
    private static CuentaCorriente cuentaCorriente;
    private static CuentaAhorro cuentaAhorro;
    private static CuentaCredito cuentaCredito;

    public static void main(String[] args) {
        mostrarMenu();
    }

    public static void mostrarMenu() {
        int opcion;
        do {
            System.out.println("=== Menú Bank Boston ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos de cliente");
            System.out.println("3. Cuenta Corriente (Depositar / Girar)");
            System.out.println("4. Cuenta Ahorro (Depositar / Girar)");
            System.out.println("5. Cuenta Crédito (Avance / Pagar Deuda)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    verDatosCliente();
                    break;
                case 3:
                    operarCuentaCorriente();
                    break;
                case 4:
                    operarCuentaAhorro();
                    break;
                case 5:
                    operarCuentaCredito();
                    break;
                case 6:
                    System.out.println("Gracias por ser parte de Bank Boston. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 6);
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        Main.cliente = cliente;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Main.scanner = scanner;
    }

    public static void registrarCliente() {
        String rut;
        while (true) {
            System.out.print("Ingrese RUT (formato XX.XXX.XXX-X, si termina en K reemplazalo por 0): ");
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

            System.out.println(" RUT inválido. ingrese rut con punto y guión, reemplace la k por un 0.");
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
            else System.out.println(" El nombre no debe contener números.");
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
            else System.out.println(" El apellido no debe contener números.");
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
            else System.out.println(" El apellido no debe contener números.");
        }

        String domicilio;
        while (true) {
            System.out.print("Ingrese domicilio: ");
            domicilio = scanner.nextLine();

            boolean valido = true;
            boolean empiezaConLetra = false;
            boolean terminaEnNumero = false;

            if (domicilio.length() > 0) {
                char primerCaracter = domicilio.charAt(0);
                char ultimoCaracter = domicilio.charAt(domicilio.length() - 1);

                empiezaConLetra = ((primerCaracter >= 'A' && primerCaracter <= 'Z') ||
                        (primerCaracter >= 'a' && primerCaracter <= 'z'));

                terminaEnNumero = (ultimoCaracter >= '0' && ultimoCaracter <= '9');
            }

            for (int i = 0; i < domicilio.length(); i++) {
                char c = domicilio.charAt(i);
                if (!((c >= 'A' && c <= 'Z') ||
                        (c >= 'a' && c <= 'z') ||
                        (c >= '0' && c <= '9') ||
                        c == ' ')) {
                    valido = false;
                    break;
                }
            }

            if (valido && empiezaConLetra && terminaEnNumero) break;
            else System.out.println(" El domicilio debe comenzar con letras, terminar en número y no contener símbolos.");
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
            else System.out.println(" La comuna no debe contener números.");
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

        cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        cuentaCorriente = new CuentaCorriente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        cuentaAhorro = new CuentaAhorro(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
        cuentaCredito = new CuentaCredito(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);

        System.out.println("Cliente registrado con éxito.");
    }

    public static void verDatosCliente() {
        if (cliente != null) {
            cliente.mostrarDatos();
        } else {
            System.out.println("Primero debe registrar un cliente.");
        }
    }

     public static void operarCuentaCorriente() {
        if (cliente == null) {
            System.out.println("Primero debe registrar un cliente.");
            return;
        }

        System.out.println("1. Depositar\n2. Girar");
        int op;
        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        System.out.print("Ingrese monto: ");
        int monto;
        try {
            monto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        if (op == 1) {
            cuentaCorriente.depositar(monto);
        } else if (op == 2) {
            cuentaCorriente.girar(monto);
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public static void operarCuentaAhorro() {
        if (cliente == null) {
            System.out.println("Primero debe registrar un cliente.");
            return;
        }

        System.out.println("1. Depositar\n2. Girar");
        int op;
        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        System.out.print("Ingrese monto: ");
        int monto;
        try {
            monto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        if (op == 1) {
            cuentaAhorro.depositar(monto);
        } else if (op == 2) {
            cuentaAhorro.girar(monto);
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public static void operarCuentaCredito() {
        if (cliente == null) {
            System.out.println("Primero debe registrar un cliente.");
            return;
        }

        System.out.println("Tu deuda actual es: $" + cuentaCredito.getDeuda());
        System.out.println("1. Hacer Avance");
        System.out.println("2. Pagar Deuda");
        System.out.print("Seleccione una opción: ");

        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Opción inválida. Debes ingresar un número.");
            return;
        }

        System.out.print("Ingrese el monto: ");
        int monto;
        try {
            monto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido. Debes ingresar solo números.");
            return;
        }

        if (opcion == 1) {
            cuentaCredito.hacerAvance(monto);
        } else if (opcion == 2) {
            cuentaCredito.pagarDeuda(monto);
        } else {
            System.out.println(" Opción inválida.");
        }

        System.out.println(" Deuda actual: $" + cuentaCredito.getDeuda());
    }
}

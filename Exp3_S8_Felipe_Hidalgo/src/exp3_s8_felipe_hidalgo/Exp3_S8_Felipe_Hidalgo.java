/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_felipe_hidalgo;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class Exp3_S8_Felipe_Hidalgo {
 static Scanner scanner = new Scanner(System.in);
    static final int Max_Entradas = 100;
    static String[] nombresClientes = new String[Max_Entradas];
    static String[] ubicaciones = new String[Max_Entradas];
    static boolean[] esReserva = new boolean[Max_Entradas];
    static String[] tipoCliente = new String[Max_Entradas];
    static int cantidadEntradas = 0;

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n--- Bienvenido a Solticket ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar entrada");
            System.out.println("3. Buscar entrada por nombre");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Ver todas las entradas");
            System.out.println("6. Convertir entrada reservada en compra");
            System.out.println("7. Imprimir boleta");
            System.out.println("8. Salir");

            while (true) {
                System.out.print("Seleccione una opción: ");
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine();
                    if (opcion >= 1 && opcion <= 8)
                        break;
                    else System.out.println("Por favor, elija una opción válida.");
                } else {
                    System.out.println("Entrada no válida.");
                    scanner.nextLine();
                }
            }

            switch (opcion) {
                case 1:
                    venderEntrada(scanner);
                    break;
                case 2:
                    reservarEntrada(scanner);
                    break;
                case 3:
                    buscarEntrada(scanner);
                    break;
                case 4:
                    eliminarEntrada(scanner);
                    break;
                case 5:
                    mostrarEntradas();
                    break;
                case 6:
                    convertirReserva(scanner);
                    break;
                case 7:
                    generarBoletas();
                    break;
                case 8:
                    System.out.println("Gracias por visitar Solticket. ¡Hasta pronto!");
                    break;
            }

        } while (opcion != 8);

        scanner.close();
    }

    static void venderEntrada(Scanner scanner) {
        if (cantidadEntradas >= Max_Entradas) {
            System.out.println("No hay entradas disponibles.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        int tipoUbicacion = 0;
        while (true) {
            System.out.println("Seleccione ubicación: 1. VIP ($10000), 2. Platea ($7000), 3. Balcón ($5000)");
            if (scanner.hasNextInt()) {
                tipoUbicacion = scanner.nextInt();
                scanner.nextLine();
                if (tipoUbicacion >= 1 && tipoUbicacion <= 3) break;
                else System.out.println("Opción inválida.");
            } else {
                System.out.println("Entrada inválida. Intente nuevamente.");
                scanner.nextLine();
            }
        }

        String ubicacion = ""; 
        switch (tipoUbicacion) {
            case 1:
                ubicacion = "VIP";
                break;
            case 2:
                ubicacion = "Platea";
                break;
            case 3:
                ubicacion = "Balcón";
                break;
            default:
                System.out.println("Ubicación inválida. Seleccione nuevamente");
                break;
        }

        String tipo = obtenerTipoCliente(scanner);

        nombresClientes[cantidadEntradas] = nombre;
        ubicaciones[cantidadEntradas] = ubicacion;
        esReserva[cantidadEntradas] = false;
        tipoCliente[cantidadEntradas] = tipo;
        
        // Debug
        System.out.println("[DEBUG] Entrada registrada en índice: " + cantidadEntradas);
        System.out.println("[DEBUG] Cliente: " + nombre);
        System.out.println("[DEBUG] Ubicación: " + ubicacion);
        System.out.println("[DEBUG] Tipo Cliente: " + tipo);

        System.out.println("Entrada vendida exitosamente.");
        imprimirBoleta(cantidadEntradas);

        cantidadEntradas++;
    }

    static void reservarEntrada(Scanner scanner) {
        if (cantidadEntradas >= Max_Entradas) {
            System.out.println("No hay más entradas disponibles para reservar.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        int tipoUbicacion = 0;
        while (true) {
            System.out.println("Seleccione ubicación para reservar: 1. VIP ($10000), 2. Platea ($7000), 3. Balcón ($5000)");
            if (scanner.hasNextInt()) {
                tipoUbicacion = scanner.nextInt();
                scanner.nextLine();
                if (tipoUbicacion >= 1 && tipoUbicacion <= 3) break;
                else System.out.println("Opción fuera de rango.");
            } else {
                System.out.println("Entrada inválida. Intente nuevamente.");
                scanner.nextLine();
            }
        }

        String ubicacion = ""; 
        switch (tipoUbicacion) {
            case 1:
                ubicacion = "VIP";
                break;
            case 2:
                ubicacion = "Platea";
                break;
            case 3:
                ubicacion = "Balcón";
                break;
            default:
                System.out.println("Ubicación inválida. Seleccione nuevamente");
                break;
        }

        nombresClientes[cantidadEntradas] = nombre;
        ubicaciones[cantidadEntradas] = ubicacion;
        esReserva[cantidadEntradas] = true;
        tipoCliente[cantidadEntradas] = "normal";
        
        System.out.println("[DEBUG] Entrada registrada en índice: " + cantidadEntradas); // Indica en donde se está reservando la entrada
        System.out.println("[DEBUG] Cliente: " + nombre); // Muestra el nombre de quien reserva
        System.out.println("[DEBUG] Ubicación: " + ubicacion); // Muestra la ubicación reservada

        cantidadEntradas++;
        System.out.println("Reserva realizada exitosamente.");
    }

    static void buscarEntrada(Scanner scanner) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < cantidadEntradas; i++) {
             System.out.println("[DEBUG] Buscando en índice: " + i); // Muestra cada índice que se revisa al buscar por nombre
            if (nombresClientes[i].equalsIgnoreCase(nombre)) {
                String tipoEntrada;
                if (esReserva[i]) {
                    tipoEntrada = "Reserva";
                } else {
                    tipoEntrada = "Compra";
                }

                System.out.println("Nombre: " + nombresClientes[i] + ", Ubicación: " + ubicaciones[i] +
                        ", Tipo: " + tipoEntrada + ", Cliente: " + tipoCliente[i]);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ninguna entrada con ese nombre.");
        }
    }

    static void eliminarEntrada(Scanner scanner) {
        System.out.print("Ingrese el nombre del cliente a eliminar: ");
        String nombre = scanner.nextLine();
        boolean eliminado = false;

        for (int i = 0; i < cantidadEntradas; i++) {
            if (nombresClientes[i].equalsIgnoreCase(nombre)) {
                //Debug
                System.out.println("[DEBUG] Eliminando entrada en índice: " + i); // Informa en qué índice se encontró y está eliminando la entrada
                for (int j = i; j < cantidadEntradas - 1; j++) {
                    nombresClientes[j] = nombresClientes[j + 1];
                    ubicaciones[j] = ubicaciones[j + 1];
                    esReserva[j] = esReserva[j + 1];
                    tipoCliente[j] = tipoCliente[j + 1];
                }
                cantidadEntradas--;
                eliminado = true;
                System.out.println("Entrada eliminada exitosamente.");
                break;
            }
        }

        if (!eliminado) {
            System.out.println("No se encontró ninguna entrada con ese nombre.");
        }
    }

    static void mostrarEntradas() {
        if (cantidadEntradas == 0) {
            System.out.println("No hay entradas registradas.");
            return;
        }

        for (int i = 0; i < cantidadEntradas; i++) {
            String tipoEntrada;
            if (esReserva[i]) {
                tipoEntrada = "Reserva";
            } else {
                tipoEntrada = "Compra";
            }

            System.out.println("Nombre: " + nombresClientes[i] +
                    ", Ubicación: " + ubicaciones[i] +
                    ", Tipo: " + tipoEntrada +
                    ", Cliente: " + tipoCliente[i]);
        }
    }

    static void convertirReserva(Scanner scanner) {
        System.out.print("Ingrese el nombre del cliente que desea convertir su reserva: ");
        String nombre = scanner.nextLine();
        boolean convertido = false;

        for (int i = 0; i < cantidadEntradas; i++) {
            if (nombresClientes[i].equalsIgnoreCase(nombre) && esReserva[i]) {
                // Debug
                 System.out.println("[DEBUG] Convirtiendo reserva en índice: " + i); // Muestra en qué índice se está convirtiendo la reserva a compra
                esReserva[i] = false;
                tipoCliente[i] = obtenerTipoCliente(scanner);
                System.out.println("Reserva convertida en compra con éxito.");
                imprimirBoleta(i);
                convertido = true;
                break;
            }
        }

        if (!convertido) {
            System.out.println("No se encontró una reserva con ese nombre.");
        }
    }

    static void generarBoletas() {
        boolean hayCompras = false;

        for (int i = 0; i < cantidadEntradas; i++) {
            if (!esReserva[i]) {
                imprimirBoleta(i);
                hayCompras = true;
            }
        }

        if (!hayCompras) {
            System.out.println("No hay compras registradas para generar boletas.");
        }
    }

   static void imprimirBoleta(int i) {
       // Debug
       System.out.println("[DEBUG] Imprimiendo boleta del índice: " + i); // Indica qué índice se está usando para imprimir la boleta
        System.out.println("[DEBUG] Ubicación: " + ubicaciones[i] + ", Tipo cliente: " + tipoCliente[i]); //  Muestra ubicación y tipo de cliente al momento de calcular el total
        
    int precioBase = 0;
    switch (ubicaciones[i]) {
        case "VIP":
            precioBase = 10000;
            break;
        case "Platea":
            precioBase = 7000;
            break;
        case "Balcón":
            precioBase = 5000;
            break;
        default:
            System.out.println("Ubicación desconocida. Se asigna precio base 0.");
            break;
    }

    double descuento = 0.0;
    String tipo = tipoCliente[i];

    if (tipo != null) {
        tipo = tipo.toLowerCase();
        switch (tipo) {
            case "estudiante":
                descuento = 0.10;
                break;
            case "tercera edad":
                descuento = 0.15;
                break;
            default:
                // cliente normal, sin descuento
                break;
        }
    } else {
        System.out.println("Tipo de cliente no especificado.");
    }
    
        double total = precioBase * (1 - descuento);

        System.out.println("\n--- Boleta ---");
        System.out.println("Cliente: " + nombresClientes[i]);
        System.out.println("Ubicación: " + ubicaciones[i]);
        System.out.println("Tipo de cliente: " + tipoCliente[i]);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento: " + (int) (descuento * 100) + "%");
        System.out.println("Total pagado: $" + total);
    }

    static String obtenerTipoCliente(Scanner scanner) {
        while (true) {
            System.out.println("Tipo de cliente: 1. Normal 2. Estudiante 3. Tercera Edad");
            System.out.print("Seleccione una opción (1-3): ");
            if (scanner.hasNextInt()) {
                int tipo = scanner.nextInt();
                scanner.nextLine();
                switch (tipo) {
                    case 1: 
                        return "normal";
                    case 2:
                        return"estudiante";
                    case 3:
                        return "tercera edad";
                    default: 
                        System.out.println("Opción fuera de rango. Intente otra vez.");
                }
            } else {
                System.out.println("Entrada no válida. Intente otra vez.");
                scanner.nextLine();
            }
        }
    }
}
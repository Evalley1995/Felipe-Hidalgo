/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s7_felipe_hidalgo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class Exp3_S7_Felipe_Hidalgo {
    static int capacidadSala = 100;
    static int entradasDisponibles = 100;
    static int totalEntradasVendidas = 0;
    
    static ArrayList<String> ubicaciones = new ArrayList<>();
    static ArrayList<Integer> preciosFinales = new ArrayList<>();
    static ArrayList<String> descAplicados = new ArrayList<>();
    static ArrayList<String> nombresClientes = new ArrayList<>();
    
    static ArrayList<String> reservasClientes = new ArrayList<>();
    static ArrayList<String> reservasUbicaciones = new ArrayList<>();
    private static String ubicacionReserva;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\n--- Bienvenido a Solticket---");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Ver resumen de ventas");
            System.out.println("3. Generar boletas");
            System.out.println("4. Reservar entrada");
            System.out.println("5. Ver reservas");
            System.out.println("6. Comprar entrada reservada");
            System.out.println("7. Eliminar reserva.");
            System.out.println("8. Salir");
            System.out.println("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    venderEntrada(scanner);
                    break;
                case 2:
                    mostrarResumen();
                    break;
                case 3:
                    generarBoletas();
                    break;
                case 4:
                    reservarEntrada(scanner);
                    break;
                case 5:
                    mostrarReservas();
                    break;
                case 6:
                    convertirReservaEnCompra(scanner);
                    break;
                case 7:
                    eliminarReservas(scanner);
                    break;
                case 8:
                    System.out.println("Gracias por visitar Solticket. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            } 
        }    while (opcion != 8);
        
        scanner.close();
    }
    
        
    public static void venderEntrada(Scanner scanner) {
            String nombreCliente;
            String ubicacion = "";
            int precioBase = 0;
            double descuento = 0;
            
            if (entradasDisponibles <= 0) {
                System.out.println("No hay entradas disponibles.");
                return;
        }
        
        System.out.println("Ingrese su nombre: ");
        nombreCliente = scanner.nextLine();
        
        int tipoUbicacion = -1;
        while (tipoUbicacion < 1 || tipoUbicacion > 3) {
            System.out.println("Seleccione ubicación: 1. VIP ($10000), 2. Platea ($7000), 3. Balcón ($5000)");
            System.out.println("Ingrese el número de la ubicación (1, 2, 3): ");
            
            if (scanner.hasNextInt()) {
                tipoUbicacion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Selección no válida. eliga opción (1, 2, 3.)");
                scanner.nextLine();
            }
        }
        
        
        switch (tipoUbicacion) {
            case 1: {
                ubicacion = "VIP";
                precioBase = 10000;
                break;
            }
            case 2: {
                ubicacion = "Platea";
                precioBase = 7000;
                break;
            }
            case 3: {
                ubicacion = "Balcón";
                precioBase = 5000;
                break;
            }
        }
        
        String esEstudiante;
        do {
            System.out.println("¿Es estudiante? (si/no): ");
            esEstudiante = scanner.nextLine().trim().toLowerCase();
            if (!esEstudiante.equals("si") && !esEstudiante.equals("no")) {
                System.out.println("Opción no válida. Escriba 'si' o 'no'");
           }
        } while (!esEstudiante.equals("si") && !esEstudiante.equals("no"));
        
       boolean respuestaEstudiante = esEstudiante.equals("si");
        
        String esTerceraEdad;
        do {
            System.out.println("¿Es de la tercera edad? (si/no): ");
            esTerceraEdad = scanner.nextLine().trim().toLowerCase();
            if (!esTerceraEdad.equals("si") && !esTerceraEdad.equals("no")) {
                System.out.println("Opción no válida. Escriba 'si' o 'no'");
            }
        } while (!esTerceraEdad.equals("si") && !esTerceraEdad.equals("no"));
        
        boolean respuestaTercera = esTerceraEdad.equals("si");
        
        descuento = 0;
        String descAplicado = "Sin descuento";
        if (esEstudiante.equals("si")) {
            descuento = 0.10;
            descAplicado = "10% Estudiante";
        } else if (esTerceraEdad.equals("si")) {
            descuento = 0.15;
            descAplicado = "15% Tercera edad";
        }
        
        
        
        int precioFinal = (int) (precioBase * (1 - descuento));
        
        nombresClientes.add(nombreCliente);
        ubicaciones.add(ubicacion);
        preciosFinales.add(precioFinal);
        descAplicados.add(descAplicado);
        
        totalEntradasVendidas++;
        entradasDisponibles--;
        
        
        System.out.println("Entrada vendida con éxito " + nombreCliente + " Precio total " + precioFinal);
            
    }
        
    public static void reservarEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay entradas disponibles.");
            return;
        }
        
        System.out.println("Ingrese su nombre para reservar entrada: ");
        String nombreReserva = scanner.nextLine();
        
        int tipo = -1;
        while (tipo < 1 || tipo > 3) {
            System.out.println("Seleccione ubicación para reservar: 1. (VIP), 2. (Platea), 3. (Balcón)");
            if (scanner.hasNextInt()) {
                tipo = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opción inválida. Eliga (1, 2, 3");
                scanner.nextLine();
            }
        }
        
        switch (tipo) {
            case 1:
                ubicacionReserva = "VIP";
                break;
            case 2:
                ubicacionReserva = "Platea";
                break;
            case 3:
                ubicacionReserva = "Balcón";
                break;
            default: 
                System.out.println("Ubicación inválida.");
            return;
        }
        
        reservasClientes.add(nombreReserva);
        reservasUbicaciones.add(ubicacionReserva);
        entradasDisponibles--;
        
        System.out.println("Reserva realizada para " + nombreReserva + " en ubicación " + ubicacionReserva);
        System.out.println("Clientes reservados: " + reservasClientes);
        System.out.println("Ubicaciones reservadas: " + reservasUbicaciones);

        
    }
    
    public static void mostrarReservas() {
        if (reservasClientes.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        
        System.out.println("\n--- Reservas actuales ---");
        for (int i = 0; i < reservasClientes.size(); i++) {
            System.out.println((i + 1) + ". Cliente: " + reservasClientes.get(i) + ", ubicación: " + reservasUbicaciones.get(i));
        }
    }
    
    public static void eliminarReservas(Scanner scanner) {
        if (reservasClientes.isEmpty()) {
            System.out.println("No hay reservas para eliminar.");
            return;
        }
        
        System.out.println("Ingrese el nombre del cliente para eliminar su reserva: ");
        String nombre = scanner.nextLine();
        
        int indice = -1;
        for (int i = 0; i < reservasClientes.size(); i++) {
            if (reservasClientes.get(i).trim().equalsIgnoreCase(nombre)) {
                indice = i;
                break;
            }
        }
        
        if (indice == -1) {
            System.out.println("No se encontró una reserva con el nombre \""+ nombre + "\". ");
        } else {
            reservasClientes.remove(indice);
            reservasUbicaciones.remove(indice);
            entradasDisponibles++;
            System.out.println("Reserva de " + nombre + " eliminada con éxito.");
        }
        
    }
    
    public static void mostrarResumen() {
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas registradas aún.");
            return;
        }
        
        System.out.println("\n--- REsumen de ventas ---");
        for (int i = 0; i < ubicaciones.size(); i++) {
                        System.out.println("Venta" + (i + 1) + ": " + nombresClientes.get(i) + " - " + ubicaciones.get(i) + ", $" + preciosFinales.get(i) + ", " + descAplicados.get(i));
        }
    }
    
    public static void generarBoletas() {
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay boletas para generar.");
            return;
        }
        
        System.out.println("\n--- Boleta ---");
        for (int i = 0; i < ubicaciones.size(); i++) {
            String nombre = nombresClientes.get(i);
            String ubicacion = ubicaciones.get(i);
            int precioFinal = preciosFinales.get(i);
            String descuento = descAplicados.get(i);
            
            int precioBase = switch (ubicacion) {
                case "VIP" -> 10000;
                case "Platea" -> 7000;
                case "Balcón" -> 5000;
                default -> 0;
            };
            
            System.out.println("\n---------------------------------");
            System.out.println("          Boleta de compra");
            System.out.println("Cliente: " + nombre);
            System.out.println("Ubicación: " + ubicacion);
            System.out.println("Precio base: $" + precioBase);
            System.out.println("Descuento aplicado: " + descuento);
            System.out.println("Total a pagar: $" + precioFinal);
            System.out.println("Gracias por preferir Solticket. Hasta pronto!");
            System.out.println("---------------------------------");
            
        }
    }
    
      public static void convertirReservaEnCompra(Scanner scanner) {
        if (reservasClientes.isEmpty()) {
            System.out.println("No hay reservas disponibles para comprar.");
            return;
        }
        
        System.out.println("Ingrese su nombre para comprar reserva: ");
        String nombre = scanner.nextLine().trim();
        
        int indice = -1;
        for (int i = 0; i < reservasClientes.size(); i++) {
            if (reservasClientes.get(i).equalsIgnoreCase(nombre)) {
                indice = i;
                break;
            }
        }
        
        if (indice == -1) {
            System.out.println("No se encontró una reserva con ese nombre.");
            return;
        }
        String ubicacion = reservasUbicaciones.get(indice);
        int precioBase = switch (ubicacion) {
            case "VIP" -> 10000;
            case "Platea" -> 7000;
            case "Balcón" -> 5000;
            default -> 0;
        };
        
        String respuestaEstudiante;
        do {
            System.out.println("¿Es estudiante? (si/no): ");
            respuestaEstudiante = scanner.nextLine().trim().toLowerCase();        
        } while (!respuestaEstudiante.equals("si") && !respuestaEstudiante.equals("no"));
        boolean esEstudiante = respuestaEstudiante.equals("si");
        
        String respuestaTercera;
        do {
            System.out.println("¿Es persona de la tercera edad? (si/no): ");
            respuestaTercera = scanner.nextLine().trim().toLowerCase();
        } while (!respuestaTercera.equals("si") && !respuestaTercera.equals("no"));
        boolean esTerceraEdad = respuestaTercera.equals("si");
        
        double descuento = 0;
        String descAplicado = "Sin descuento";
        
        if (esEstudiante) {
            descuento = 0.10;
            descAplicado = "10% Estudiante";
        }
        if (esTerceraEdad && descuento < 0.15) {
            descuento = 0.15;
            descAplicado = "15% Tercera edad";
        }
        
        int precioFinal = (int) (precioBase * (1 - descuento));
        
        nombresClientes.add(nombre);
        ubicaciones.add(ubicacion);
        preciosFinales.add(precioFinal);
        descAplicados.add(descAplicado);
        
        reservasClientes.remove(indice);
        reservasUbicaciones.remove(indice);
        totalEntradasVendidas++;
        
        System.out.println("Reserva convertida en compra para " + nombre + ". Total pagado: $" + precioFinal);
        
        System.out.println("\n----------------------------------");
        System.out.println("         Boleta de compra");
        System.out.println("Cliente: " + nombre);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento aplicado: " + descAplicado);
        System.out.println("Total a pagar: $" + precioFinal);
        System.out.println("Gracias por comprar en Solticket. Hasta pronto!");
        System.out.println("---------------------------------------------------");
    }
}  

  


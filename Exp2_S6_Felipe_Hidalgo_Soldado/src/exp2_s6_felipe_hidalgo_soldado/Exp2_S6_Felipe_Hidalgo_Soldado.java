/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s6_felipe_hidalgo_soldado;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class Exp2_S6_Felipe_Hidalgo_Soldado {

   
    static final int CAPACIDAD_SALA = 100;
    static int cantidadVendidas = 0;
    static double ingresosTotales = 0;
    static int entradasReservadas = 0;

    static String[] entradasVendidas = new String[CAPACIDAD_SALA];
    static double[] preciosVendidos = new double[CAPACIDAD_SALA];
    static String[] ubicacionesVendidas = new String[CAPACIDAD_SALA];
    static int[] cantidadesVendidas = new int[CAPACIDAD_SALA];

    static ArrayList<String> reservas = new ArrayList<>();
    static ArrayList<Integer> cantidadesReservadas = new ArrayList<>();
    static ArrayList<String> ubicacionesReservadas = new ArrayList<>();
    static ArrayList<Long> tiemposDeReserva = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Bienvenido a Solticket ===");
            System.out.println("1. Reservar entradas");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Modificar una venta");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Ver promociones");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) { // Verificar el scanner
                case 1 -> reservarEntradas(scanner);
                case 2 -> comprarEntradas(scanner);
                case 3 -> modificarVenta(scanner);
                case 4 -> imprimirBoleta(scanner);
                case 5 -> mostrarPromociones();
                case 6 -> System.out.println("Gracias por visitar Solticket. Hasta pronto! ");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    public static void reservarEntradas(Scanner scanner) {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ubicación (VIP, Platea, General): ");
        String ubicacion = scanner.nextLine();
        System.out.print("Cantidad de entradas: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        if (cantidad + cantidadVendidas + entradasReservadas > CAPACIDAD_SALA) {
            System.out.println("No hay suficiente capacidad.");
            return; 
        }

        reservas.add(nombre);
        ubicacionesReservadas.add(ubicacion);
        cantidadesReservadas.add(cantidad);
        tiemposDeReserva.add(System.currentTimeMillis());
        entradasReservadas += cantidad;

        System.out.println("Reserva hecha para " + nombre + " (" + cantidad + " entradas en " + ubicacion + ")");
    }

    public static void comprarEntradas(Scanner scanner) {
        System.out.print("¿Desea convertir una reserva? (si/no): ");
        String respuesta = scanner.nextLine();
        String nombre;
        int cantidad;
        String ubicacion;

        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Ingrese nombre: ");
            nombre = scanner.nextLine();
            int index = -1;
            for (int i = 0; i < reservas.size(); i++) {
                if (reservas.get(i).trim().equalsIgnoreCase(nombre.trim())) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("Reserva no encontrada.");
                return;
            }

            long tiempoReserva = tiemposDeReserva.get(index);
            long tiempoAhora = System.currentTimeMillis();
            long diferencia = (tiempoAhora - tiempoReserva) / 1000;
            if (diferencia > 60) {
                System.out.println("La reserva ha expirado (pasaron más de 60 segundos)."); // Verificar si la reserva esta dentro del tiempo permitido
                reservas.remove(index);
                cantidadesReservadas.remove(index);
                ubicacionesReservadas.remove(index);
                tiemposDeReserva.remove(index);
                return;
            }

            cantidad = cantidadesReservadas.get(index);
            ubicacion = ubicacionesReservadas.get(index);
            reservas.remove(index); // Verifica que  los datos de reserva sean transferidos correctamente a la compra
            cantidadesReservadas.remove(index);
            ubicacionesReservadas.remove(index);
            tiemposDeReserva.remove(index);
            entradasReservadas -= cantidad;
        } else {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
            System.out.print("Ubicación (VIP, Platea, General): ");
            ubicacion = scanner.nextLine();
            System.out.print("Cantidad: ");
            cantidad = scanner.nextInt();
            scanner.nextLine();
        }

        if (cantidadVendidas + cantidad > CAPACIDAD_SALA) {
            System.out.println("Capacidad insuficiente.");
            return; // Verificar que no exceda la capacidad max de entradas
        }

        double precioBase = switch (ubicacion.toLowerCase()) {
            case "vip" -> 20000;
            case "platea" -> 15000;
            case "general" -> 10000;
            default -> {
                System.out.println("Ubicación inválida.");
                yield -1; // Verificar la ubicacíon y asignar precio
            }
        };
        if (precioBase == -1) return;

        System.out.print("¿Es estudiante? (si/no): ");
        boolean estudiante = scanner.nextLine().equalsIgnoreCase("si");
        System.out.print("¿Es tercera edad? (si/no): ");
        boolean terceraEdad = scanner.nextLine().equalsIgnoreCase("si"); // Verificar si el cliente es estudiante o tercera edad para aplicar descuento

        double descuento = 0;
        if (estudiante) descuento += 0.10;
        if (terceraEdad) descuento += 0.15;
        if (cantidad >= 3) descuento += 0.05;

        double precioFinal = precioBase * (1 - descuento); // Verificar  que los descuentos se apliquen
        double total = precioFinal * cantidad;

        for (int i = 0; i < cantidad; i++) {
            entradasVendidas[cantidadVendidas] = nombre;
            preciosVendidos[cantidadVendidas] = precioFinal;
            ubicacionesVendidas[cantidadVendidas] = ubicacion;
            cantidadesVendidas[cantidadVendidas] = cantidad;
            cantidadVendidas++;
        }
        ingresosTotales += total; // Verifica que el total se sume correctamente

        Boleta boleta = new Boleta(nombre, ubicacion, cantidad, precioFinal);
        boleta.imprimir(); // Verifica que los datos sean correctos antes de imprimir boleta
    }

    public static void modificarVenta(Scanner scanner) {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        for (int i = 0; i < cantidadVendidas; i++) {
            if (entradasVendidas[i].equalsIgnoreCase(nombre)) {
                System.out.print("Nueva ubicación: ");
                String nueva = scanner.nextLine();
                ubicacionesVendidas[i] = nueva;
                System.out.println("Venta modificada.");
                return;
            }
        }
        System.out.println("Cliente no encontrado.");
    }

    public static void imprimirBoleta(Scanner scanner) {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        for (int i = 0; i < cantidadVendidas; i++) {
            if (entradasVendidas[i].equalsIgnoreCase(nombre)) {
                Boleta boleta = new Boleta(entradasVendidas[i], ubicacionesVendidas[i],
                        cantidadesVendidas[i], preciosVendidos[i]);
                boleta.imprimir();
                return;
            }
        }
        System.out.println("Boleta no encontrada.");
    }

    public static void mostrarPromociones() {
        System.out.println("- 10% descuento estudiantes");
        System.out.println("- 15% tercera edad");
        System.out.println("- 5% por comprar 3 o más entradas");
    }
}
    
    


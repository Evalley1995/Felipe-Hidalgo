/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package EFT_s9_felipe_hidalgo;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author pipe-
 */
public class EFT_s9Felipe_Hidalgo {
  static final int MAX_ENTRADAS = 8;
    static ArrayList<String[]> entradas = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n--- Bienvenido a Solticket ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar entrada");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Ver entradas");
            System.out.println("6. Convertir reserva en compra");
            System.out.println("7. Imprimir boletas");
            System.out.println("8. Ver resumen de ventas.");
            System.out.println("9. Salir.");

            System.out.print("Ingrese opción : ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
            System.out.println("Opción no válida. Vuelva a elegir nuevamente");
                opcion = 0;
            }

            switch (opcion) {
                case 1: comprarEntrada(); break;
                case 2: reservarEntrada(); break;
                case 3: buscarEntrada(); break;
                case 4: eliminarEntrada(); break;
                case 5: verEntradas(); break;
                case 6: convertirReserva(); break;
                case 7: imprimirBoletas(); break;
                case 8: mostrarResumen(entradas); break;
                case 9: System.out.println("Gracias por visitar Solticket. Hasta pronto."); break;
                default: System.out.println("Opción inválida."); break;
            }

        } while (opcion != 9);
    }

    public static void comprarEntrada() {
        if (entradas.size() >= MAX_ENTRADAS) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        String[] entrada = crearEntrada("comprada");
        if (entrada != null) {
            entradas.add(entrada);
            System.out.println("Entrada comprada con éxito.");
            imprimirBoleta(entrada);
        }
    }

    public static void reservarEntrada() {
        if (entradas.size() >= MAX_ENTRADAS) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        String[] entrada = crearEntrada("reservada");
        if (entrada != null) {
            entradas.add(entrada);
            System.out.println("Entrada reservada con éxito.");
        }
    }

    public static String[] crearEntrada(String estado) {
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine().trim();

        String ubicacion = "";
        while (true) {
            System.out.print("Ubicación (platea, platea alta, galeria, palco): ");
            ubicacion = sc.nextLine().trim().toLowerCase();
            if (ubicacion.equals("platea") || ubicacion.equals("platea alta") ||
                ubicacion.equals("galeria") || ubicacion.equals("palco")) {
                break;
            } else {
                System.out.println("Ubicación inválida.");
            }
        }

        String tipoCliente = "";
        boolean esEstudiante = preguntar("¿Es estudiante? (s/n): ");
        boolean esMujer = preguntar("¿Es mujer? (s/n): ");
        boolean esNiño = preguntar("¿Es niño? (s/n): ");
        boolean esTerceraEdad = preguntar("¿Es tercera edad? (s/n): ");

        if (esEstudiante) tipoCliente += "estudiante ";
        if (esMujer) tipoCliente += "mujer ";
        if (esNiño) tipoCliente += "niño ";
        if (esTerceraEdad) tipoCliente += "tercera edad ";
        if (tipoCliente.isEmpty()) tipoCliente = "ninguno";

        return new String[] { nombre, ubicacion, tipoCliente.trim(), estado };
    }

    public static boolean preguntar(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = sc.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) return true;
            if (respuesta.equals("n")) return false;
            System.out.println("Respuesta inválida. Escriba 's' o 'n'.");
        }
    }

    public static void buscarEntrada() {
        System.out.print("Ingrese nombre a buscar: ");
        String nombre = sc.nextLine().trim();
        boolean encontrada = false;

        for (String[] entrada : entradas) {
            if (entrada[0].equalsIgnoreCase(nombre)) {
                System.out.println("Entrada encontrada: " + String.join(", ", entrada));
                encontrada = true;
            }
        }

        if (!encontrada) System.out.println("Entrada no encontrada.");
    }

    public static void eliminarEntrada() {
        System.out.print("Ingrese nombre para eliminar: ");
        String nombre = sc.nextLine().trim();
        boolean eliminada = false;

        for (int i = 0; i < entradas.size(); i++) {
            if (entradas.get(i)[0].equalsIgnoreCase(nombre)) {
                entradas.remove(i);
                System.out.println("Entrada eliminada.");
                eliminada = true;
                break;
            }
        }

        if (!eliminada) System.out.println("Entrada no encontrada.");
    }

    public static void verEntradas() {
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas registradas.");
        } else {
            System.out.println("--- LISTA DE ENTRADAS ---");
            for (String[] entrada : entradas) {
                System.out.println("Nombre: " + entrada[0] + ", Ubicación: " + entrada[1] +
                                   ", Cliente: " + entrada[2] + ", Estado: " + entrada[3]);
            }
        }
    }

    public static void convertirReserva() {
        System.out.print("Ingrese nombre para convertir su reserva: ");
        String nombre = sc.nextLine().trim();
        boolean convertida = false;

        for (String[] entrada : entradas) {
            if (entrada[0].equalsIgnoreCase(nombre) && entrada[3].equalsIgnoreCase("reservada")) {
                entrada[3] = "comprada";
                System.out.println("Reserva convertida en compra.");
                imprimirBoleta(entrada);
                convertida = true;
                break;
            }
        }

        if (!convertida) System.out.println("Reserva no encontrada o ya fue comprada.");
    }

    public static void imprimirBoleta(String[] entrada) {
        double precioBase;

        switch (entrada[1].toLowerCase()) {
            case "palco": precioBase = 20000; break;
            case "platea": precioBase = 15000; break;
            case "platea alta": precioBase = 12000; break;
            case "galeria": precioBase = 8000; break;
            default: precioBase = 10000; break;
        }

        double descuento = 0;
        String tipo = entrada[2].toLowerCase();

        if (tipo.contains("estudiante")) descuento += 0.10;
        if (tipo.contains("mujer")) descuento += 0.10;
        if (tipo.contains("niño")) descuento += 0.05;
        if (tipo.contains("tercera edad")) descuento += 0.15;

        double total = precioBase * (1 - descuento);

        System.out.println("\n--- BOLETA ---");
        System.out.println("Nombre: " + entrada[0]);
        System.out.println("Ubicación: " + entrada[1]);
        System.out.println("Tipo Cliente: " + entrada[2]);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento aplicado: " + (int)(descuento * 100) + "%");
        System.out.println("Total a pagar: $" + total);
    }

   public static void imprimirBoletas() {
    double precioBase = 10000;
    boolean hayCompradas = false;

    for (String[] entrada : entradas) {
        if (entrada[3].equalsIgnoreCase("comprada")) {
            hayCompradas = true;
            double descuento = 0;

            if (entrada[2].contains("estudiante")) descuento += 0.10;
            if (entrada[2].contains("mujer")) descuento += 0.10;
            if (entrada[2].contains("niño")) descuento += 0.05;
            if (entrada[2].contains("tercera edad")) descuento += 0.15;

            double total = precioBase * (1 - descuento);

            System.out.println("\n--- BOLETA ---");
            System.out.println("Nombre: " + entrada[0]);
            System.out.println("Ubicación: " + entrada[1]);
            System.out.println("Tipo Cliente: " + entrada[2]);
            System.out.println("Descuento aplicado: " + (int)(descuento * 100) + "%");
            System.out.println("Total a pagar: $" + total);
        }
    }

    if (!hayCompradas) {
        System.out.println("No hay entradas compradas para imprimir.");
    }
  }
   
   public static void mostrarResumen(ArrayList<String[]> entradas) {
    System.out.println("\n--- Resumen de Ventas ---");
    int totalVendidas = 0;
    int totalReservadas = 0;
    int totalIngresos = 0;

    int platea = 0, galeria = 0, palco = 0, plateaAlta = 0;

    int estudiantes = 0, mujeres = 0, niños = 0, terceraEdad = 0;

    for (String[] entrada : entradas) {
        String estado = entrada[3];
        String ubicacion = entrada[1];
        String cliente = entrada[2];

        if (estado.equals("comprada")) {
            totalVendidas++;

            // Ingresos por ubicación
            int precio = 0;
            if (ubicacion.equalsIgnoreCase("platea")) {
                precio = 12000;
                platea++;
            } else if (ubicacion.equalsIgnoreCase("galeria")) {
                precio = 8000;
                galeria++;
            } else if (ubicacion.equalsIgnoreCase("palco")) {
                precio = 15000;
                palco++;
            } else if (ubicacion.equalsIgnoreCase("platea alta")) {
                precio = 10000;
                plateaAlta++;
            }

            int descuento = 0;
            if (cliente.contains("estudiante")) {
                descuento += 10;
                estudiantes++;
            }
            if (cliente.contains("mujer")) {
                descuento += 5;
                mujeres++;
            }
            if (cliente.contains("niño")) {
                descuento += 15;
                niños++;
            }
            if (cliente.contains("tercera edad")) {
                descuento += 20;
                terceraEdad++;
            }

            int descuentoTotal = (precio * descuento) / 100;
            totalIngresos += precio - descuentoTotal;

        } else if (estado.equals("reservada")) {
            totalReservadas++;
        }
    }
    
     if (totalVendidas == 0 && totalReservadas == 0) {
        System.out.println("No hay entradas compradas ni reservadas.");
     } else {

    System.out.println("Entradas vendidas: " + totalVendidas);
    System.out.println("Entradas reservadas: " + totalReservadas);
    System.out.println("Total ingresos: $" + totalIngresos);
    System.out.println("\nEntradas vendidas por ubicación:");
    System.out.println("- Platea: " + platea);
    System.out.println("- Galeria: " + galeria);
    System.out.println("- Palco: " + palco);
    System.out.println("- Platea Alta: " + plateaAlta);
    System.out.println("\nDescuentos aplicados:");
    System.out.println("- Estudiantes: " + estudiantes);
    System.out.println("- Mujeres: " + mujeres);
    System.out.println("- Niños: " + niños);
    System.out.println("- Tercera edad: " + terceraEdad);
  }
 }
}
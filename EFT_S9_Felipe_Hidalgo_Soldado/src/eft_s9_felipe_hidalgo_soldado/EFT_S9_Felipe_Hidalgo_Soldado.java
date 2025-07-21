/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_felipe_hidalgo_soldado;

import com.evaluacion.gestionflota.GestionFlota;
import com.evaluacion.veahiculo.Vehiculo;
import com.evaluacion.vehiculocarga.ICalculoBoleta;
import com.evaluacion.vehiculocarga.VehiculoCarga;
import com.evaluacion.vehiculopasajero.VehiculoPasajero;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class EFT_S9_Felipe_Hidalgo_Soldado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GestionFlota gestor = new GestionFlota();

        // Hilos para carga y validación
        Thread hiloCarga = new Thread(() -> gestor.cargarDesdeArchivo("vehiculos.txt"));
        Thread hiloValidacion = new Thread(() -> gestor.validarPatentes());

        hiloCarga.start();
        try { hiloCarga.join(); 
        } catch (InterruptedException e) {}
        hiloValidacion.start();
        try { hiloValidacion.join(); 
        } catch (InterruptedException e) {}

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("\n=== MENÚ GESTIÓN DE FLOTA ===");
            System.out.println("1. Listar todos los vehículos");
            System.out.println("2. Mostrar boleta de un vehículo");
            System.out.println("3. Ver vehículos con arriendo largo (≥ 7 días)");
            System.out.println("4. Modificar datos de un vehículo");
            System.out.println("5. Guardar y salir");

            // Validar opción de menú
            while (true) {
                System.out.print("Seleccione una opción: ");
                String entrada = sc.nextLine().trim();
                try {
                    opcion = Integer.parseInt(entrada);
                    if (opcion >= 1 && opcion <= 5) break;
                    else System.out.println("Opción fuera de rango (1-5). Intente nuevamente.");
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número válido (1-5).");
                }
            }

            switch(opcion) {
                case 1:
                    gestor.listarVehiculos();
                    break;

                case 2: {
                    System.out.print("Ingrese la patente del vehículo: ");
                    String pat = sc.nextLine().trim();
                    if (pat.isEmpty()) {
                        System.out.println("La patente no puede estar vacía.");
                        break;
                    }
                    Vehiculo v = gestor.getFlota().get(pat);
                    if (v != null && v instanceof ICalculoBoleta) {
                        ((ICalculoBoleta) v).calcularYMostrarBoleta();
                    } else {
                        System.out.println("No se encontró vehículo con esa patente.");
                    }
                    break;
                }

                case 3:
                    gestor.listarArriendosLargos();
                    break;

                case 4: {
                    System.out.print("Ingrese la patente del vehículo a modificar: ");
                    String patMod = sc.nextLine().trim();
                    if (patMod.isEmpty()) {
                        System.out.println("La patente no puede estar vacía.");
                        break;
                    }
                    Vehiculo vMod = gestor.getFlota().get(patMod);
                    if (vMod != null) {
                        modificarDatosVehiculo(vMod, sc);
                    } else {
                        System.out.println("No se encontró vehículo con esa patente.");
                    }
                    break;
                }

                case 5:
                    gestor.guardarEnArchivo("vehiculos.txt");
                    System.out.println("Estado de los vehículos guardado. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while(opcion != 5);

        sc.close();
    }

    // Método auxiliar para modificar datos del vehículo
    public static void modificarDatosVehiculo(Vehiculo v, Scanner sc) {
        System.out.println("=== Modificar datos de: " + v.getPatente() + " ===");
        System.out.println("1. Modificar días de arriendo");
        System.out.println("2. Modificar precio por día");
        if (v instanceof VehiculoCarga) {
            System.out.println("3. Modificar capacidad de carga (toneladas)");
        } else if (v instanceof VehiculoPasajero) {
            System.out.println("3. Modificar cantidad de pasajeros");
        }
        System.out.println("4. Volver");

        int op = 0;
        while (true) {
            System.out.print("Seleccione una opción: ");
            String entrada = sc.nextLine().trim();
            try {
                op = Integer.parseInt(entrada);
                if (op >= 1 && op <= 4) break;
                else System.out.println("Opción fuera de rango (1-4). Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido (1-4).");
            }
        }

        try {
            switch (op) {
                case 1: {
                    int nuevosDias = 0;
                    while (true) {
                        System.out.print("Nuevo número de días de arriendo: ");
                        String input = sc.nextLine().trim();
                        try {
                            nuevosDias = Integer.parseInt(input);
                            if (nuevosDias > 0) break;
                            else System.out.println("Debe ser mayor que cero.");
                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar un número válido.");
                        }
                    }
                    v.setDiasArriendo(nuevosDias);
                    System.out.println("Días de arriendo actualizados.");
                    break;
                }
                case 2: {
                    double nuevoPrecio = 0;
                    while (true) {
                        System.out.print("Nuevo precio por día: ");
                        String input = sc.nextLine().trim();
                        try {
                            nuevoPrecio = Double.parseDouble(input);
                            if (nuevoPrecio > 0) break;
                            else System.out.println("Debe ser mayor que cero.");
                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar un número válido.");
                        }
                    }
                    v.setPrecioDia(nuevoPrecio);
                    System.out.println("Precio actualizado.");
                    break;
                }
                case 3:
                    if (v instanceof VehiculoCarga) {
                        double nuevaCap = 0;
                        while (true) {
                            System.out.print("Nueva capacidad (toneladas): ");
                            String input = sc.nextLine().trim();
                            try {
                                nuevaCap = Double.parseDouble(input);
                                if (nuevaCap > 0) break;
                                else System.out.println("Debe ser mayor que cero.");
                            } catch (NumberFormatException e) {
                                System.out.println("Debe ingresar un número válido.");
                            }
                        }
                        ((VehiculoCarga) v).setCapacidadToneladas(nuevaCap);
                        System.out.println("Capacidad actualizada.");
                    } else if (v instanceof VehiculoPasajero) {
                        int nuevoPas = 0;
                        while (true) {
                            System.out.print("Nueva cantidad de pasajeros: ");
                            String input = sc.nextLine().trim();
                            try {
                                nuevoPas = Integer.parseInt(input);
                                if (nuevoPas > 0) break;
                                else System.out.println("Debe ser mayor que cero.");
                            } catch (NumberFormatException e) {
                                System.out.println("Debe ingresar un número válido.");
                            }
                        }
                        ((VehiculoPasajero) v).setCantidadPasajeros(nuevoPas);
                        System.out.println("Cantidad de pasajeros actualizada.");
                    }
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error al modificar datos: " + e.getMessage());
        }
    
    }
    
}

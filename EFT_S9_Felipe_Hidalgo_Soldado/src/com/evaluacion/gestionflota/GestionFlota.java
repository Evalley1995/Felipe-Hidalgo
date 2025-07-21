/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evaluacion.gestionflota;

import com.evaluacion.veahiculo.Vehiculo;
import com.evaluacion.vehiculocarga.VehiculoCarga;
import com.evaluacion.vehiculopasajero.VehiculoPasajero;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author pipe-
 */
public class GestionFlota {
     private HashMap<String, Vehiculo> flota;

    public GestionFlota(HashMap<String, Vehiculo> flota) {
        this.flota = flota;
    }
     
 
    public GestionFlota() {
        flota = new HashMap<>();
    }

    public HashMap<String, Vehiculo> getFlota() { return flota; }

    // Listar todos los vehículos
    public void listarVehiculos() {
        if (flota.isEmpty()) {
            System.out.println("No hay vehículos en la flota.");
            return;
        }
        System.out.println("==== LISTADO DE VEHÍCULOS ====");
        for (Vehiculo v : flota.values()) {
            v.mostrarDatos();
            if (v instanceof VehiculoCarga) {
                System.out.println("Tipo: Carga");
            } else if (v instanceof VehiculoPasajero) {
                System.out.println("Tipo: Pasajeros");
            }
            System.out.println("---------------------------");
        }
    }

    // Filtrar arriendos largos
    public void listarArriendosLargos() {
        boolean hayLargos = false;
        System.out.println("=== Vehículos con arriendo de 7 días o más ===");
        for (Vehiculo v : flota.values()) {
            if (v.getDiasArriendo() >= 7) {
                v.mostrarDatos();
                if (v instanceof VehiculoCarga) {
                    System.out.println("Tipo: Carga");
                } else if (v instanceof VehiculoPasajero) {
                    System.out.println("Tipo: Pasajeros");
                }
                System.out.println("---------------------------");
                hayLargos = true;
            }
        }
        if (!hayLargos) {
            System.out.println("No hay vehículos con arriendo largo.");
        }
    }

    // Guardar en archivo externo
    public synchronized void guardarEnArchivo(String archivo) {
        try (FileWriter fw = new FileWriter(archivo, false)) {
            for (Vehiculo v : flota.values()) {
                if (v instanceof VehiculoCarga) {
                    VehiculoCarga c = (VehiculoCarga) v;
                    fw.write(c.getPatente() + "," +
                            c.getMarca() + "," +
                            c.getModelo() + "," +
                             c.getAnio() + "," +
                            c.getDiasArriendo() + "," +
                            c.getPrecioDia() + ",carga," +
                            c.getCapacidadToneladas() + "\n");
                } else if (v instanceof VehiculoPasajero) {
                    VehiculoPasajero p = (VehiculoPasajero) v;
                    fw.write(p.getPatente() + "," +
                            p.getMarca() + "," +
                            p.getModelo() + "," +
                            p.getAnio() + "," +
                            p.getDiasArriendo() + "," +
                            p.getPrecioDia() + ",pasajeros," +
                            p.getCantidadPasajeros() + "\n");
                }
            }
            System.out.println("Vehículos guardados en " + archivo);
        } catch (Exception e) {
            System.out.println("Error al guardar vehículos: " + e.getMessage());
        }
    }

    // Cargar desde archivo externo
    public synchronized void cargarDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length != 8) {
                    System.out.println("Línea mal formada: " + linea);
                    continue;
                }
                try {
                    String patente = datos[0];
                    String marca = datos[1];
                    String modelo = datos[2];
                    int anio = Integer.parseInt(datos[3]);
                    int diasArriendo = Integer.parseInt(datos[4]);
                    double precioDia = Double.parseDouble(datos[5]);
                    String tipo = datos[6];
                    if (tipo.equals("carga")) {
                        double capacidad = Double.parseDouble(datos[7]);
                        VehiculoCarga v = new VehiculoCarga(patente, marca, modelo, anio, diasArriendo, precioDia, capacidad);
                        flota.put(patente, v);
                    } else if (tipo.equals("pasajeros")) {
                        int pasajeros = Integer.parseInt(datos[7]);
                        VehiculoPasajero v = new VehiculoPasajero(patente, marca, modelo, anio, diasArriendo, precioDia, pasajeros);
                        flota.put(patente, v);
                    } else {
                        System.out.println("Tipo de vehículo desconocido: " + tipo);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error en los datos numéricos: " + linea);
                }
            }
            System.out.println("Vehículos cargados desde " + archivo);
        } catch (Exception e) {
            System.out.println("Error al cargar vehículos: " + e.getMessage());
        }
    }

    // Validar patentes (para usar con hilos)
    public synchronized void validarPatentes() {
        HashSet<String> vistas = new HashSet<>();
        for (Vehiculo v : flota.values()) {
            if (vistas.contains(v.getPatente())) {
                System.out.println("¡Patente repetida encontrada: " + v.getPatente() + "!");
            } else {
                vistas.add(v.getPatente());
            }
        }
        System.out.println("Validación de patentes terminada.");
    }
}
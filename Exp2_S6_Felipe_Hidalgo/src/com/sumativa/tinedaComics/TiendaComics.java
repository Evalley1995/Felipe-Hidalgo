/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.tinedaComics;

import com.sumativa.comics.Comics;
import com.sumativa.usuario.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author pipe-
 */
public class TiendaComics {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Comics> comics = new ArrayList<>();
    static HashMap<String, Usuario> usuarios = new HashMap<>();
    static HashSet<String> autoresUnicos = new HashSet<>();
    static TreeSet<String> autoresOrdenados = new TreeSet<>();

    public static void main(String[] args) {
        comics = leerComicsDesdeCSV("comics.csv");

        int opcion = 0;
        do {
            System.out.println("\n=== Menú ComicCollector ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Comprar o reservar cómic");
            System.out.println("4. Mostrar historial de usuario");
            System.out.println("5. Mostrar cómics disponibles");
            System.out.println("6. Mostrar autores únicos (HashSet)");
            System.out.println("7. Mostrar autores ordenados (TreeSet)");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    mostrarUsuarios();
                    break;
                case 3:
                    comprarOReservarComic();
                    break;
                case 4:
                    mostrarHistorialUsuario();
                    break;
                case 5:
                    mostrarComicsDisponibles();
                    break;
                case 6:
                    mostrarAutoresUnicos();
                    break;
                case 7:
                    mostrarAutoresOrdenados();
                    break;
                case 8:
                    guardarComicsEnCSV("comics.csv");
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 8);
    }

    // ---- MÉTODOS AUXILIARES ----

    public static ArrayList<Comics> leerComicsDesdeCSV(String rutaArchivo) {
        ArrayList<Comics> comics = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String id = partes[0].trim();
                    String titulo = partes[1].trim();
                    String autor = partes[2].trim();
                    String estado = partes[3].trim();
                    comics.add(new Comics(id, titulo, autor, estado));
                    // Llenar autores únicos y ordenados
                    autoresUnicos.add(autor);
                    autoresOrdenados.add(autor);
                }
            }
            System.out.println("¡Cómics cargados correctamente!");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return comics;
    }

    public static void guardarComicsEnCSV(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Comics c : comics) {
                bw.write(c.getId() + "," + c.getTitulo() + "," + c.getAutor() + "," + c.getEstado());
                bw.newLine();
            }
            System.out.println("Cómics guardados correctamente en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar cómics: " + e.getMessage());
        }
    }

    public static void crearUsuario() {
        String nombre, rut;
        while (true) {
            System.out.print("Ingrese nombre: ");
            nombre = scanner.nextLine();
            if (nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) break;
            System.out.println("Nombre inválido. Intente solo con letras.");
        }

        while (true) {
            System.out.print("Ingrese RUT: ");
            rut = scanner.nextLine().trim();
            if (rut.matches("\\d{8,9}")) {
                if (usuarios.containsKey(rut)) {
                    System.out.println("Ese RUT ya existe. Intente con otro.");
                } else {
                    break;
                }
            } else {
                System.out.println("RUT inválido. Debe tener 8 u 9 dígitos.");
            }
        }

        Usuario usuario = new Usuario(nombre, rut);
        usuarios.put(rut, usuario);
        System.out.println("Usuario creado correctamente: " + usuario.getNombre());
    }

    public static void mostrarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("Usuarios registrados:");
            for (Usuario u : usuarios.values()) {
                System.out.println(u.getNombre() + " | RUT: " + u.getRut());
            }
        }
    }

    public static void mostrarComicsDisponibles() {
        boolean hayDisponibles = false;
        System.out.println("=== Cómics disponibles ===");
        for (Comics c : comics) {
            if (c.getEstado().equalsIgnoreCase("disponible")) {
                System.out.println(c);
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay cómics disponibles.");
        }
    }

    public static void comprarOReservarComic() {
        System.out.print("Ingrese RUT del usuario: ");
        String rut = scanner.nextLine().trim();
        if (!usuarios.containsKey(rut)) {
            System.out.println("El usuario no existe.");
            return;
        }
        Usuario usuario = usuarios.get(rut);

        ArrayList<Comics> disponibles = new ArrayList<>();
        for (Comics c : comics) {
            if (c.getEstado().equalsIgnoreCase("disponible")) {
                disponibles.add(c);
            }
        }
        if (disponibles.isEmpty()) {
            System.out.println("No hay cómics disponibles.");
            return;
        }
        System.out.println("Cómics disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i));
        }

        int eleccion = -1;
        while (true) {
            System.out.print("Seleccione el número del cómic: ");
            try {
                eleccion = Integer.parseInt(scanner.nextLine()) - 1;
                if (eleccion >= 0 && eleccion < disponibles.size()) {
                    break;
                } else {
                    System.out.println("Número fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        Comics elegido = disponibles.get(eleccion);

        System.out.print("¿Desea comprar (1) o reservar (2) este cómic?: ");
        int opcionAccion;
        while (true) {
            try {
                opcionAccion = Integer.parseInt(scanner.nextLine());
                if (opcionAccion == 1 || opcionAccion == 2) {
                    break;
                } else {
                    System.out.println("Elija 1 para comprar o 2 para reservar.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        if (opcionAccion == 1) {
            elegido.setEstado("vendido");
            System.out.println("Cómic comprado exitosamente.");
        } else {
            elegido.setEstado("reservado");
            System.out.println("Cómic reservado exitosamente.");
        }
        usuario.agregarComic(elegido);
    }

    public static void mostrarHistorialUsuario() {
        System.out.print("Ingrese RUT del usuario: ");
        String rut = scanner.nextLine().trim();
        if (!usuarios.containsKey(rut)) {
            System.out.println("El usuario no existe.");
                    
            return;
        }
        Usuario usuario = usuarios.get(rut);
        ArrayList<Comics> historial = usuario.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("Este usuario no ha comprado ni reservado ningún cómic.");
        } else {
            System.out.println("Historial de cómics de " + usuario.getNombre() + ":");
            for (Comics c : historial) {
                System.out.println(c);
            }
        }
    }

    public static void mostrarAutoresUnicos() {
        System.out.println("=== Autores únicos (HashSet, orden no garantizado) ===");
        for (String autor : autoresUnicos) {
            System.out.println(autor);
        }
    }

    public static void mostrarAutoresOrdenados() {
        System.out.println("=== Autores ordenados alfabéticamente (TreeSet) ===");
        for (String autor : autoresOrdenados) {
            System.out.println(autor);
        }
    } 
}

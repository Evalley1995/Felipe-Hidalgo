/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.formativa.Biblioteca;

import com.formativa.libro.Libro;
import com.formativa.usuario.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author pipe-
 */
public class Biblioteca {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static HashMap<String, Libro> libros = new HashMap<>();

    public static void main(String[] args) {
        // Cargar libros con ID único
        libros.put("L001", new Libro("L001", "El Principito", "Antoine de Saint-Exupéry"));
        libros.put("L002", new Libro("L002", "Cien años de soledad", "Gabriel García Márquez"));
        libros.put("L003", new Libro("L003", "Harry Potter y la piedra filosofal", "J.K. Rowling"));

        int opcion = 0;
        do {
            System.out.println("\n=== Menú Biblioteca ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Arrendar libro");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número. Intente de nuevo.");
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    arrendarLibro();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    public static void crearUsuario() {
        String nombre, rut;

        // Validar nombre
        while (true) {
            System.out.print("Ingrese su nombre: ");
            nombre = scanner.nextLine();
            if (nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                break;
            } else {
                System.out.println("Nombre inválido. Solo se permiten letras.");
            }
        }

        // Validar RUT y que no esté repetido
        while (true) {
            System.out.print("Ingrese su RUT (sin puntos ni guión): ");
            rut = scanner.nextLine();
            if (rut.matches("\\d{7,8}")) {
                boolean rutExistente = false;
                for (Usuario u : usuarios) {
                    if (u.getRut().equals(rut)) {
                        rutExistente = true;
                        break;
                    }
                }
                if (rutExistente) {
                    System.out.println("El RUT ya está registrado.");
                } else {
                    break;
                }
            } else {
                System.out.println("RUT inválido. Debe tener entre 7 y 8 dígitos.");
            }
        }

        usuarios.add(new Usuario(nombre, rut));
        System.out.println("Usuario creado correctamente.");
    }

    public static void arrendarLibro() {
        if (usuarios.isEmpty()) {
            System.out.println("Debe crear al menos un usuario antes de arrendar libros.");
            return;
        }
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
            return;
        }

        // Mostrar usuarios
        System.out.println("Usuarios registrados:");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
        }

        int idxUsuario = -1;
        while (true) {
            System.out.print("Seleccione el usuario (número): ");
            try {
                idxUsuario = Integer.parseInt(scanner.nextLine()) - 1;
                if (idxUsuario >= 0 && idxUsuario < usuarios.size()) {
                    break;
                } else {
                    System.out.println("Número fuera de rango. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        // Mostrar libros disponibles (solo los disponibles)
        ArrayList<Libro> disponibles = new ArrayList<>();
        ArrayList<String> idsDisponibles = new ArrayList<>();

        System.out.println("Libros disponibles:");
        int contador = 1;
        for (Map.Entry<String, Libro> entry : libros.entrySet()) {
            if (entry.getValue().isDisponible()) {
                System.out.println(contador + ". " + entry.getValue().getTitulo() + " - " + entry.getValue().getAutor() + " [ID: " + entry.getKey() + "]");
                disponibles.add(entry.getValue());
                idsDisponibles.add(entry.getKey());
                contador++;
            }
        }
        if (disponibles.isEmpty()) {
            System.out.println("No hay libros disponibles para arrendar.");
            return;
        }

        int idxLibro = -1;
        while (true) {
            System.out.print("Seleccione el libro (número): ");
            try {
                idxLibro = Integer.parseInt(scanner.nextLine()) - 1;
                if (idxLibro >= 0 && idxLibro < disponibles.size()) {
                    break;
                } else {
                    System.out.println("Número fuera de rango. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        // Marcar libro como no disponible
        String idLibroSeleccionado = idsDisponibles.get(idxLibro);
        libros.get(idLibroSeleccionado).setDisponible(false);
        System.out.println(" Libro arrendado con éxito a " + usuarios.get(idxUsuario).getNombre());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Set;
/**
 *
 * @author pipe-
 */
public class PeliculaValidador {
    private PeliculaValidador() {}
    private static final Set<String> GENEROS =
        Set.of("Accion","Comedia","Drama","Romance","Terror","Aventura");

    public static void validateForCreate(Pelicula p) { validateCommon(p); }
    public static void validateForUpdate(Pelicula p) {
        if (p.getId() == null) throw new IllegalArgumentException("ID requerido para actualizar");
        validateCommon(p);
    }

    private static void validateCommon(Pelicula p) {
        if (p == null) throw new IllegalArgumentException("Película nula");
        if (p.getTitulo() == null || p.getTitulo().isBlank())   throw new IllegalArgumentException("Título obligatorio");
        if (p.getDirector() == null || p.getDirector().isBlank()) throw new IllegalArgumentException("Director obligatorio");
        if (p.getAnio() < 1888 || p.getAnio() > 2100)           throw new IllegalArgumentException("Año fuera de rango");
        if (p.getDuracion() <= 0)                                throw new IllegalArgumentException("Duración > 0");
        if (p.getGenero() == null || !GENEROS.contains(p.getGenero()))
            throw new IllegalArgumentException("Género inválido");
    }
}

package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.Curso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CursoService {

    private final ArrayList<Curso> cursos;

    public CursoService() {
        cursos = new ArrayList<>();

        cursos.add(new Curso(1, "Programación Backend", "Luis Hidalgo Baeza", true));
        cursos.add(new Curso(2, "Base de Datos", "Luis Pérez", true));
        cursos.add(new Curso(3, "Arquitectura de Microservicios", "Camila Rojas", false));
        cursos.add(new Curso(4, "Desarrollo Web", "Felipe Torres", true));
        cursos.add(new Curso(5, "Testing de Software", "María Soto", true));
    }

    public List<Curso> obtenerCursosActivosOrdenados() {
        ArrayList<Curso> cursosActivos = new ArrayList<>();

        for (Curso curso : cursos) {
            if (curso.isActivo()) {
                cursosActivos.add(curso);
            }
        }

        cursosActivos.sort(Comparator.comparing(Curso::getNombre));

        return cursosActivos;
    }

    public List<String> mostrarCursosDisponibles() {
        List<Curso> cursosOrdenados = obtenerCursosActivosOrdenados();

        if (cursosOrdenados.isEmpty()) {
            throw new RuntimeException("No hay cursos disponibles en este momento.");
        }

        ArrayList<String> resultado = new ArrayList<>();

        for (Curso curso : cursosOrdenados) {
            String detalle = "ID: " + curso.getId()
                    + " | Curso: " + curso.getNombre()
                    + " | Docente: " + curso.getDocente()
                    + " | Estado: Activo";
            resultado.add(detalle);
        }

        return resultado;
    }
}
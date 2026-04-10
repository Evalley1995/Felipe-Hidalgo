package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.service.CursoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/cursos/disponibles")
    public List<String> obtenerCursosDisponibles() {
        return cursoService.mostrarCursosDisponibles();
    }
}
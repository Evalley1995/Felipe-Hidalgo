package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Curso;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository {
    List<Curso> obtenerCursos();
}
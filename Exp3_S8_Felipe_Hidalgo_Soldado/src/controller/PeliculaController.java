/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import config.Conexion;
import model.Pelicula;
import model.PeliculaDao;
import model.PeliculaValidador;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author pipe-
 */
public class PeliculaController {
     private final PeliculaDao dao = new PeliculaDao();

    // READs
    public List<Pelicula> listar() throws Exception {
        try (Connection cn = Conexion.getConnection()) {
            return dao.listar(cn);
        }
    }

    public Optional<Pelicula> buscarPorId(int id) throws Exception {
        try (Connection cn = Conexion.getConnection()) {
            return dao.buscarPorId(cn, id);
        }
    }

    public List<Pelicula> buscarPorTitulo(String patron) throws Exception {
        try (Connection cn = Conexion.getConnection()) {
            return dao.buscarPorTitulo(cn, patron == null ? "" : patron);
        }
    }

    public List<Pelicula> filtrar(String genero, Integer anioDesde, Integer anioHasta) throws Exception {
        try (Connection cn = Conexion.getConnection()) {
            return dao.filtrarPorGeneroYAnio(cn, genero, anioDesde, anioHasta);
        }
    }

    // CREATE
    public int crear(Pelicula p) throws Exception {
        PeliculaValidador.validateForCreate(p);
        try (Connection cn = Conexion.getConnection()) {
            int rows = dao.insertar(cn, p);
            if (rows != 1 || p.getId() == null) throw new IllegalStateException("No se pudo crear la película.");
            return p.getId();
        }
    }

    // UPDATE
    public boolean actualizar(Pelicula p) throws Exception {
        PeliculaValidador.validateForUpdate(p);
        try (Connection cn = Conexion.getConnection()) {
            return dao.actualizar(cn, p) == 1;
        }
    }

    // DELETE
    public boolean eliminar(int id) throws Exception {
        try (Connection cn = Conexion.getConnection()) {
            return dao.eliminarPorId(cn, id) == 1;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.EquipoDAO;
import java.util.List;
import model.EquipoDesktop;
import model.EquipoLaptop;
import model.EquipoListado;

/**
 *
 * @author pipe-
 */
public class EquipoController {
     private final EquipoDAO dao = new EquipoDAO();

    // DESKTOP
    public int crearDesktop(EquipoDesktop d) {
        validarDesktop(d, false);
        try {
            return dao.insertarDesktop(d);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear desktop: " + e.getMessage(), e);
        }
    }

    public boolean actualizarDesktop(EquipoDesktop d) {
        validarDesktop(d, true);
        try {
            return dao.actualizarDesktop(d);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar desktop: " + e.getMessage(), e);
        }
    }

    // LAPTOP
    public int crearLaptop(EquipoLaptop l) {
        validarLaptop(l, false);
        try {
            return dao.insertarLaptop(l);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear laptop: " + e.getMessage(), e);
        }
    }

    public boolean actualizarLaptop(EquipoLaptop l) {
        validarLaptop(l, true);
        try {
            return dao.actualizarLaptop(l);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar laptop: " + e.getMessage(), e);
        }
    }

    // COMÚN
    public boolean eliminar(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        try {
            return dao.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar equipo: " + e.getMessage(), e);
        }
    }

    public List<EquipoListado> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar equipos: " + e.getMessage(), e);
        }
    }

    // --- helpers ---
    private void validarDesktop(EquipoDesktop d, boolean requiereId) {
        if (d == null) throw new IllegalArgumentException("Equipo desktop nulo");
        if (requiereId && (d.getId() == null || d.getId() <= 0))
            throw new IllegalArgumentException("ID requerido para actualizar");
        if (isBlank(d.getModelo())) throw new IllegalArgumentException("Modelo obligatorio");
        if (isBlank(d.getCpu()))    throw new IllegalArgumentException("CPU obligatoria");
        if (d.getDiscoMb() <= 0)    throw new IllegalArgumentException("Disco (MB) > 0");
        if (d.getRamGb()  <= 0)     throw new IllegalArgumentException("RAM (GB) > 0");
        if (d.getPrecio() <= 0)     throw new IllegalArgumentException("Precio > 0");
        if (d.getFuenteW() <= 0)    throw new IllegalArgumentException("Fuente (W) > 0");
        if (isBlank(d.getFactor())) throw new IllegalArgumentException("Factor obligatorio");
    }

    private void validarLaptop(EquipoLaptop l, boolean requiereId) {
        if (l == null) throw new IllegalArgumentException("Equipo laptop nulo");
        if (requiereId && (l.getId() == null || l.getId() <= 0))
            throw new IllegalArgumentException("ID requerido para actualizar");
        if (isBlank(l.getModelo())) throw new IllegalArgumentException("Modelo obligatorio");
        if (isBlank(l.getCpu()))    throw new IllegalArgumentException("CPU obligatoria");
        if (l.getDiscoMb() <= 0)    throw new IllegalArgumentException("Disco (MB) > 0");
        if (l.getRamGb()  <= 0)     throw new IllegalArgumentException("RAM (GB) > 0");
        if (l.getPrecio() <= 0)     throw new IllegalArgumentException("Precio > 0");
        if (l.getPantallaIn() <= 0) throw new IllegalArgumentException("Pantalla (in) > 0");
        if (l.getUsbCount() < 0)    throw new IllegalArgumentException("USB count >= 0");
    }

    private boolean isBlank(String s) { return s == null || s.isBlank(); }
}

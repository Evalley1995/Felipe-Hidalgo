/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.VentaDAO;
import java.util.List;
import model.Venta;

/**
 *
 * @author pipe-
 */
public class VentaController {
    private final VentaDAO dao = new VentaDAO();

    public int crear(String clienteRut, int equipoId, int precioFinal) {
        if (isBlank(clienteRut)) throw new IllegalArgumentException("RUT obligatorio");
        if (equipoId <= 0)       throw new IllegalArgumentException("EquipoId inválido");
        if (precioFinal <= 0)    throw new IllegalArgumentException("Precio final > 0");
        try {
            return dao.crear(clienteRut.trim(), equipoId, precioFinal);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear venta: " + e.getMessage(), e);
        }
    }

    public boolean eliminar(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        try {
            return dao.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar venta: " + e.getMessage(), e);
        }
    }

    public List<Venta> listar() {
        try {
            return dao.listar();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar ventas: " + e.getMessage(), e);
        }
    }

    private boolean isBlank(String s) { return s == null || s.isBlank(); }
}

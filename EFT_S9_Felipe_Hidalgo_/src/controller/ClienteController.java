/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.ClienteDAO;
import model.Cliente;
import java.util.List;

/**
 *
 * @author pipe-
 */
public class ClienteController {
    private final ClienteDAO dao = new ClienteDAO();

    public boolean crear(Cliente c) {
        validarCrearActualizar(c, true);
        try {
            return dao.crear(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear cliente: " + e.getMessage(), e);
        }
    }

    public boolean actualizar(Cliente c) {
        validarCrearActualizar(c, false);
        try {
            return dao.actualizar(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    public boolean eliminar(String rut) {
        if (isBlank(rut)) throw new IllegalArgumentException("RUT obligatorio");
        try {
            return dao.eliminar(rut.trim());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }

    public List<Cliente> listar() {
        try {
            return dao.listar();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
    }

    
    private void validarCrearActualizar(Cliente c, boolean crear) {
        if (c == null) throw new IllegalArgumentException("Cliente nulo");
        if (isBlank(c.getRut()))       throw new IllegalArgumentException("RUT obligatorio");
        if (isBlank(c.getNombre()))    throw new IllegalArgumentException("Nombre obligatorio");
        if (isBlank(c.getDireccion())) throw new IllegalArgumentException("Dirección obligatoria");
        if (isBlank(c.getComuna()))    throw new IllegalArgumentException("Comuna obligatoria");
        if (isBlank(c.getCorreo()))    throw new IllegalArgumentException("Correo obligatorio");
        if (isBlank(c.getTelefono()))  throw new IllegalArgumentException("Teléfono obligatorio");
    }

    private boolean isBlank(String s) { return s == null || s.isBlank(); }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pipe-
 */
public class Inventario {
    private final Map<String, Producto> productos = new HashMap<>();

    public boolean agregarProducto(Producto p) {
        if (p == null) return false;
        String c = p.getCodigo();
        if (c == null || c.isBlank()) return false;
        if (productos.containsKey(c)) return false;
        productos.put(c, p);
        return true;
    }

    public boolean eliminarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return false;
        return productos.remove(codigo) != null;
    }

    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        return productos.get(codigo);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> res = new ArrayList<>();
        if (nombre == null || nombre.isBlank()) return res;
        String q = nombre.toLowerCase();
        for (Producto p : productos.values()) {
            if (p.getNombre() != null && p.getNombre().toLowerCase().contains(q)) res.add(p);
        }
        return res;
    }

    public boolean actualizarPrecio(String codigo, double nuevoPrecio) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) return false;
        p.actualizarPrecio(nuevoPrecio); // puede lanzar IllegalArgumentException
        return true;
    }

    public boolean ajustarStock(String codigo, int delta) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) return false;
        p.ajustarStock(delta); // puede lanzar IllegalArgumentException
        return true;
    }

    public List<Producto> listarTodos() { return new ArrayList<>(productos.values()); }

    public String generarInforme() {
        int cantidadItems = productos.size();
        int stockTotal = 0;
        double valorInventario = 0.0;
        for (Producto p : productos.values()) {
            stockTotal += p.getStock();
            valorInventario += p.getPrecio() * p.getStock();
        }
        return String.format("INFORME INVENTARIO -> items=%d | stockTotal=%d | valor=%.0f",
                cantidadItems, stockTotal, valorInventario);
    }
}

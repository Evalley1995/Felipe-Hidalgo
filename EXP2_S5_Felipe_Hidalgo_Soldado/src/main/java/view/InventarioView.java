/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.Producto;

/**
 *
 * @author pipe-
 */
public class InventarioView {
     private final ProductoView productoView = new ProductoView();

    public void mostrarResultadoOperacion(boolean ok, String mensaje) {
        System.out.println((ok ? "[OK] " : "[ERROR] ") + mensaje);
    }

    public void mostrarListado(List<Producto> lista) {
        productoView.listarProductos(lista);
    }

    public void mostrarProducto(Producto p) {
        productoView.mostrarProducto(p);
    }

    public void mostrarInforme(String informe) {
        System.out.println(informe);
    }
}

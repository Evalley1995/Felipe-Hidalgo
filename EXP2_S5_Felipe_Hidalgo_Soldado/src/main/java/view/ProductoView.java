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
public class ProductoView {
    public void mostrarProducto(Producto p) {
        if (p == null) { System.out.println("No encontrado"); return; }
        System.out.println(" - " + p.descripcionDetallada());
    }

    public void listarProductos(List<Producto> lista) {
        if (lista == null || lista.isEmpty()) { System.out.println("(sin resultados)"); return; }
        for (Producto p : lista) mostrarProducto(p);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.productocontroller;

import com.sumativa.component.Component;
import com.sumativa.producto.Producto;
import com.sumativa.productview.ProductView;

/**
 *
 * @author pipe-
 */
public class ProductoController {
    private final ProductView view;

    public ProductoController(ProductView view) {
        this.view = view;
    }

    public Component crearProducto(String tipo, int precioBase) {
        Component p = new Producto(tipo, precioBase);
        view.mostrarProducto(p);
        return p;
    }

    public void listar(java.util.List<Component> items) {
        view.listarProductos(items);
    }
}

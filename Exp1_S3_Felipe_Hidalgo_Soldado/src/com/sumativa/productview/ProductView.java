/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.productview;

import com.sumativa.component.Component;
import java.util.List;

/**
 *
 * @author pipe-
 */
public class ProductView {
   public void mostrarProducto(Component c) {
        System.out.println("------------------------------");
        System.out.println(" Producto agregado");
        System.out.println(" Tipo   : " + c.tipo());
        System.out.println(" Precio : $" + c.precio());
        System.out.println("------------------------------\n");
    }

    public void listarProductos(List<Component> items) {
        System.out.println("================================");
        System.out.println("        LISTA DE PRODUCTOS");
        System.out.println("================================");
        int i = 0;
        for (Component c : items) {
            System.out.printf(" [%d] %-30s = $%d%n", i++, c.detalle(), c.precio());
        }
        System.out.println("================================\n");
    }
}
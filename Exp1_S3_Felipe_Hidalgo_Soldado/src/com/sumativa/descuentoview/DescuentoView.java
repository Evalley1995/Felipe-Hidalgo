/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.descuentoview;

/**
 *
 * @author pipe-
 */
public class DescuentoView {
     public void aplicarPorcentaje(int index, int porcentaje, int antes, int despues) {
        System.out.println("Descuento aplicado al producto [" + index + "]");
        System.out.println("  Tipo      : Porcentaje (" + porcentaje + "%)");
        System.out.println("  Antes     : $" + antes);
        System.out.println("  Despues   : $" + despues);
        System.out.println("--------------------------------\n");
    }

    public void aplicarCategoria(int index, String categoria, int porcentaje, int antes, int despues) {
        System.out.println("  Descuento aplicado al producto [" + index + "]");
        System.out.println("  Tipo      : Categoria (" + categoria + ")");
        System.out.println("  Porcentaje: " + porcentaje + "%");
        System.out.println("  Antes     : $" + antes);
        System.out.println("  Despues   : $" + despues);
        System.out.println("--------------------------------\n");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.descuentocategoria;

import com.sumativa.component.Component;
import com.sumativa.decorator.Decorator;

/**
 *
 * @author pipe-
 */
public class DescuentoCategoria extends Decorator {
    private final String categoriaObjetivo; // p.ej. "chaqueta"
    private final int porcentaje;           // p.ej. 20

    public DescuentoCategoria(Component componente, String categoriaObjetivo, int porcentaje) {
        super(componente);
        this.categoriaObjetivo = categoriaObjetivo.toLowerCase();
        this.porcentaje = porcentaje;
    }

    @Override
    public int precio() {
        int p = componente.precio();
        if (componente.tipo().toLowerCase().equals(categoriaObjetivo)) {
            return p * (100 - porcentaje) / 100;
        }
        return p;
    }

    @Override
    public String detalle() {
        return componente.detalle()
                + " DescuentoCategoria(" + categoriaObjetivo + ", " + porcentaje + "%)";
    }
}

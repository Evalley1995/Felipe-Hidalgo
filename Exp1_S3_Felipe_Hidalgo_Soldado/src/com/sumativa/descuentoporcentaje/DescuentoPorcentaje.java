/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.descuentoporcentaje;

import com.sumativa.component.Component;
import com.sumativa.decorator.Decorator;

/**
 *
 * @author pipe-
 */
public class DescuentoPorcentaje extends Decorator {
    private final int porcentaje; // 0..100

    public DescuentoPorcentaje(Component componente, int porcentaje) {
        super(componente);
        this.porcentaje = porcentaje;
    }

    @Override
    public int precio() {
        int p = componente.precio();
        return p * (100 - porcentaje) / 100;
    }

    @Override
    public String detalle() {
        return componente.detalle() + " DescuentoPorcentaje(" + porcentaje + "%)";
    }
}


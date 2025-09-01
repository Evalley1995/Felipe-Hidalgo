/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.aplicardescuentocategoria;

import com.sumativa.carrito.Carrito;
import com.sumativa.command.Command;
import com.sumativa.component.Component;
import com.sumativa.descuentoporcentaje.DescuentoPorcentaje;

/**
 *
 * @author pipe-
 */
public class AplicarDescuentoCommand implements Command{
    private final Carrito carrito;
    private final int index;
    private final int porcentaje;

    public AplicarDescuentoCommand(Carrito carrito, int index, int porcentaje) {
        this.carrito = carrito;
        this.index = index;
        this.porcentaje = porcentaje;
    }

    public void Ejecutar() {
        Component original = carrito.get(index);
        Component decorado = new DescuentoPorcentaje(original, porcentaje);
        carrito.set(index, decorado);
    }

    @Override
    public void ejecutar() {
    }
}

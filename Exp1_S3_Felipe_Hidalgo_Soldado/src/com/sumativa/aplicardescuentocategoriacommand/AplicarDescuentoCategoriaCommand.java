/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.aplicardescuentocategoriacommand;

import com.sumativa.carrito.Carrito;
import com.sumativa.descuentocategoria.DescuentoCategoria;
import com.sumativa.command.Command;
import com.sumativa.component.Component;

/**
 *
 * @author pipe-
 */
public class AplicarDescuentoCategoriaCommand implements Command{
    private final Carrito carrito;
    private final int index;
    private final String categoria;
    private final int porcentaje;

    public AplicarDescuentoCategoriaCommand(Carrito carrito, int index, String categoria, int porcentaje) {
        this.carrito = carrito;
        this.index = index;
        this.categoria = categoria;
        this.porcentaje = porcentaje;
    }

    public void Ejecutar() {
        Component original = carrito.get(index);
        Component decorado = new DescuentoCategoria(original, categoria, porcentaje);
        carrito.set(index, decorado);
    }

    @Override
    public void ejecutar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

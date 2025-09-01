/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.descuentocontroller;

import com.sumativa.aplicardescuentocategoriacommand.AplicarDescuentoCategoriaCommand;
import com.sumativa.aplicardescuentocategoria.AplicarDescuentoCommand;
import com.sumativa.descuentoview.DescuentoView;
import com.sumativa.pedido.Pedido;

/**
 *
 * @author pipe-
 */
public class DescuentoController {
    private final Pedido pedido;
    private final DescuentoView view;

    public DescuentoController(Pedido pedido, DescuentoView view) {
        this.pedido = pedido;
        this.view = view;
    }

    public void aplicarPorcentaje(int index, int porcentaje) {
        int antes = pedido.get(index).precio();
        new AplicarDescuentoCommand(pedido.getCarrito(), index, porcentaje).Ejecutar();
        int despues = pedido.get(index).precio();
        view.aplicarPorcentaje(index, porcentaje, antes, despues);
    }

    public void aplicarCategoria(int index, String categoria, int porcentaje) {
        int antes = pedido.get(index).precio();
        new AplicarDescuentoCategoriaCommand(pedido.getCarrito(), index, categoria, porcentaje).Ejecutar();
        int despues = pedido.get(index).precio();
        view.aplicarCategoria(index, categoria, porcentaje, antes, despues);
    }
}

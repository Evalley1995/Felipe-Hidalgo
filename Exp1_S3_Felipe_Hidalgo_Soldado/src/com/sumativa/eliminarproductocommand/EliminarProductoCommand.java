/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.eliminarproductocommand;

import com.sumativa.carrito.Carrito;
import com.sumativa.command.Command;

/**
 *
 * @author pipe-
 */
public class EliminarProductoCommand implements Command{
    private final Carrito carrito;
    private final int index;

    public EliminarProductoCommand(Carrito carrito, int index) {
        this.carrito = carrito;
        this.index = index;
    }

    public void Ejecutar() { carrito.eliminar(index); }

    @Override
    public void ejecutar() {
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.agregarproductocommand;

import com.sumativa.carrito.Carrito;
import com.sumativa.command.Command;
import com.sumativa.component.Component;

/**
 *
 * @author pipe-
 */
public class AgregarProductoCommand implements Command{
     private final Carrito carrito;
    private final Component producto;

    public AgregarProductoCommand(Carrito carrito, Component producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    public void Ejecutar() { carrito.agregar(producto); }

    @Override
    public void ejecutar() {
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.pedidocontroller;

import com.sumativa.agregarproductocommand.AgregarProductoCommand;
import com.sumativa.component.Component;
import com.sumativa.pedido.Pedido;
import com.sumativa.pedidoview.PedidoView;
import com.sumativa.eliminarproductocommand.EliminarProductoCommand;

/**
 *
 * @author pipe-
 */
public class PedidoController {
    private final Pedido pedido;
    private final PedidoView view;

    public PedidoController(Pedido pedido, PedidoView view) {
        this.pedido = pedido;
        this.view = view;
    }

    public void agregarProducto(Component c) {
        new AgregarProductoCommand(pedido.getCarrito(), c).Ejecutar();
        view.mostrarPedido(pedido);
    }

    public void eliminarProducto(int index) {
        new EliminarProductoCommand(pedido.getCarrito(), index).Ejecutar();
        view.mostrarPedido(pedido);
    }

    public void mostrarPedido() {
        view.mostrarPedido(pedido);
    }
}


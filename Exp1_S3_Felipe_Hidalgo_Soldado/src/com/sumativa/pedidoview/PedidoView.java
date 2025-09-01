/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.pedidoview;

import com.sumativa.component.Component;
import com.sumativa.pedido.Pedido;

/**
 *
 * @author pipe-
 */
public class PedidoView {
  public void mostrarPedido(Pedido pedido) {
        System.out.println("\n================================");
        System.out.println("  PEDIDO #" + pedido.getId());
        System.out.println("  Cliente: " + pedido.getUsuario().getNombre());
        System.out.println("  Email  : " + pedido.getUsuario().getEmail());
        System.out.println("================================");
        int i = 0;
        for (Component c : pedido.verItems()) {
            System.out.printf(" [%d] %-30s = $%d%n", i++, c.detalle(), c.precio());
        }
        System.out.println("--------------------------------");
        System.out.println(" TOTAL A PAGAR: $" + pedido.total());
        System.out.println("================================\n");
    }
}

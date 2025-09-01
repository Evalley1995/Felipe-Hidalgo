/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.pedido;

import com.sumativa.carrito.Carrito;
import com.sumativa.component.Component;
import com.sumativa.usuario.Usuario;

/**
 *
 * @author pipe-
 */
public class Pedido {
      private final int id;
    private final Usuario usuario;
    private final Carrito carrito = new Carrito();

    public Pedido(int id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public void agregar(Component c) { carrito.agregar(c); }
    public void eliminar(int index) { carrito.eliminar(index); }
    public Component get(int index) { return carrito.get(index); }
    public void set(int index, Component c) { carrito.set(index, c); }
    public int size() { return carrito.size(); }
    public java.util.List<Component> verItems() { return carrito.verItems(); }
    public int total() { return carrito.total(); }

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Carrito getCarrito() { return carrito; }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.producto;

import com.sumativa.component.Component;
import com.sumativa.discountmanager.DiscountManager;

/**
 *
 * @author pipe-
 */
public class Producto implements Component {
   private final String tipo;
    private final int precioBase;

    public Producto(String tipo, int precioBase) {
        this.tipo = tipo;
        this.precioBase = precioBase;
    }

    @Override
    public int precio() {
        return DiscountManager.getInstance().applyDiscount(tipo, precioBase);
    }

    @Override
    public String tipo() { return tipo; }

    @Override
    public String detalle() {
        return "Producto(" + tipo + ", base=" + precioBase + ")";
    }
}

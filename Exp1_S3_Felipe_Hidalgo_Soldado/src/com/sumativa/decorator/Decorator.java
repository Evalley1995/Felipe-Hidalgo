/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.decorator;

import com.sumativa.component.Component;

/**
 *
 * @author pipe-
 */
public class Decorator implements Component {
     protected final Component componente;

    protected Decorator(Component componente) {
        this.componente = componente;
    }

    @Override
    public String tipo() { return componente.tipo(); }

    @Override
    public String detalle() { return componente.detalle(); }

    @Override
    public int precio() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

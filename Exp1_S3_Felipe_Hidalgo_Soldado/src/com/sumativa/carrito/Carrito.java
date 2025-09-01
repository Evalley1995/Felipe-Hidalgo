/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.carrito;

import com.sumativa.component.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pipe-
 */
public class Carrito {
   private final List<Component> items = new ArrayList<>();

    public void agregar(Component c) { items.add(c); }

    public void eliminar(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public Component get(int index) { return items.get(index); }

    public void set(int index, Component c) { items.set(index, c); }

    public int size() { return items.size(); }

    public List<Component> verItems() { return new ArrayList<>(items); }

    public int total() {
        int t = 0;
        for (Component c : items) t += c.precio();
        return t;
    }
}

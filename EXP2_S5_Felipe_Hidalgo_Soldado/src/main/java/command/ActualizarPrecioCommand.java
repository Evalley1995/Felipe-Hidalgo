/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import model.Inventario;
import view.InventarioView;

/**
 *
 * @author pipe-
 */
public class ActualizarPrecioCommand implements Command{
    private final Inventario inv; private final InventarioView view;
    private final String codigo; private final double nuevoPrecio;

    public ActualizarPrecioCommand(Inventario inv, InventarioView view, String codigo, double nuevoPrecio) {
        this.inv = inv; this.view = view; this.codigo = codigo; this.nuevoPrecio = nuevoPrecio;
    }

    @Override public void Ejecutar() {
        try {
            boolean ok = inv.actualizarPrecio(codigo, nuevoPrecio);
            if (ok) view.mostrarResultadoOperacion(true, "Precio actualizado para código " + codigo);
            else    view.mostrarResultadoOperacion(false, "No existe producto con código " + codigo);
        } catch (IllegalArgumentException ex) {
            view.mostrarResultadoOperacion(false, "No se pudo actualizar: " + ex.getMessage());
        }
    }
}

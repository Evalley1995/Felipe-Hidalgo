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
public class AjustarStockCommand implements Command{
    private final Inventario inv; private final InventarioView view;
    private final String codigo; private final int delta;

    public AjustarStockCommand(Inventario inv, InventarioView view, String codigo, int delta) {
        this.inv = inv; this.view = view; this.codigo = codigo; this.delta = delta;
    }

    @Override public void Ejecutar() {
        try {
            boolean ok = inv.ajustarStock(codigo, delta);
            if (ok) view.mostrarResultadoOperacion(true, "Stock ajustado (" + delta + ") para código " + codigo);
            else    view.mostrarResultadoOperacion(false, "No existe producto con código " + codigo);
        } catch (IllegalArgumentException ex) {
            view.mostrarResultadoOperacion(false, "No se pudo ajustar stock: " + ex.getMessage());
        }
    }
}

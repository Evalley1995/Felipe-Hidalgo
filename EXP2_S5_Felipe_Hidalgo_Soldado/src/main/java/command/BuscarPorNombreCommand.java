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
public class BuscarPorNombreCommand implements Command{
    private final Inventario inv; private final InventarioView view; private final String q;

    public BuscarPorNombreCommand(Inventario inv, InventarioView view, String q) {
        this.inv = inv; this.view = view; this.q = q;
    }

    @Override public void Ejecutar() {
        view.mostrarListado(inv.buscarPorNombre(q));
    }
}

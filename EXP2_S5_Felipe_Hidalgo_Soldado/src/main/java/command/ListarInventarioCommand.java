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
public class ListarInventarioCommand implements Command{
    private final Inventario inv; private final InventarioView view;

    public ListarInventarioCommand(Inventario inv, InventarioView view) {
        this.inv = inv; this.view = view;
    }

    @Override public void Ejecutar() {
        view.mostrarListado(inv.listarTodos());
    }
}

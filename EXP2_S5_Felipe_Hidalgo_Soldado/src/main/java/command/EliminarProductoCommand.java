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
public class EliminarProductoCommand implements Command{
    private final Inventario inv; private final InventarioView view; private final String codigo;

    public EliminarProductoCommand(Inventario inv, InventarioView view, String codigo) {
        this.inv = inv; this.view = view; this.codigo = codigo;
    }

    @Override public void Ejecutar() {
        boolean ok = inv.eliminarPorCodigo(codigo);
        if (ok) view.mostrarResultadoOperacion(true, "Producto eliminado (" + codigo + ")");
        else    view.mostrarResultadoOperacion(false, "Producto no existe: " + codigo);
    }
}

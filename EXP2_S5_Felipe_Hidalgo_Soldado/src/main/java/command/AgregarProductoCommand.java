/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import model.Inventario;
import model.Producto;
import view.InventarioView;

/**
 *
 * @author pipe-
 */
public class AgregarProductoCommand implements Command{
    private final Inventario inv; private final InventarioView view; private final Producto p;

    public AgregarProductoCommand(Inventario inv, InventarioView view, Producto p) {
        this.inv = inv; this.view = view; this.p = p;
    }

    @Override public void Ejecutar() {
        try {
            if (p == null) { view.mostrarResultadoOperacion(false, "Producto inválido (null)"); return; }
            boolean ok = inv.agregarProducto(p);
            if (ok) view.mostrarResultadoOperacion(true, "Producto agregado (" + p.getCodigo() + ")");
            else    view.mostrarResultadoOperacion(false, "No se pudo agregar: código vacío o duplicado");
        } catch (IllegalArgumentException ex) {
            view.mostrarResultadoOperacion(false, "No se pudo agregar: " + ex.getMessage());
        }
    }
}

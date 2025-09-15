/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.MenuView;

/**
 *
 * @author pipe-
 */
public class MenuController {
     private final MenuView menuView;
    private final InventarioController invController;

    public MenuController(MenuView menuView, InventarioController invController) {
        this.menuView = menuView; this.invController = invController;
    }

    public void run() {
        int op;
        do {
            menuView.mostrarMenu();
            op = menuView.leerOpcion();
            try {
                switch (op) {
                    case 1 -> {
                        String c = menuView.pedirTexto("Codigo");
                        String n = menuView.pedirTexto("Nombre");
                        String d = menuView.pedirTexto("Descripcion");
                        double pr = menuView.pedirDoubleNoNegativo("Precio");
                        int st    = menuView.pedirEnteroNoNegativo("Stock");
                        invController.agregarProducto(c,n,d,pr,st);
                    }
                    case 2 -> {
                        String c = menuView.pedirTexto("Codigo");
                        double pr = menuView.pedirDoubleNoNegativo("Nuevo precio");
                        invController.actualizarPrecio(c, pr);
                    }
                    case 3 -> {
                        String c = menuView.pedirTexto("Codigo");
                        int delta = menuView.pedirEntero("Delta (+/-)");
                        invController.ajustarStock(c, delta);
                    }
                    case 4 -> {
                        String c = menuView.pedirTexto("Codigo");
                        invController.buscarPorCodigo(c);
                    }
                    case 5 -> {
                        String q = menuView.pedirTexto("Nombre contiene");
                        invController.buscarPorNombre(q);
                    }
                    case 6 -> invController.listar();
                    case 7 -> invController.informe();
                    case 8 -> {
                        String c = menuView.pedirTexto("Codigo");
                        invController.eliminarPorCodigo(c);
                    }
                    case 0 -> menuView.despedir();
                    default -> menuView.invalida();
                }
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        } while (op != 0);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import controller.InventarioController;
import controller.MenuController;
import model.Inventario;
import view.InventarioView;
import view.MenuView;

/**
 *
 * @author pipe-
 */
public class Main {

    public static void main(String[] args) {
        Inventario modelo = new Inventario();
        InventarioView inventarioView = new InventarioView();
        InventarioController invController = new InventarioController(modelo, inventarioView);

        MenuView menuView = new MenuView();
        MenuController menu = new MenuController(menuView, invController);

        menu.run();
    }
    
}

package app;
import controller.PeliculaController;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author pipe-
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            var ctl = new PeliculaController();
            System.out.println("Conexión OK. Películas en BD: " + ctl.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    


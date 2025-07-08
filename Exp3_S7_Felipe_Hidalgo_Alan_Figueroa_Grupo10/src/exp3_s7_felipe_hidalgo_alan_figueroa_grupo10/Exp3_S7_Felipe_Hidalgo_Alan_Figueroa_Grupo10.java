/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s7_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
public class Exp3_S7_Felipe_Hidalgo_Alan_Figueroa_Grupo10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       PrimesList lista = new PrimesList();

        // Dos hilos: uno para primos del 2 al 20, otro del 21 al 40
        Thread hilo1 = new PrimeAdderThread(lista, 2, 20);
        Thread hilo2 = new PrimeAdderThread(lista, 21, 40);

        hilo1.start();
        hilo2.start();

        // Esperar que ambos terminen
        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            System.out.println("Error en los hilos.");
        }

        System.out.println("\nCantidad de primos total: " + lista.getPrimesCount());
        System.out.println("Lista final de primos: " + lista);
    }
    }
   

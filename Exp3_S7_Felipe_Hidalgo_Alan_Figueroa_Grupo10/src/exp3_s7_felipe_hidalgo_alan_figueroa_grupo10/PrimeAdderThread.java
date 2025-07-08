/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s7_felipe_hidalgo_alan_figueroa_grupo10;

/**
 *
 * @author pipe-
 */
public class PrimeAdderThread extends Thread{
    private PrimesList primesList;
    private int inicio, fin;

    public PrimeAdderThread(PrimesList primesList, int inicio, int fin) {
        this.primesList = primesList;
        this.inicio = inicio;
        this.fin = fin;
    }

    public void run() {
        for (int n = inicio; n <= fin; n++) {
            try {
                primesList.add(n);
                System.out.println("[" + this.getName() + "] agregó: " + n);
            } catch (IllegalArgumentException e) {
                // No es primo, no se agrega
            }
        }
    }
}

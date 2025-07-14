/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_felipe_hidalgo;

import com.sumativa.PrimeList.PrimesList;
import com.sumativa.consumidor.Consumidor;
import com.sumativa.primesThread.PrimesThread;
import com.sumativa.productor.Productor;
import com.sumativa.topicQueue.TopicQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pipe-
 */
public class Exp3_S8_Felipe_Hidalgo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           PrimesList primesList = new PrimesList();

        // 1. Cargar los parámetros primos base desde archivo
        try {
            cargarPrimosDesdeCSV(primesList, "primos.csv");

            // >>> SUGERENCIA: Imprime la lista apenas termina de cargar
            System.out.println("Lista después de cargar CSV:");
            for (int primo : primesList) {
                System.out.print(primo + " ");
            }
            System.out.println(); // Salto de línea para claridad

        } catch (Exception e) {
            System.out.println(" Error al cargar los parámetros primos desde archivo: " + e.getMessage());
        }

        // 2. Añadir parámetros dinámicos usando hilos (nuevos primos)
        int[] nuevosPrimos = {151, 157, 161, 163, 169};
        Thread[] primosThreads = new Thread[nuevosPrimos.length];
        for (int i = 0; i < nuevosPrimos.length; i++) {
            primosThreads[i] = new Thread(new PrimesThread(primesList, nuevosPrimos[i]), "Hilo-Primo-" + (i + 1));
            primosThreads[i].start();
        }
        for (Thread t : primosThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(" Error esperando hilo: " + t.getName());
            }
        }

        // 3. Productor/Consumidor para ingreso concurrente de parámetros
        TopicQueue topic = new TopicQueue();
        int[] parametrosPosibles = {173, 175, 179, 181, 183, 191, 193, 197, 199, 211};
        Thread productor = new Thread(new Productor(topic, parametrosPosibles), "Productor");
        Thread consumidor1 = new Thread(new Consumidor(topic, primesList), "Consumidor-1");
        Thread consumidor2 = new Thread(new Consumidor(topic, primesList), "Consumidor-2");

        productor.start();
        consumidor1.start();
        consumidor2.start();

        try {
            productor.join();
            consumidor1.join();
            consumidor2.join();
        } catch (InterruptedException e) {
            System.out.println(" Error esperando productor/consumidores: " + e.getMessage());
        }

        // 4. Mostrar parámetros primos activos en el sistema
        System.out.println("\n=== Parámetros primos activos en el sistema ===");
        for (int primo : primesList) {
            System.out.print(primo + " ");
        }
        System.out.println("\nTotal de parámetros primos: " + primesList.getPrimesCount());

        // 5. Guardar mensaje cifrado + código primo
        try {
            if (!primesList.isEmpty()) {
                guardarMensajeEncriptado("Voto seguro y anónimo para Elección 2025", primesList.get(0), "mensajes_encriptados.txt");
            }
        } catch (Exception e) {
            System.out.println(" Error al guardar mensaje cifrado: " + e.getMessage());
        }
    }


    public static void cargarPrimosDesdeCSV(PrimesList lista, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine();
            if (linea != null) {
                String[] numeros = linea.split(",");
                for (String numStr : numeros) {
                    int num = Integer.parseInt(numStr.trim());
                    lista.add(num);
                }
            }
            System.out.println(" Parámetros primos cargados desde archivo.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void guardarMensajeEncriptado(String mensaje, int codigoPrimo, String rutaArchivo) {
        try (FileWriter fw = new FileWriter(rutaArchivo, true)) {
            fw.write("Mensaje: " + mensaje + " | Código Primo: " + codigoPrimo + "\n");
            System.out.println(" Mensaje guardado en archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
    
}

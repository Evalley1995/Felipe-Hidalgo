/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.topicQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author pipe-
 */
public class TopicQueue {
   private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACIDAD_MAXIMA = 10;

    // Método para que el productor publique un número
    public synchronized void publicar(int numero) throws InterruptedException {
        while (queue.size() >= CAPACIDAD_MAXIMA) {
            wait();
        }
        queue.add(numero);
        System.out.println("[Topic] Publicado: " + numero);
        notifyAll();
    }

    // Método para que los consumidores saquen un número
    public synchronized int consumir() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int numero = queue.poll();
        System.out.println("[Topic] Consumido: " + numero);
        notifyAll();
        return numero;
    }
}

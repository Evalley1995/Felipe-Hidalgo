/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.productor;

import com.sumativa.topicQueue.TopicQueue;

/**
 *
 * @author pipe-
 */
public class Productor implements Runnable {
     private final TopicQueue topic;
    private final int[] datos;

    public Productor(TopicQueue topic, int[] datos) {
        this.topic = topic;
        this.datos = datos;
    }

    @Override
    public void run() {
        try {
            for (int numero : datos) {
                topic.publicar(numero);
                Thread.sleep(100); // simula tiempo entre publicaciones
            }
        } catch (InterruptedException e) {
            System.out.println("Productor interrumpido.");
        }
    }
}

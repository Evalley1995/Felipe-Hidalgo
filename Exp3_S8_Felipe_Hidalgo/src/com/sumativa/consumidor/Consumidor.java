/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.consumidor;


import com.sumativa.PrimeList.PrimesList;
import com.sumativa.topicQueue.TopicQueue;

/**
 *
 * @author pipe-
 */
public class Consumidor implements Runnable {
      private final TopicQueue topic;
    private final PrimesList primesList;

    public Consumidor(TopicQueue topic, PrimesList primesList) {
        this.topic = topic;
        this.primesList = primesList;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                int numero = topic.consumir();
                if (primesList.isPrime(numero)) {
                    primesList.add(numero);
                    System.out.println("[" + Thread.currentThread().getName() + "] agregó primo desde Topic: " + numero);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor interrumpido.");
        }
    }
}

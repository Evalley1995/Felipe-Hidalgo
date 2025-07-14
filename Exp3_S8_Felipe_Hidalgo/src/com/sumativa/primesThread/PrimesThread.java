/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.primesThread;

import com.sumativa.PrimeList.PrimesList;

/**
 *
 * @author pipe-
 */
public class PrimesThread implements Runnable {
    private final PrimesList primesList;
    private final int number;

    public PrimesThread(PrimesList primesList, int number) {
        this.primesList = primesList;
        this.number = number;
    }

    @Override
    public void run() {
        if (primesList.isPrime(number)) {
            try {
                primesList.add(number);
                System.out.println("[" + Thread.currentThread().getName() + "] agregó: " + number);
            } catch (IllegalArgumentException e) {
                System.out.println("[" + Thread.currentThread().getName() + "] no se pudo agregar: " + number);
            }
        } else {
            System.out.println("[" + Thread.currentThread().getName() + "] descartó: " + number);
        }
    }
}

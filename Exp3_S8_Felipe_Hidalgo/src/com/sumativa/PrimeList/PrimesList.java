/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sumativa.PrimeList;

import java.util.ArrayList;

/**
 *
 * @author pipe-
 */
public class PrimesList extends ArrayList<Integer> {
    // Método que verifica si un número es primo
    public boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    // Solo permite agregar números primos
    @Override
    public boolean add(Integer number) {
        if (!isPrime(number)) {
            throw new IllegalArgumentException("Solo se pueden agregar números primos.");
        }
        return super.add(number);
    }

    // Solo permite eliminar si es primo
    @Override
    public boolean remove(Object o) {
        if (o instanceof Integer && isPrime((Integer) o)) {
            return super.remove(o);
        } else {
            throw new IllegalArgumentException("Solo puedes eliminar números primos existentes.");
        }
    }

    // Cuenta los primos (todos los elementos son primos por diseño)
    public int getPrimesCount() {
        return this.size();
    }
}

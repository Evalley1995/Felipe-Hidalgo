/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s7_felipe_hidalgo_alan_figueroa_grupo10;

import java.util.ArrayList;

/**
 *
 * @author pipe-
 */
public class PrimesList extends ArrayList<Integer> {
      public boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2)
            if (n % i == 0) return false;
        return true;
    }

    @Override
    public boolean add(Integer n) {
        if (!isPrime(n))
            throw new IllegalArgumentException("Solo se pueden agregar números primos. " + n + " no es primo.");
        return super.add(n);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer) || !isPrime((Integer) o))
            throw new IllegalArgumentException("Solo se pueden eliminar números primos. " + o + " no es primo.");
        return super.remove(o);
    }

    public int getPrimesCount() {
        return this.size();
    }}

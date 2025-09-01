/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.sumativa.discountmanager;

/**
 *
 * @author pipe-
 */
public class DiscountManager {
    private static DiscountManager instance;

    private DiscountManager() { }

    public static DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }

    public int applyDiscount(String clothingType, int price) {
        int finalPrice = price;
        switch (clothingType.toLowerCase()) {
            case "camisa":
                finalPrice = price * 90 / 100; // 10%
                break;
            case "pantalon":
                finalPrice = price * 85 / 100; // 15%
                break;
            case "chaqueta":
                finalPrice = price * 80 / 100; // 20%
                break;
            default:
                finalPrice = price;            // sin descuento
                break;
        }
        return finalPrice;
    }
}

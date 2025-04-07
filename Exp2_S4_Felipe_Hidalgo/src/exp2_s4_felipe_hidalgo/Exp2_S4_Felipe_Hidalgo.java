/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s4_felipe_hidalgo;
import java.util.Scanner;
/**
 *
 * @author pipe-
 */
public class Exp2_S4_Felipe_Hidalgo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        final int Precio_A = 5000;
        final int Precio_B = 4000;
        final int Precio_C = 3000;
        
    
        for (int i = 0; i < 100; i++) { // Utilizar FOR para mantener el menú activo
            System.out.println("Bienvenidos a SOlTicket. ");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.println("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            
            if (opcion == 1) {
                // Mostrar el teratro por zonas y precioa
                System.out.println("Zonas disponibles. ");
                System.out.println("Zona A: $5000");
                System.out.println("Zona B: $4000");
                System.out.println("Zona C: $3000");
                
                // Seleccionar zona
                System.out.println("Seleccione zona (A,B,C): ");
                String zona = scanner.next().toUpperCase();
                
                int precioBase = 0;
                if (zona.equals("A")) {
                    precioBase = Precio_A;
                } else if (zona.equals("B")) {
                    precioBase = Precio_B;
                } else if (zona.equals("C")) {
                    precioBase = Precio_C;
                } else {
                    System.out.println("Zona inválida. Intente nuevamente. ");
                    continue; // Volver al menú
                }
                
                // Pedir eddad
                System.out.println("Ingrese su edad: ");
                int edad = scanner.nextInt();
                
                double descuento = 0;
                if (edad <= 25) {
                    descuento = 0.10; // DEscuento estudiante
                } else if (edad >= 60) {
                    descuento = 0.15; // Descuento ADulto mayor
                }
                
                // CAlcular precio total con ciclo Do While
                double precioFinal;
                int intentos = 0;
                do {
                    precioFinal = precioBase - (precioBase * descuento);
                    intentos++;
                } while (intentos > 1);
                
                //MOstrar resumen de la compra
                System.out.println("Resumen de la compra. ");
                System.out.println("Zona elegida: " + zona);
                System.out.println("Precio base: $" + precioBase);
                System.out.println("Descuento aplicado: " + (int) (descuento * 100) + "%");
                System.out.println("Precio total a pagar: $" + (int)precioFinal);
                
            } else if (opcion == 2) {
                System.out.println("Gracias por visitar Solticket. Hasta pronto. ");
                break;
            } else {
                System.out.println("Opción no válida. Intente nuevamente. ");
            }
        }
        
        scanner.close();
    
   
    
    }
    
}

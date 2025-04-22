/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp2_s6_felipe_hidalgo_soldado;

/**
 *
 * @author pipe-
 */
public class Boleta {

String cliente;
    String ubicacion;
    int cantidad;
    double precioUnitario;

    public Boleta(String cliente, String ubicacion, int cantidad, double precioUnitario) {
        this.cliente = cliente;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public void imprimir() {
        int ancho = 60;
        String total = String.format("$%.2f", precioUnitario * cantidad);
        String linea1 = String.format(" Cliente: %-45s", cliente);
        String linea2 = String.format(" Ubicación: %-43s", ubicacion);
        String linea3 = String.format(" Cantidad: %-44d", cantidad);
        String linea4 = String.format(" Precio por entrada: $%-30.2f", precioUnitario);
        String linea5 = String.format(" Total pagado: %-36s", total);

        // Línea superior
        dibujarLinea(ancho);

        // Contenido centrado
        System.out.println("==                                                           ==");
        System.out.println("==" + linea1 + "==");
        System.out.println("==" + linea2 + "==");
        System.out.println("==" + linea3 + "==");
        System.out.println("==" + linea4 + "==");
        System.out.println("==" + linea5 + "==");
        System.out.println("==                                                           ==");

        // Línea inferior
        dibujarLinea(ancho);
    }

    public static void dibujarLinea(int ancho) {
        for (int i = 0; i < ancho / 2; i++) {
            System.out.print("==");
        }
        System.out.println();
    }
}

    


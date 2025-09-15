/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pipe-
 */
public class Producto {
     private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Producto(String codigo, String nombre, String descripcion, double precio, int stock) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código no puede estar vacío");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre no puede estar vacío");
        if (precio < 0) throw new IllegalArgumentException("Precio inválido: no puede ser negativo");
        if (stock < 0) throw new IllegalArgumentException("Stock inválido: no puede ser negativo");
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.descripcion = (descripcion == null ? "" : descripcion.trim());
        this.precio = precio;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre no puede estar vacío");
        this.nombre = nombre.trim();
    }
    public void setDescripcion(String descripcion) { this.descripcion = (descripcion == null ? "" : descripcion.trim()); }
    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("Precio inválido: no puede ser negativo");
        this.precio = precio;
    }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock inválido: no puede ser negativo");
        this.stock = stock;
    }

    public void actualizarPrecio(double nuevoPrecio) {
        if (nuevoPrecio < 0) throw new IllegalArgumentException("Precio inválido: no puede ser negativo");
        this.precio = nuevoPrecio;
    }

    public void ajustarStock(int delta) {
        int nuevo = this.stock + delta;
        if (nuevo < 0) throw new IllegalArgumentException("Operación inválida: stock no puede quedar negativo");
        this.stock = nuevo;
    }

    public String descripcionDetallada() {
        return String.format("(%s) %s - %s | $%.0f | stock=%d",
                codigo, nombre, (descripcion.isBlank() ? "-" : descripcion), precio, stock);
    }
}

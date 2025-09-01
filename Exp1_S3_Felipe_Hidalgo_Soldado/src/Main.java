
import com.sumativa.component.Component;
import com.sumativa.descuentocontroller.DescuentoController;
import com.sumativa.discountmanager.DiscountManager;
import com.sumativa.descuentoview.DescuentoView;
import com.sumativa.pedido.Pedido;
import com.sumativa.pedidocontroller.PedidoController;
import com.sumativa.pedidoview.PedidoView;
import com.sumativa.productocontroller.ProductoController;
import com.sumativa.productview.ProductView;
import com.sumativa.usuario.Usuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pipe-
 */
public class Main {
    public static void main(String[] args) {
        
    DiscountManager discountManager = DiscountManager.getInstance();

        int precioCamisa = 20000;
        int precioConDescuento = discountManager.applyDiscount("camisa", precioCamisa);

        int precioPantalon = 30000;
        int precioPantalonConDescuento = discountManager.applyDiscount("pantalon", precioPantalon);

        int precioChaqueta = 150000;
        int precioChaquetaConDescuento = discountManager.applyDiscount("chaqueta", precioChaqueta);

        System.out.println("Precio original de la camisa: $" + precioCamisa);
        System.out.println("Precio con descuento aplicado: $" + precioConDescuento);
        System.out.println("Precio original del pantalón: $" + precioPantalon);
        System.out.println("Precio con descuento aplicado $" + precioPantalonConDescuento);
        System.out.println("Precio original de la chaqueta $" + precioChaqueta);
        System.out.println("precio con descuento aplicado $" + precioChaquetaConDescuento);

        // ====== MVC + Patrones (int) ======
        Usuario usuario = new Usuario("Felipe", "felipe@example.com");
        Pedido pedido = new Pedido(1, usuario);

        ProductView productView = new ProductView();
        PedidoView pedidoView = new PedidoView();
        DescuentoView descuentoView = new DescuentoView();

        ProductoController productoCtrl = new ProductoController(productView);
        PedidoController pedidoCtrl = new PedidoController(pedido, pedidoView);
        DescuentoController descuentoCtrl = new DescuentoController(pedido, descuentoView);

        Component camisa   = productoCtrl.crearProducto("camisa", 20000);
        Component pantalon = productoCtrl.crearProducto("pantalon", 30000);
        Component chaqueta = productoCtrl.crearProducto("chaqueta", 150000);

        pedidoCtrl.agregarProducto(camisa);
        pedidoCtrl.agregarProducto(pantalon);
        pedidoCtrl.agregarProducto(chaqueta);

        descuentoCtrl.aplicarPorcentaje(0, 10);            // 10% extra a la camisa
        descuentoCtrl.aplicarCategoria(2, "chaqueta", 20); // 20% extra SOLO a chaquetas

        pedidoCtrl.mostrarPedido();
    }
}
package es.uam.eps.adsof.ventas;

import es.uam.eps.adsof.modelo.*;
import java.util.*;

/**
 * Clase que gestiona las ventas realizadas en el sistema.
 * Contiene una lista de ventas y permite registrar la compra de productos.
 */
public class GestorVenta {
    private List<Venta> ventas;

    /**
     * Constructor por defecto. Inicializa la lista de ventas como vacía.
     */
    public GestorVenta() {
        this.ventas = new ArrayList<>();
    }

    /**
     * Obtiene la lista de ventas registradas.
     * 
     * @return lista de objetos {@link Venta}.
     */
    public List<Venta> getVentas() {
        return ventas;
    }
    
    /**
     * Registra la compra de un producto por un comprador. 
     * Si el comprador intenta comprar su propio producto, lanza una excepción.
     * 
     * @param comprador el usuario que desea comprar el producto.
     * @param producto el producto que se desea comprar.
     * @throws IllegalArgumentException si el comprador es también el vendedor del producto.
     */
    public void comprarProducto(Usuario comprador, Producto producto) {
        if (producto.getVendedor().equals(comprador)) {
            throw new IllegalArgumentException("No puedes comprar tu propio producto.");
        }
        Venta nuevaVenta = new Venta(comprador, producto.getVendedor(), producto);
        ventas.add(nuevaVenta);
        producto.setEstado(EstadoProducto.VENDIDO);
        producto.getVendedor().eliminarProductoEnVenta(producto);
    }
}
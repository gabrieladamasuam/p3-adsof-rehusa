package es.uam.eps.adsof.modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa una venta de un producto entre un comprador y un vendedor.
 * La venta tiene una fecha y se asocia a un producto específico.
 */
public class Venta {
    private final Usuario comprador;
    private final Usuario vendedor;
    private final Producto producto;
    private LocalDateTime fecha;

    /**
     * Constructor de la clase Venta.
     * 
     * @param comprador Usuario que compra el producto.
     * @param vendedor Usuario que vende el producto.
     * @param producto Producto que se vende.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo o si el comprador y el vendedor son la misma persona.
     */
    public Venta(Usuario comprador, Usuario vendedor, Producto producto) {
        if (comprador == null || vendedor == null || producto == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos.");
        }
        if (comprador.equals(vendedor)) {
            throw new IllegalArgumentException("El comprador y el vendedor no pueden ser la misma persona.");
        }
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.producto = producto;
        this.fecha = LocalDateTime.now();
    }

    /**
     * Obtiene el comprador de la venta.
     * 
     * @return Usuario El comprador.
     */
    public Usuario getComprador() {
        return comprador;
    }

    /**
     * Obtiene el vendedor de la venta.
     * 
     * @return Usuario El vendedor.
     */
    public Usuario getVendedor() {
        return vendedor;
    }

    /**
     * Obtiene el producto asociado a la venta.
     * 
     * @return Producto El producto vendido.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la fecha de la venta.
     * 
     * @return LocalDateTime La fecha de la venta.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece una nueva fecha para la venta.
     * 
     * @param fecha Nueva fecha de la venta.
     * @throws IllegalArgumentException Si la fecha es nula.
     */
    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        this.fecha = fecha;
    }

    /**
     * Representa la venta como una cadena de texto.
     * Incluye información sobre el producto, el comprador, el vendedor, el precio y la fecha.
     * 
     * @return String Representación de la venta.
     */
    @Override
    public String toString() {
        return "Venta de: " + producto.getTitulo() + "\n"
             + "Comprador: " + comprador.getNombre() + "\n"
             + "Vendedor: " + vendedor.getNombre() + "\n"
             + "Precio: " + producto.getPrecio() + "€\n"
             + "Fecha: " + fecha;
    }
}
package es.uam.eps.adsof.modelo;

import es.uam.eps.adsof.observer.ProductoObserver;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase que representa un producto en el sistema.
 * Un producto tiene un título, una descripción, un vendedor, un precio y un estado.
 * Además, permite notificar a los observadores sobre cambios en el precio.
 */
public class Producto {
    private String titulo;
    private String descripcion;
    private final Usuario vendedor;
    private double precio;
    private EstadoProducto estado;
    private final LocalDateTime fechaPublicacion;
    private final Set<ProductoObserver> observadores = new HashSet<>();

    /**
     * Constructor de la clase Producto.
     * 
     * @param titulo Título del producto.
     * @param descripcion Descripción del producto.
     * @param vendedor Usuario que vende el producto.
     * @param precio Precio inicial del producto.
     * @throws IllegalArgumentException Si alguno de los parámetros es inválido.
     */
    public Producto(String titulo, String descripcion, Usuario vendedor, double precio) {
        validarTitulo(titulo);
        validarDescripcion(descripcion);
        validarPrecio(precio);
        if (vendedor == null) throw new IllegalArgumentException("El vendedor no puede ser nulo.");
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.vendedor = vendedor;
        this.precio = precio;
        this.estado = EstadoProducto.EN_VENTA;
        this.fechaPublicacion = LocalDateTime.now();
    }

    /**
     * Obtiene el título del producto.
     * 
     * @return String El título del producto.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece un nuevo título para el producto.
     * 
     * @param titulo Nuevo título del producto.
     * @throws IllegalArgumentException Si el título es inválido.
     */
    public void setTitulo(String titulo) {
        validarTitulo(titulo);
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción del producto.
     * 
     * @return String La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una nueva descripción para el producto.
     * 
     * @param descripcion Nueva descripción del producto.
     * @throws IllegalArgumentException Si la descripción es inválida.
     */
    public void setDescripcion(String descripcion) {
        validarDescripcion(descripcion);
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el vendedor del producto.
     * 
     * @return Usuario El vendedor del producto.
     */
    public Usuario getVendedor() {
        return vendedor;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return double El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Cambia el precio del producto y notifica a los observadores si hay un cambio.
     * 
     * @param nuevoPrecio Nuevo precio del producto.
     * @throws IllegalArgumentException Si el precio es inválido o el producto ya ha sido vendido.
     */
    public void setPrecio(double nuevoPrecio) {
        if (estado == EstadoProducto.VENDIDO)
            throw new IllegalArgumentException("No se puede cambiar el precio de un producto vendido.");
        validarPrecio(nuevoPrecio);
        if (this.precio != nuevoPrecio) {
            double anterior = this.precio;
            this.precio = nuevoPrecio;
            notificarCambioPrecio(anterior, nuevoPrecio);
        }
    }

    /**
     * Obtiene el estado actual del producto.
     * 
     * @return EstadoProducto El estado del producto.
     */
    public EstadoProducto getEstado() {
        return estado;
    }

    /**
     * Cambia el estado del producto.
     * 
     * @param nuevoEstado Nuevo estado del producto.
     * @throws IllegalArgumentException Si el estado es inválido.
     */
    public void setEstado(EstadoProducto nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        if (this.estado == EstadoProducto.VENDIDO && nuevoEstado == EstadoProducto.EN_VENTA) {
            throw new IllegalArgumentException("No se puede cambiar el estado de VENDIDO a EN_VENTA.");
        }
        this.estado = nuevoEstado;
    }

    /**
     * Obtiene la fecha de publicación del producto.
     * 
     * @return LocalDateTime La fecha de publicación.
     */
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Agrega un observador al producto.
     * 
     * @param o Observador a agregar.
     */
    public void addObserver(ProductoObserver o) {
        if (o != null) observadores.add(o);
    }

    /**
     * Elimina un observador del producto.
     * 
     * @param o Observador a eliminar.
     */
    public void removeObserver(ProductoObserver o) {
        observadores.remove(o);
    }

    /**
     * Notifica a los observadores sobre un cambio en el precio del producto.
     * 
     * @param anterior Precio anterior.
     * @param nuevo Nuevo precio.
     */
    private void notificarCambioPrecio(double anterior, double nuevo) {
        for (ProductoObserver obs : observadores) {
            obs.onPrecioCambiado(this, anterior, nuevo);
        }
    }

    /**
     * Representa el producto como una cadena de texto.
     * 
     * @return String Representación del producto.
     */
    @Override
    public String toString() {
        return "Título: " + titulo + "\n"
             + "Descripción: " + descripcion + "\n"
             + "Precio: " + String.format("%.2f", precio) + "€\n"
             + "Vendedor: " + vendedor.getNombre() + "\n"
             + "Estado: " + estado;
    }

    /**
     * Compara si dos productos son iguales basándose en el título, descripción y vendedor.
     * 
     * @param obj Objeto a comparar.
     * @return boolean True si los productos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Producto producto = (Producto) obj;
        return Objects.equals(titulo, producto.titulo) &&
               Objects.equals(descripcion, producto.descripcion) &&
               Objects.equals(vendedor, producto.vendedor);
    }

    /**
     * Calcula el hash code del producto.
     * 
     * @return int Hash code del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(titulo, descripcion, vendedor);
    }

    // Métodos de validación

    /**
     * Valida el título del producto.
     * 
     * @param titulo Título a validar.
     * @throws IllegalArgumentException Si el título es nulo, vacío o excede los 100 caracteres.
     */
    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        }
        if (titulo.length() > 100) {
            throw new IllegalArgumentException("El título no puede exceder los 100 caracteres.");
        }
    }

    /**
     * Valida la descripción del producto.
     * 
     * @param descripcion Descripción a validar.
     * @throws IllegalArgumentException Si la descripción es nula, vacía o excede los 500 caracteres.
     */
    private void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }
        if (descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede exceder los 500 caracteres.");
        }
    }

    /**
     * Valida el precio del producto.
     * 
     * @param precio Precio a validar.
     * @throws IllegalArgumentException Si el precio es menor o igual a cero o excede 1,000,000 €.
     */
    private void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        if (precio > 1000000) {
            throw new IllegalArgumentException("El precio no puede exceder 1000000 €.");
        }
    }
}
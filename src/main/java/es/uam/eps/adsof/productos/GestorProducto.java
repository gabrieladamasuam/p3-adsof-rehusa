package es.uam.eps.adsof.productos;

import java.util.*;

import es.uam.eps.adsof.modelo.*;

/**
 * Clase GestorProducto que gestiona los productos en el sistema.
 * Permite agregar, modificar y eliminar productos, así como buscar productos por título o vendedor.
 */
public class GestorProducto {
    private final List<Producto> productos;

    /**
     * Constructor de la clase GestorProducto.
     * Inicializa la lista de productos como una lista vacía.
     */
    public GestorProducto() {
        this.productos = new ArrayList<>();
    }

    /**
     * Obtiene una copia inmutable de la lista de productos registrados.
     * 
     * @return List<Producto> Lista inmutable de productos.
     */
    public List<Producto> getProductos() {
        return List.copyOf(productos);
    }

    /**
     * Agrega un producto a la lista de productos registrados.
     * 
     * @param producto Producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    
    /**
     * Publica un nuevo producto en venta.
     * 
     * @param vendedor Usuario que vende el producto.
     * @param titulo Título del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     * @throws IllegalArgumentException Si el vendedor ya tiene un producto similar en venta.
     */
    public void ponerProductoEnVenta(Usuario vendedor, String titulo, String descripcion, double precio) {
        Producto nuevoProducto = new Producto(titulo, descripcion, vendedor, precio);
        if (vendedor.getProductosEnVenta().contains(nuevoProducto)) {
            throw new IllegalArgumentException("Ya tienes un producto similar en venta.");
        }
        productos.add(nuevoProducto);
        vendedor.agregarProductoEnVenta(nuevoProducto);
    }

    /**
     * Cambia el precio de un producto registrado.
     * 
     * @param producto Producto cuyo precio se desea cambiar.
     * @param nuevoPrecio Nuevo precio del producto.
     * @throws IllegalArgumentException Si el producto no está registrado en el sistema.
     */
    public void cambiarPrecioProducto(Producto producto, double nuevoPrecio) {
        if (!productos.contains(producto)) {
            throw new IllegalArgumentException("El producto no está registrado en el sistema.");
        }
        producto.setPrecio(nuevoPrecio);
    }

    /**
     * Retira un producto de la venta.
     * 
     * @param vendedor Usuario que vende el producto.
     * @param producto Producto a retirar de la venta.
     * @throws IllegalArgumentException Si el producto no pertenece al vendedor.
     */
    public void dejarDeVenderProducto(Usuario vendedor, Producto producto) {
        if (!producto.getVendedor().equals(vendedor)) {
            throw new IllegalArgumentException("No puedes retirar un producto que no te pertenece.");
        }
        producto.setEstado(EstadoProducto.RETIRADO);
        productos.remove(producto);
        vendedor.eliminarProductoEnVenta(producto);
    }

    /**
     * Obtiene el catálogo general de productos disponibles para la venta.
     * 
     * @return List<Producto> Lista de productos en venta.
     */
    public List<Producto> obtenerCatalogoGeneral() {
        List<Producto> catalogo = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getEstado() == EstadoProducto.EN_VENTA) {
                catalogo.add(producto);
            }
        }
        return catalogo;
    }

    /**
     * Busca productos por título.
     * 
     * @param titulo Título o parte del título a buscar.
     * @return List<Producto> Lista de productos que coinciden con el título.
     * @throws IllegalArgumentException Si el título es nulo o vacío.
     */
    public List<Producto> buscarProductosPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        }
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    /**
     * Busca productos por vendedor.
     * 
     * @param vendedor Usuario vendedor cuyos productos se desean buscar.
     * @return List<Producto> Lista de productos del vendedor.
     * @throws IllegalArgumentException Si el vendedor es nulo.
     */
    public List<Producto> buscarProductosPorVendedor(Usuario vendedor) {
        if (vendedor == null) {
            throw new IllegalArgumentException("El vendedor no puede ser nulo.");
        }
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getVendedor().equals(vendedor)) {
                resultados.add(producto);
            }
        }
        return resultados;
    }
}
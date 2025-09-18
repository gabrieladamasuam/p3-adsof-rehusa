package es.uam.eps.adsof.observer;

import es.uam.eps.adsof.modelo.Producto;

/**
 * Interfaz del patrón Observer para observar cambios en productos.
 * Se utiliza para notificar a los usuarios cuando un producto cambia de precio.
 */
public interface ProductoObserver {
    /**
     * Notifica que el precio del producto ha cambiado.
     *
     * @param producto El producto modificado.
     * @param precioAnterior El precio anterior del producto.
     * @param precioNuevo El nuevo precio del producto.
     */
    void onPrecioCambiado(Producto producto, double precioAnterior, double precioNuevo);
}
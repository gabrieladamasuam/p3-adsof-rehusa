package es.uam.eps.adsof.modelo;

/**
 * Enumeración que representa los posibles estados de un producto
 * dentro del sistema.
 */
public enum EstadoProducto {
    /**
     * El producto está disponible para la venta.
     */
    EN_VENTA,

    /**
     * El producto ha sido reservado por un usuario.
     */
    RESERVADO,

    /**
     * El producto ha sido vendido.
     */
    VENDIDO,

    /**
     * El producto ha sido enviado al comprador.
     */
    ENVIADO,

    /**
     * El producto está en reparto hacia el comprador.
     */
    EN_REPARTO,

    /**
     * El producto está en proceso de devolución.
     */
    EN_DEVOLUCION,

    /**
     * El producto ha sido devuelto al vendedor.
     */
    DEVUELTO,

    /**
     * El producto ha sido retirado de la venta.
     */
    RETIRADO
}
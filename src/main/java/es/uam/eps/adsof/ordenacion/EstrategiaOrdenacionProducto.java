package es.uam.eps.adsof.ordenacion;

import es.uam.eps.adsof.modelo.Producto;
import java.util.List;

/**
 * Interfaz para definir estrategias de ordenación de productos.
 * Se utiliza en el patrón Strategy para aplicar distintos criterios de ordenación.
 */
public interface EstrategiaOrdenacionProducto {
    /**
     * Ordena una lista de productos según un criterio específico.
     *
     * @param productos Lista de productos a ordenar.
     * @return Lista de productos ordenada.
     */
    List<Producto> ordenar(List<Producto> productos);
}
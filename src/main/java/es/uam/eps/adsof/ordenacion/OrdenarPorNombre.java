package es.uam.eps.adsof.ordenacion;

import es.uam.eps.adsof.modelo.Producto;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Estrategia de ordenación de productos por nombre.
 * Esta clase implementa la interfaz EstrategiaOrdenacionProducto y proporciona
 * un método para ordenar una lista de productos en función de su nombre de publicación,
 * en orden alfabético.
 */
public class OrdenarPorNombre implements EstrategiaOrdenacionProducto {
    @Override
    public List<Producto> ordenar(List<Producto> productos) {
        return productos.stream()
                .sorted(Comparator.comparing(Producto::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
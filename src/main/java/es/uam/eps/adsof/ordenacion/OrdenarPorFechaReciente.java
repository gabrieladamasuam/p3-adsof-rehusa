package es.uam.eps.adsof.ordenacion;

import es.uam.eps.adsof.modelo.Producto;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Estrategia de ordenación de productos por fecha de publicación reciente.
 * Esta clase implementa la interfaz EstrategiaOrdenacionProducto y proporciona
 * un método para ordenar una lista de productos en función de su fecha de publicación,
 * de más reciente a más antigua.
 */
public class OrdenarPorFechaReciente implements EstrategiaOrdenacionProducto {
    @Override
    public List<Producto> ordenar(List<Producto> productos) {
        return productos.stream()
                .sorted(Comparator.comparing(Producto::getFechaPublicacion).reversed())
                .collect(Collectors.toList());
    }
}
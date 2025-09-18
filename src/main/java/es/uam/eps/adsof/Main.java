package es.uam.eps.adsof;

import es.uam.eps.adsof.persistencia.GestorPersistencia;
import es.uam.eps.adsof.controlador.Controlador;
import es.uam.eps.adsof.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        GestorPersistencia persistencia = new GestorPersistencia();
        Controlador controlador = new Controlador();

        try {
            // 1. CARGAR DATOS si existen
            if (persistencia.existenArchivos()) {
                persistencia.cargarDatos(controlador);
            }

            // 2. Ejecutar menú
            MenuPrincipal menuPrincipal = new MenuPrincipal(controlador);
            menuPrincipal.mostrarMenu();
        } catch (Exception e) {
            System.err.println("Error: " + e.getClass().getName());
            e.printStackTrace();
        }
         finally {
            try {
                // 3. GUARDAR DATOS al salir
                persistencia.guardarDatos(
                    controlador.getGestorUsuario().getUsuarios(),
                    controlador.getGestorProducto().getProductos(),
                    controlador.getGestorVenta().getVentas(),
                    controlador.getGestorChat().getChats()
                );
            } catch (Exception e) {
                System.err.println("Error al guardar datos: " + e.getMessage());
            }
        }
    }
}

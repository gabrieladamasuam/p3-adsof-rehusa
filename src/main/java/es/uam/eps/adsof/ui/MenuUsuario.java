package es.uam.eps.adsof.ui;

import es.uam.eps.adsof.controlador.Controlador;
import es.uam.eps.adsof.modelo.*;
import es.uam.eps.adsof.ordenacion.*;
import java.util.*;

/**
 * Clase que representa el menú de usuario en la aplicación.
 * Permite al usuario interactuar con el sistema para gestionar productos, chats y favoritos.
 */
public class MenuUsuario {
    private final Controlador controlador;
    private final Consola consola;

    /**
     * Constructor de la clase MenuUsuario.
     * 
     * @param controlador Controlador de la aplicación para gestionar las operaciones.
     * @param consola Consola para interactuar con el usuario.
     */
    public MenuUsuario(Controlador controlador, Consola consola) {
        this.controlador = controlador;
        this.consola = consola;
    }

    /**
     * Muestra el menú de usuario y gestiona las opciones seleccionadas.
     */
    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            consola.mostrarMenuUsuario();
            int opcion = consola.leerInt();
            try {
                switch (opcion) {
                    case 1 -> ponerProductoEnVenta();
                    case 2 -> cambiarPrecioProducto();
                    case 3 -> verCatalogo();
                    case 4 -> agregarAFavoritos();
                    case 5 -> comprarProducto();
                    case 6 -> dejarDeVenderProducto();
                    case 7 -> iniciarChat();
                    case 8 -> agregarMensajeEnChat();
                    case 9 -> salir = true;
                    default -> consola.mostrarError("Opción no válida.");
                }
            } catch (Exception e) {
                consola.mostrarError(e.getMessage());
            }
        }
    }

    /**
     * Muestra una lista de productos con opciones numeradas.
     * 
     * @param productos Lista de productos a mostrar.
     */
    private void mostrarOpcionesDeProductos(List<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i).getTitulo());
        }
    }

    /**
     * Permite al usuario poner un producto en venta.
     */
    private void ponerProductoEnVenta() {
        System.out.println("\u001B[1m=== Añadir nuevo producto ===\u001B[0m");
        String titulo = consola.leerTexto("Título: ");
        String descripcion = consola.leerTexto("Descripción: ");
        double precio = consola.leerDouble("Precio: ");
        controlador.ponerProductoEnVenta(titulo, descripcion, precio);
        consola.mostrarMensaje("Producto añadido con éxito.");
    }

    /**
     * Permite al usuario cambiar el precio de un producto en venta.
     */
    private void cambiarPrecioProducto() {
        System.out.println("\u001B[1m=== Cambiar precio de un producto ===\u001B[0m");
        List<Producto> productos = controlador.getUsuarioActual().getProductosEnVenta();
        if (productos.isEmpty()) {
            consola.mostrarMensaje("No tienes productos en venta.");
            return;
        }
        mostrarOpcionesDeProductos(productos);
        int opcion = consola.leerInt();
        Producto seleccionado = productos.get(opcion - 1);
        double nuevoPrecio = consola.leerDouble("Nuevo precio: ");
        controlador.cambiarPrecio(seleccionado, nuevoPrecio);
        consola.mostrarMensaje("Precio cambiado con éxito.");
    }

    /**
     * Muestra el catálogo de productos disponibles para la venta.
     * Permite al usuario ordenar el catálogo según diferentes criterios.
     */
    private void verCatalogo() {
        List<Producto> catalogo = controlador.verCatalogo();
        if (catalogo.isEmpty()) {
            consola.mostrarMensaje("No hay productos disponibles.");
        } else {
            consola.mostrarMenuOrdenacion();
            int tipoOrden = consola.leerInt();
            EstrategiaOrdenacionProducto estrategia;
            switch (tipoOrden) {
                case 1 -> estrategia = new OrdenarPorPrecioAscendente();
                case 2 -> estrategia = new OrdenarPorPrecioDescendente();
                case 3 -> estrategia = new OrdenarPorFechaReciente();
                default -> {
                    consola.mostrarError("Ordenación no válida. Mostrando catálogo sin ordenar.");
                    estrategia = productos -> productos;
                }
            }

            List<Producto> catalogoOrdenado = estrategia.ordenar(catalogo);
            System.out.println("\u001B[1m=== Catálogo de productos ===\u001B[0m");
            for (Producto p : catalogoOrdenado) {
                System.out.println(p.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    /**
     * Permite al usuario agregar un producto a su lista de favoritos.
     */
    private void agregarAFavoritos() {
        System.out.println("\u001B[1m=== Añadir producto a favoritos ===\u001B[0m");
        List<Producto> catalogo = controlador.verCatalogo();
        if (catalogo.isEmpty()) {
            consola.mostrarMensaje("No hay productos disponibles.");
            return;
        }
        mostrarOpcionesDeProductos(catalogo);
        int opcion = consola.leerInt();
        Producto seleccionado = catalogo.get(opcion - 1);
        controlador.agregarAFavoritos(seleccionado);
        consola.mostrarMensaje("Producto añadido a favoritos.");
    }

    /**
     * Permite al usuario comprar un producto.
     */
    private void comprarProducto() {
        System.out.println("\u001B[1m=== Comprar producto ===\u001B[0m");
        List<Producto> catalogo = controlador.verCatalogo();
        if (catalogo.isEmpty()) {
            consola.mostrarMensaje("No hay productos disponibles.");
            return;
        }
        mostrarOpcionesDeProductos(catalogo);
        int opcion = consola.leerInt();
        Producto seleccionado = catalogo.get(opcion - 1);
        controlador.comprarProducto(seleccionado);
        consola.mostrarMensaje("Producto comprado con éxito.");
    }

    /**
     * Permite al usuario retirar un producto de la venta.
     */
    private void dejarDeVenderProducto() {
        System.out.println("\u001B[1m=== Retirar producto de la venta ===\u001B[0m");
        List<Producto> productos = controlador.getUsuarioActual().getProductosEnVenta();
        if (productos.isEmpty()) {
            consola.mostrarMensaje("No tienes productos en venta.");
            return;
        }
        mostrarOpcionesDeProductos(productos);
        int opcion = consola.leerInt();
        Producto seleccionado = productos.get(opcion - 1);
        controlador.dejarDeVenderProducto(seleccionado);
        consola.mostrarMensaje("Producto retirado de la venta.");
    }

    /**
     * Permite al usuario iniciar un chat sobre un producto.
     */
    private void iniciarChat() {
        System.out.println("\u001B[1m=== Iniciar chat ===\u001B[0m");
        List<Producto> catalogo = controlador.verCatalogo();
        mostrarOpcionesDeProductos(catalogo);
        int opcion = consola.leerInt();
        Producto seleccionado = catalogo.get(opcion - 1);
        controlador.iniciarChat(seleccionado);
        consola.mostrarMensaje("Chat iniciado con éxito.");
    }

    /**
     * Permite al usuario enviar un mensaje en un chat existente.
     */
    private void agregarMensajeEnChat() {
        System.out.println("\u001B[1m=== Enviar mensaje en chat ===\u001B[0m");
        List<Chat> chats = controlador.getChats();
        if (chats.isEmpty()) {
            consola.mostrarError("No tienes chats disponibles.");
            return;
        }
        System.out.println("Selecciona un chat:");
        for (int i = 0; i < chats.size(); i++) {
            System.out.println((i + 1) + ". " + chats.get(i));
        }
        int opcion = consola.leerInt();
        Chat chat = chats.get(opcion - 1);
        String contenido = consola.leerTexto("Escribe tu mensaje: ");
        if (!contenido.isBlank()) {
            controlador.agregarMensaje(chat, contenido);
            consola.mostrarMensaje("Mensaje enviado.");
        } else {
            consola.mostrarError("No se envió ningún mensaje.");
        }
    }
}
package es.uam.eps.adsof.controlador;

import es.uam.eps.adsof.modelo.*;
import es.uam.eps.adsof.productos.GestorProducto;
import es.uam.eps.adsof.usuarios.GestorUsuario;
import es.uam.eps.adsof.ventas.GestorVenta;
import es.uam.eps.adsof.chats.GestorChat;
import java.util.*;

/**
 * Controlador de la aplicación.
 * Este controlador actúa como intermediario entre la vista y los gestores de la aplicación.
 * Proporciona métodos para gestionar usuarios, productos, ventas y chats.
 */
public class Controlador {
    private final GestorUsuario gestorUsuario = new GestorUsuario();
    private final GestorProducto gestorProducto = new GestorProducto();
    private final GestorVenta gestorVenta = new GestorVenta();
    private final GestorChat gestorChat = new GestorChat();
    
    /**
     * Obtiene el gestor de usuarios.
     * 
     * @return GestorUsuario El gestor de usuarios.
     */
    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    /**
     * Obtiene el gestor de productos.
     * 
     * @return GestorProducto El gestor de productos.
     */
    public GestorProducto getGestorProducto() {
        return gestorProducto;
    }

    /**
     * Obtiene el gestor de ventas.
     * 
     * @return GestorVenta El gestor de ventas.
     */
    public GestorVenta getGestorVenta() {
        return gestorVenta;
    }

    /**
     * Obtiene el gestor de chats.
     * 
     * @return GestorChat El gestor de chats.
     */
    public GestorChat getGestorChat() {
        return gestorChat;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @param contraseña Contraseña del usuario.
     * @throws IllegalArgumentException Si el email ya está registrado.
     */
    public void registrarUsuario(String nombre, String email, String contraseña) {
        Usuario nuevo = new Usuario(nombre, email, contraseña);
        gestorUsuario.registrarUsuario(nuevo);
    }

    /**
     * Inicia sesión con un usuario existente.
     * 
     * @param email Email del usuario.
     * @param contraseña Contraseña del usuario.
     */
    public void login(String email, String contraseña) {
        gestorUsuario.login(email, contraseña);
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        gestorUsuario.logout();
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     * 
     * @return Usuario El usuario actual.
     */
    public Usuario getUsuarioActual() {
        return gestorUsuario.getUsuarioActual();
    }

    /**
     * Publica un producto en venta.
     * 
     * @param titulo Título del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     */
    public void ponerProductoEnVenta(String titulo, String descripcion, double precio) {
        gestorProducto.ponerProductoEnVenta(getUsuarioActual(), titulo, descripcion, precio);
    }

    /**
     * Cambia el precio de un producto.
     * 
     * @param producto Producto cuyo precio se desea cambiar.
     * @param nuevoPrecio Nuevo precio del producto.
     */
    public void cambiarPrecio(Producto producto, double nuevoPrecio) {
        gestorProducto.cambiarPrecioProducto(producto, nuevoPrecio);
    }

    /**
     * Obtiene el catálogo general de productos.
     * 
     * @return List<Producto> Lista de productos disponibles.
     */
    public List<Producto> verCatalogo() {
        return gestorProducto.obtenerCatalogoGeneral();
    }

    /**
     * Deja de vender un producto.
     * 
     * @param producto Producto que se desea retirar de la venta.
     */
    public void dejarDeVenderProducto(Producto producto) {
        gestorProducto.dejarDeVenderProducto(getUsuarioActual(), producto);
    }

    /**
     * Agrega un producto a la lista de favoritos del usuario actual.
     * 
     * @param producto Producto a agregar a favoritos.
     * @throws IllegalArgumentException Si el usuario intenta agregar su propio producto.
     */
    public void agregarAFavoritos(Producto producto) {
        Usuario usuario = getUsuarioActual();
        if (producto.getVendedor().equals(usuario)) {
            throw new IllegalArgumentException("No puedes añadir tus propios productos.");
        }
        usuario.agregarFavorito(producto);
    }

    /**
     * Compra un producto.
     * 
     * @param producto Producto que se desea comprar.
     */
    public void comprarProducto(Producto producto) {
        gestorVenta.comprarProducto(getUsuarioActual(), producto);
    }

    /**
     * Inicia un chat sobre un producto con su vendedor.
     * 
     * @param producto Producto sobre el cual se desea iniciar el chat.
     */
    public void iniciarChat(Producto producto) {
        gestorChat.iniciarChat(getUsuarioActual(), producto);
    }

    /**
     * Agrega un mensaje a un chat existente.
     * 
     * @param chat Chat en el que se desea agregar el mensaje.
     * @param contenido Contenido del mensaje.
     */
    public void agregarMensaje(Chat chat, String contenido) {
        gestorChat.agregarMensajeEnChat(chat, getUsuarioActual(), contenido);
    }

    /**
     * Obtiene la lista de chats del usuario actual.
     * 
     * @return List<Chat> Lista de chats del usuario actual.
     */
    public List<Chat> getChats() {
        return getUsuarioActual().getChats();
    }
}
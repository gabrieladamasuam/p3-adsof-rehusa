package es.uam.eps.adsof.chats;

import es.uam.eps.adsof.modelo.*;
import java.util.*;

/**
 * Clase GestorChat que gestiona los chats entre usuarios y vendedores.
 * Permite iniciar chats, agregar mensajes y eliminar mensajes en los chats.
 */
public class GestorChat {
    /**
     * Lista de chats activos en el sistema.
     */
    private final List<Chat> chats;

    /**
     * Constructor de la clase GestorChat.
     * Inicializa la lista de chats como una lista vacía.
     */
    public GestorChat() {
        this.chats = new ArrayList<>();
    }

    /**
     * Obtiene una copia inmutable de la lista de chats activos.
     * 
     * @return Lista inmutable de chats activos.
     */
    public List<Chat> getChats() {
        return List.copyOf(chats);
    }

    /**
     * Agrega un chat a la lista de chats activos.
     * 
     * @param chat Chat a agregar.
     */
    public void agregarChat(Chat chat) {
        chats.add(chat);
    }
    
    /**
     * Inicia un nuevo chat entre un usuario y el vendedor de un producto.
     * 
     * @param usuario Usuario que inicia el chat.
     * @param producto Producto sobre el cual se inicia el chat.
     * @throws IllegalArgumentException Si el usuario intenta iniciar un chat consigo mismo
     *                                  o si ya existe un chat entre el usuario y el vendedor
     *                                  sobre el mismo producto.
     */
    public void iniciarChat(Usuario usuario, Producto producto) {
        Usuario vendedor = producto.getVendedor();
        if (usuario.equals(vendedor)) {
            throw new IllegalArgumentException("No puedes iniciar un chat contigo mismo.");
        }
        for (Chat chat : chats) {
            if (chat.getProducto().equals(producto) && chat.participa(usuario) && chat.participa(vendedor)) {
                throw new IllegalArgumentException("Ya existe un chat con este usuario sobre este producto.");
            }
        }
        Chat nuevoChat = new Chat(usuario, vendedor, producto);
        chats.add(nuevoChat);
        usuario.agregarChat(nuevoChat);
        vendedor.agregarChat(nuevoChat);
    }

    /**
     * Agrega un mensaje a un chat existente.
     * 
     * @param chat Chat en el que se agrega el mensaje.
     * @param emisor Usuario que envía el mensaje.
     * @param contenido Contenido del mensaje.
     * @throws IllegalArgumentException Si el chat no está registrado, el usuario no participa
     *                                  en el chat, o el contenido del mensaje es nulo o vacío.
     */
    public void agregarMensajeEnChat(Chat chat, Usuario emisor, String contenido) {
        if (!chats.contains(chat)) {
            throw new IllegalArgumentException("El chat no está registrado en el sistema.");
        }
        if (!chat.participa(emisor)) {
            throw new IllegalArgumentException("El usuario no participa en este chat.");
        }
        if (contenido == null || contenido.isEmpty()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede ser nulo o vacío.");
        }
        Mensaje mensaje = new Mensaje(emisor, chat.getOtroUsuario(emisor), contenido);
        chat.agregarMensaje(mensaje);
    }

    /**
     * Elimina un mensaje de un chat.
     * 
     * @param chat Chat del que se quiere eliminar el mensaje.
     * @param usuario Usuario que solicita la eliminación del mensaje.
     * @param mensaje Mensaje a eliminar.
     * @throws IllegalArgumentException Si el chat no está registrado, el mensaje no pertenece
     *                                  al chat, o el usuario no es el emisor del mensaje.
     */
    public void eliminarMensajeEnChat(Chat chat, Usuario usuario, Mensaje mensaje) {
        if (!chats.contains(chat)) {
            throw new IllegalArgumentException("El chat no está registrado en el sistema.");
        }
        if (!chat.getMensajes().contains(mensaje)) {
            throw new IllegalArgumentException("El mensaje no pertenece a este chat.");
        }
        if (!mensaje.getEmisor().equals(usuario)) {
            throw new IllegalArgumentException("Solo el emisor del mensaje puede eliminarlo.");
        }
        chat.eliminarMensaje(mensaje);
    }
}
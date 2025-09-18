package es.uam.eps.adsof.modelo;

import java.util.*;

/**
 * Clase que representa un chat entre dos usuarios sobre un producto.
 * Permite enviar y recibir mensajes, así como marcar mensajes como leídos.
 */
public class Chat {
    private final Usuario usuario1;
    private final Usuario usuario2;
    private final Producto producto;
    private final List<Mensaje> mensajes;

    /**
     * Constructor de la clase Chat.
     * 
     * @param u1 Primer usuario participante del chat.
     * @param u2 Segundo usuario participante del chat.
     * @param producto Producto sobre el cual se realiza el chat.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo o si los usuarios son iguales.
     */
    public Chat(Usuario u1, Usuario u2, Producto producto) {
        if (u1 == null || u2 == null || producto == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o vacíos");
        }
        if (u1.equals(u2)) {
            throw new IllegalArgumentException("Los usuarios no pueden ser iguales");
        }
        this.usuario1 = u1;
        this.usuario2 = u2;
        this.producto = producto;
        this.mensajes = new ArrayList<>();
    }

    /**
     * Obtiene el primer usuario del chat.
     * 
     * @return Usuario El primer usuario.
     */
    public Usuario getUsuario1() {
        return usuario1;
    }

    /**
     * Obtiene el segundo usuario del chat.
     * 
     * @return Usuario El segundo usuario.
     */
    public Usuario getUsuario2() {
        return usuario2;
    }

    /**
     * Obtiene el otro usuario del chat dado un usuario actual.
     * 
     * @param actual Usuario actual.
     * @return Usuario El otro usuario del chat.
     * @throws IllegalArgumentException Si el usuario no pertenece al chat.
     */
    public Usuario getOtroUsuario(Usuario actual) {
        if (actual.equals(usuario1)) return usuario2;
        if (actual.equals(usuario2)) return usuario1;
        throw new IllegalArgumentException("El usuario no pertenece a este chat");
    }

    /**
     * Obtiene el producto sobre el cual se realiza el chat.
     * 
     * @return Producto El producto del chat.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene una copia inmutable de la lista de mensajes del chat.
     * 
     * @return List<Mensaje> Lista de mensajes.
     */
    public List<Mensaje> getMensajes() {
        return List.copyOf(mensajes);
    }

    /**
     * Obtiene el último mensaje del chat.
     * 
     * @return Mensaje El último mensaje, o null si no hay mensajes.
     */
    public Mensaje getUltimoMensaje() {
        if (mensajes.isEmpty()) return null;
        return mensajes.get(mensajes.size() - 1);
    }

    /**
     * Verifica si un usuario participa en el chat.
     * 
     * @param u Usuario a verificar.
     * @return boolean True si el usuario participa, false en caso contrario.
     */
    public boolean participa(Usuario u) {
        return u.equals(usuario1) || u.equals(usuario2);
    }

    /**
     * Agrega un mensaje al chat.
     * 
     * @param mensaje Mensaje a agregar.
     * @throws IllegalArgumentException Si el mensaje es nulo o el emisor no pertenece al chat.
     */
    public void agregarMensaje(Mensaje mensaje) {
        if (mensaje == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo");
        }
        if (!participa(mensaje.getEmisor())) {
            throw new IllegalArgumentException("El emisor no pertenece a este chat");
        }
        mensajes.add(mensaje);
    }

    /**
     * Elimina un mensaje del chat.
     * 
     * @param mensaje Mensaje a eliminar.
     * @throws IllegalArgumentException Si el mensaje es nulo o no pertenece al chat.
     */
    public void eliminarMensaje(Mensaje mensaje) {
        if (mensaje == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo");
        }
        if (!mensajes.contains(mensaje)) {
            throw new IllegalArgumentException("El mensaje no pertenece a este chat");
        }
        mensajes.remove(mensaje);
    }

    /**
     * Marca los mensajes dirigidos a un usuario como leídos.
     * 
     * @param usuario Usuario que marca los mensajes como leídos.
     * @throws IllegalArgumentException Si el usuario no pertenece al chat.
     */
    public void marcarMensajesComoLeidos(Usuario usuario) {
        if (!participa(usuario)) {
            throw new IllegalArgumentException("El usuario no pertenece a este chat.");
        }
        for (Mensaje mensaje : mensajes) {
            if (mensaje.getReceptor().equals(usuario)) {
                mensaje.setLeido(true);
            }
        }
    }

    /**
     * Compara si dos chats son iguales basándose en los usuarios y el producto.
     * 
     * @param obj Objeto a comparar.
     * @return boolean True si los chats son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chat chat = (Chat) obj;
        boolean mismosUsuarios = 
            (usuario1.equals(chat.usuario1) && usuario2.equals(chat.usuario2)) ||
            (usuario1.equals(chat.usuario2) && usuario2.equals(chat.usuario1));
        return mismosUsuarios && producto.equals(chat.producto);
    }

    /**
     * Calcula el hash code del chat.
     * 
     * @return int Hash code del chat.
     */
    @Override
    public int hashCode() {
        int h1 = usuario1.hashCode();
        int h2 = usuario2.hashCode();
        int userHash = h1 < h2 ? Objects.hash(h1, h2) : Objects.hash(h2, h1);
        return Objects.hash(userHash, producto);
    }

    /**
     * Representa el chat como una cadena de texto.
     * 
     * @return String Representación del chat.
     */
    @Override
    public String toString() {
        return "Chat con " + usuario1.getNombre() + " y " + usuario2.getNombre() + " sobre " + producto.getTitulo();
    }
}
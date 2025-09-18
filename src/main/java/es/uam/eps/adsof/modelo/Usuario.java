package es.uam.eps.adsof.modelo;

import es.uam.eps.adsof.observer.ProductoObserver;
import java.util.*;

/**
 * Clase que representa a un usuario en el sistema.
 * Un usuario tiene un nombre, un email, una contraseña y puede tener productos en venta,
 * chats y productos favoritos. Además, implementa la interfaz ProductoObserver para
 * recibir notificaciones sobre cambios en los productos que ha añadido a favoritos.
 */
public class Usuario implements ProductoObserver {
    private String nombre;
    private final String email;
    private String contraseña;
    private final List<Producto> productosEnVenta = new ArrayList<>();
    private final List<Chat> chats = new ArrayList<>();
    private final List<Producto> favoritos = new ArrayList<>();

    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @param contraseña Contraseña del usuario.
     * @throws IllegalArgumentException Si alguno de los parámetros es inválido.
     */
    public Usuario(String nombre, String email, String contraseña) {
        validarNombre(nombre);
        validarEmail(email);
        validarContraseña(contraseña);
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return String El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el usuario.
     * 
     * @param nombre Nuevo nombre del usuario.
     * @throws IllegalArgumentException Si el nombre es inválido.
     */
    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @return String El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return String La contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece una nueva contraseña para el usuario.
     * 
     * @param contraseña Nueva contraseña del usuario.
     * @throws IllegalArgumentException Si la contraseña es inválida.
     */
    public void setContraseña(String contraseña) {
        validarContraseña(contraseña);
        this.contraseña = contraseña;
    }

    /**
     * Verifica si una contraseña coincide con la del usuario.
     * 
     * @param contraseña Contraseña a verificar.
     * @return boolean True si la contraseña coincide, false en caso contrario.
     */
    public boolean verificarContraseña(String contraseña) {
        return this.contraseña.equals(contraseña);
    }

    /**
     * Obtiene la lista de productos favoritos del usuario.
     * 
     * @return List<Producto> Lista de productos favoritos.
     */
    public List<Producto> getFavoritos() {
        return List.copyOf(favoritos);
    }

    /**
     * Obtiene la lista de productos en venta del usuario.
     * 
     * @return List<Producto> Lista de productos en venta.
     */
    public List<Producto> getProductosEnVenta() {
        return List.copyOf(productosEnVenta);
    }

    /**
     * Obtiene la lista de chats del usuario.
     * 
     * @return List<Chat> Lista de chats.
     */
    public List<Chat> getChats() {
        return List.copyOf(chats);
    }

    /**
     * Agrega un producto a la lista de productos en venta del usuario.
     * 
     * @param producto Producto a agregar.
     * @throws IllegalArgumentException Si el producto es nulo o ya está en la lista.
     */
    public void agregarProductoEnVenta(Producto producto) {
        if (producto == null || productosEnVenta.contains(producto)) {
            throw new IllegalArgumentException("El producto no puede ser nulo o ya está en la lista de productos en venta.");
        }
        productosEnVenta.add(producto);
    }

    /**
     * Elimina un producto de la lista de productos en venta del usuario.
     * 
     * @param producto Producto a eliminar.
     */
    public void eliminarProductoEnVenta(Producto producto) {
        productosEnVenta.remove(producto);
    }

    /**
     * Agrega un chat a la lista de chats del usuario.
     * 
     * @param chat Chat a agregar.
     * @throws IllegalArgumentException Si el chat es nulo o ya está en la lista.
     */
    public void agregarChat(Chat chat) {
        if (chat == null || chats.contains(chat)) {
            throw new IllegalArgumentException("El chat no puede ser nulo o ya está en la lista de chats.");
        }
        chats.add(chat);
    }

    /**
     * Elimina un chat de la lista de chats del usuario.
     * 
     * @param chat Chat a eliminar.
     */
    public void eliminarChat(Chat chat) {
        chats.remove(chat);
    }

    /**
     * Agrega un producto a la lista de favoritos del usuario y lo registra como observador.
     * 
     * @param producto Producto a agregar a favoritos.
     * @throws IllegalArgumentException Si el producto es nulo o ya está en la lista.
     */
    public void agregarFavorito(Producto producto) {
        if (producto == null || favoritos.contains(producto)) {
            throw new IllegalArgumentException("Producto inválido o ya en favoritos.");
        }
        favoritos.add(producto);
        producto.addObserver(this);
    }

    /**
     * Elimina un producto de la lista de favoritos del usuario y lo elimina como observador.
     * 
     * @param producto Producto a eliminar de favoritos.
     */
    public void eliminarFavorito(Producto producto) {
        favoritos.remove(producto);
        producto.removeObserver(this);
    }

    /**
     * Método llamado cuando un producto observado cambia de precio.
     * 
     * @param producto Producto cuyo precio ha cambiado.
     * @param precioAnterior Precio anterior del producto.
     * @param precioNuevo Nuevo precio del producto.
     */
    @Override
    public void onPrecioCambiado(Producto producto, double precioAnterior, double precioNuevo) {
        System.out.printf("\u001B[33m[Notificación] %s: El producto '%s' ha cambiado de %.2f€ a %.2f€.\u001B[0m%n",
                nombre, producto.getTitulo(), precioAnterior, precioNuevo);
    }

    /**
     * Compara si dos usuarios son iguales basándose en su email.
     * 
     * @param obj Objeto a comparar.
     * @return boolean True si los usuarios son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usuario usuario = (Usuario) obj;
        return Objects.equals(email, usuario.email);
    }

    /**
     * Calcula el hash code del usuario basado en su email.
     * 
     * @return int Hash code del usuario.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Representa al usuario como una cadena de texto.
     * 
     * @return String Representación del usuario.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n"
             + "Email: " + email;
    }

    // Métodos de validación

    /**
     * Valida el nombre del usuario.
     * 
     * @param nombre Nombre a validar.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
    }

    /**
     * Valida el email del usuario.
     * 
     * @param email Email a validar.
     * @throws IllegalArgumentException Si el email es nulo, vacío o no tiene un formato válido.
     */
    private void validarEmail(String email) {
        if (email == null || email.isEmpty() || !email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email no es válido.");
        }
    }

    /**
     * Valida la contraseña del usuario.
     * 
     * @param contraseña Contraseña a validar.
     * @throws IllegalArgumentException Si la contraseña es nula, vacía o tiene menos de 8 caracteres.
     */
    private void validarContraseña(String contraseña) {
        if (contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
        }
        if (contraseña.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
    }
}
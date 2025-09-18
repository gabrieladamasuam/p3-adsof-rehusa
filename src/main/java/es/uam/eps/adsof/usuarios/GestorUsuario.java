package es.uam.eps.adsof.usuarios;

import es.uam.eps.adsof.modelo.*;
import java.util.*;

/**
 * Clase que gestiona los usuarios del sistema.
 * Permite registrar usuarios, iniciar y cerrar sesión, y acceder a información del usuario actual.
 */
public class GestorUsuario {
    private List<Usuario> usuarios;
    private Usuario usuarioActual;

    /**
     * Constructor por defecto. Inicializa la lista de usuarios y el usuario actual como nulo.
     */
    public GestorUsuario() {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;
    }

    /**
     * Devuelve la lista de usuarios registrados en el sistema.
     * 
     * @return lista de {@link Usuario}.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Devuelve el usuario que ha iniciado sesión actualmente.
     * 
     * @return el {@link Usuario} actual, o null si no hay ninguno.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Establece el usuario actual que ha iniciado sesión.
     * 
     * @param usuarioActual el usuario a establecer como actual.
     */
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuario el usuario a registrar.
     * @return true si el registro fue exitoso.
     * @throws IllegalArgumentException si el email ya está registrado.
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (buscarUsuarioPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        usuarios.add(usuario);
        return true;
    }

    /**
     * Busca un usuario por su dirección de email.
     * 
     * @param email dirección de correo electrónico del usuario.
     * @return el {@link Usuario} correspondiente o null si no se encuentra.
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas.
     * 
     * @param email correo electrónico del usuario.
     * @param contraseña contraseña del usuario.
     * @return true si el inicio de sesión es exitoso.
     * @throws IllegalArgumentException si el email no está registrado o la contraseña es incorrecta.
     */
    public boolean login(String email, String contraseña) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("El email no está registrado");
        }
        if (!usuario.verificarContraseña(contraseña)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        this.usuarioActual = usuario;
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
        return true;
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        this.usuarioActual = null;
    }

    /**
     * Comprueba si el usuario actual tiene un chat con otro usuario.
     * 
     * @param otroUsuario usuario con el que se desea comprobar el chat.
     * @return true si existe un chat con ese usuario, false en caso contrario.
     */
    public boolean tieneChatCon(Usuario otroUsuario) {
        for (Chat chat : usuarioActual.getChats()) {
            if (chat.participa(otroUsuario)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el chat existente entre el usuario actual y otro usuario.
     * 
     * @param otroUsuario el otro participante del chat.
     * @return el {@link Chat} correspondiente, o null si no existe.
     */
    public Chat getChatCon(Usuario otroUsuario) {
        for (Chat chat : usuarioActual.getChats()) {
            if (chat.participa(otroUsuario)) {
                return chat;
            }
        }
        return null;
    }
    
}
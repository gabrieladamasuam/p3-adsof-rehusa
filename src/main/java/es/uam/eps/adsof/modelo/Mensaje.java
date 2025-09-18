package es.uam.eps.adsof.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Mensaje que representa un mensaje entre dos usuarios.
 * Contiene información sobre el emisor, receptor, contenido, fecha de envío
 * y si el mensaje ha sido leído.
 */
public class Mensaje {
    private final Usuario emisor;
    private final Usuario receptor;
    private final String contenido;
    private final LocalDateTime fecha;
    private boolean leido;

    /**
     * Constructor de la clase Mensaje.
     * 
     * @param emisor Usuario que envía el mensaje.
     * @param receptor Usuario que recibe el mensaje.
     * @param contenido Contenido del mensaje.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo, el contenido está vacío,
     *                                  el emisor y receptor son iguales, o el contenido excede los 500 caracteres.
     */
    public Mensaje(Usuario emisor, Usuario receptor, String contenido) {
        if (emisor == null || receptor == null || contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o vacíos.");
        }
        if (emisor.equals(receptor)) {
            throw new IllegalArgumentException("El emisor y el receptor no pueden ser la misma persona.");
        }
        if (contenido.length() > 500) {
            throw new IllegalArgumentException("El contenido del mensaje no puede exceder los 500 caracteres.");
        }
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido.trim();
        this.fecha = LocalDateTime.now();
        this.leido = false;
    }

    /**
     * Obtiene el usuario que envió el mensaje.
     * 
     * @return Usuario El emisor del mensaje.
     */
    public Usuario getEmisor() {
        return emisor;
    }

    /**
     * Obtiene el usuario que recibió el mensaje.
     * 
     * @return Usuario El receptor del mensaje.
     */
    public Usuario getReceptor() {
        return receptor;
    }

    /**
     * Obtiene el contenido del mensaje.
     * 
     * @return String El contenido del mensaje.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Obtiene la fecha y hora en que se envió el mensaje.
     * 
     * @return LocalDateTime La fecha y hora del mensaje.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Marca el mensaje como leído o no leído.
     * 
     * @param leido True si el mensaje debe marcarse como leído, false en caso contrario.
     */
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    /**
     * Verifica si el mensaje ha sido leído.
     * 
     * @return boolean True si el mensaje ha sido leído, false en caso contrario.
     */
    public boolean isLeido() {
        return leido;
    }

    /**
     * Representa el mensaje como una cadena de texto.
     * Incluye la fecha, el emisor, el contenido y si ha sido leído.
     * 
     * @return String Representación del mensaje.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "[" + fecha.format(formatter) + "] " + emisor.getNombre() + ": " + contenido + (leido ? " (leído)" : "");
    }
}
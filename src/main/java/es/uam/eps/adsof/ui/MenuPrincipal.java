package es.uam.eps.adsof.ui;

import es.uam.eps.adsof.controlador.Controlador;
import es.uam.eps.adsof.modelo.Usuario;

/**
 * Clase MenuPrincipal que gestiona el menú principal de la aplicación.
 * Permite al usuario registrarse, iniciar sesión o salir del programa.
 */
public class MenuPrincipal {
    private final Consola consola;
    private final Controlador controlador;
    private final int REGISTRO = 1;
    private final int INICIO_SESION = 2;
    private final int SALIR = 3;

    /**
     * Constructor de la clase MenuPrincipal.
     * 
     * @param controlador Controlador de la aplicación para gestionar las operaciones.
     */
    public MenuPrincipal(Controlador controlador) {
        this.consola = new Consola();
        this.controlador = controlador;
    }

    /**
     * Muestra el menú principal y gestiona las opciones seleccionadas por el usuario.
     * Permite registrarse, iniciar sesión o salir del programa.
     */
    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            consola.mostrarMenuPrincipal();
            int opcion = consola.leerInt();
            switch (opcion) {
                case REGISTRO -> registrarse();
                case INICIO_SESION -> iniciarSesion();
                case SALIR -> salir = true;
                default -> consola.mostrarError("Opción no válida.");
            }
        }
        consola.mostrarMensaje("Saliendo del programa...");
        consola.cerrar();
    }

    /**
     * Permite al usuario registrarse en el sistema.
     * Solicita los datos necesarios y los envía al controlador para su registro.
     */
    private void registrarse() {
        try {
            String nombre = consola.leerTexto("Ingrese el nombre del usuario: ");
            String email = consola.leerTexto("Ingrese el email del usuario: ");
            String contraseña = consola.leerTexto("Ingrese la contraseña del usuario: ");
            controlador.registrarUsuario(nombre, email, contraseña);
            consola.mostrarMensaje("Registro exitoso.");
        } catch (IllegalArgumentException e) {
            consola.mostrarError(e.getMessage());
        }
    }

    /**
     * Permite al usuario iniciar sesión en el sistema.
     * Solicita las credenciales y las envía al controlador para su validación.
     * Si el inicio de sesión es exitoso, muestra el menú de usuario.
     */
    private void iniciarSesion() {
        try {
            String email = consola.leerTexto("Ingrese su email: ");
            String contraseña = consola.leerTexto("Ingrese su contraseña: ");
            controlador.login(email, contraseña);
            Usuario usuario = controlador.getUsuarioActual();
            consola.mostrarMensaje("Inicio de sesión exitoso.");
            System.out.println("Bienvenid@, " + usuario.getNombre() + "!");
            new MenuUsuario(controlador, consola).mostrarMenu();
        } catch (IllegalArgumentException e) {
            consola.mostrarError(e.getMessage());
        }
    }
}
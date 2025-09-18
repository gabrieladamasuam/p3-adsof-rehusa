package es.uam.eps.adsof.ui;

import java.util.Scanner;

/**
 * Clase Consola que gestiona la interacción con el usuario a través de la consola.
 * Proporciona métodos para mostrar menús, leer entradas y mostrar mensajes.
 */
public class Consola {
    private Scanner scanner;

    /**
     * Constructor de la clase Consola.
     * Inicializa el scanner para leer entradas desde la consola.
     */
    public Consola() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal de la aplicación.
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\u001B[1m=== Menú Principal ===\u001B[0m");
        System.out.println("1. Registrar un usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones para un usuario logueado.
     */
    public void mostrarMenuUsuario() {
        System.out.println("\u001B[1m=== Menú de Usuario ===\u001B[0m");
        System.out.println("1. Poner a la venta un producto");
        System.out.println("2. Cambiar el precio de un producto");
        System.out.println("3. Ver el catálogo de productos disponibles");
        System.out.println("4. Añadir un producto a favoritos");
        System.out.println("5. Comprar un producto");
        System.out.println("6. Dejar de vender un producto");
        System.out.println("7. Crear un chat");
        System.out.println("8. Añadir un mensaje en un chat");
        System.out.println("9. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Muestra el menú de opciones de ordenación de productos.
     */
    public void mostrarMenuOrdenacion() {
        System.out.println("\u001B[1m=== Menú de Ordenación ===\u001B[0m");
        System.out.println("1. Ordenar por precio ascendente");
        System.out.println("2. Ordenar por precio descendente");
        System.out.println("3. Ordenar por más reciente primero");
        System.out.println("4. Ordenar por más antiguo primero");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Lee un número entero ingresado por el usuario.
     * 
     * @return int El número entero ingresado.
     */
    public int leerInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                mostrarError("Por favor, ingrese un número válido.");
            }
        }
    }

    /**
     * Lee un texto ingresado por el usuario.
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el texto.
     * @return String El texto ingresado por el usuario.
     */
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un número decimal ingresado por el usuario.
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el número.
     * @return double El número decimal ingresado por el usuario.
     */
    public double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                mostrarError("Por favor, ingrese un número decimal válido.");
            }
        }
    }

    /**
     * Muestra un mensaje genérico en la consola.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println("\u001B[32m" + mensaje + "\u001B[0m");
    }

    /**
     * Muestra un mensaje de error en la consola.
     * 
     * @param mensaje Mensaje de error a mostrar.
     */
    public void mostrarError(String mensaje) {
        System.err.println("\u001B[31mError: " + mensaje + "\u001B[0m");
    }

    /**
     * Cierra el scanner utilizado para leer entradas.
     * Debe llamarse al finalizar el programa para liberar recursos.
     */
    public void cerrar() {
        scanner.close();
    }
}
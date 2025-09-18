package es.uam.eps.adsof.persistencia;

import es.uam.eps.adsof.modelo.*;
import es.uam.eps.adsof.controlador.Controlador;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase responsable de la gestión de persistencia de datos del sistema.
 * Lee y escribe datos en archivos CSV para mantener el estado del sistema
 * entre ejecuciones.
 */
public class GestorPersistencia {
    private static final String USUARIOS_CSV = "adsof-p3-8261-07/datos/usuarios.csv";
    private static final String PRODUCTOS_CSV = "adsof-p3-8261-07/datos/productos.csv";
    private static final String VENTAS_CSV = "adsof-p3-8261-07/datos/ventas.csv";
    private static final String CHATS_CSV = "adsof-p3-8261-07/datos/chats.csv";
    private static final String MENSAJES_CSV = "adsof-p3-8261-07/datos/mensajes.csv";
    private static final String FAVORITOS_CSV = "adsof-p3-8261-07/datos/favoritos.csv";

    /**
     * Comprueba si existen archivos de usuarios, lo que indica que el sistema ya ha sido usado antes.
     * @return true si el archivo de usuarios existe, false en caso contrario.
     */
    public boolean existenArchivos() {
        // Solo comprobamos si existe usuarios.csv como indicador de ejecución previa del programa.
        // Si existe, asumimos que también pueden existir los demás archivos (productos, ventas, etc.).
        // La existencia individual de cada archivo la verificamos en sus respectivos métodos de carga.
        return Files.exists(Path.of(USUARIOS_CSV));
    }

    /**
     * Carga todos los datos del sistema desde los archivos CSV.
     * @param controlador instancia del controlador del sistema.
     * @throws IOException si ocurre un error al acceder a los archivos.
     */    
    public void cargarDatos(Controlador controlador) throws IOException {
        Map<String, Usuario> usuariosMap = cargarUsuarios(controlador);
        Map<String, Producto> productosMap = cargarProductos(controlador, usuariosMap);
        cargarVentas(controlador, usuariosMap, productosMap);
        cargarChats(controlador, usuariosMap, productosMap);
        cargarMensajes(controlador.getGestorChat().getChats(), usuariosMap, productosMap);
        cargarFavoritos(controlador, usuariosMap, productosMap);
    }

    /**
     * Carga los usuarios desde el archivo CSV.
     * @param controlador controlador del sistema.
     * @return mapa de usuarios indexados por email.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private Map<String, Usuario> cargarUsuarios(Controlador controlador) throws IOException {
        Map<String, Usuario> usuariosMap = new HashMap<>();
        if (!Files.exists(Path.of(USUARIOS_CSV))) return usuariosMap;

        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 3) {
                    Usuario usuario = new Usuario(partes[0], partes[1], partes[2]);
                    controlador.getGestorUsuario().registrarUsuario(usuario);
                    usuariosMap.put(usuario.getEmail(), usuario);
                }
            }
        }
        return usuariosMap;
    }

    /**
     * Carga los productos desde el archivo CSV.
     * @param controlador controlador del sistema.
     * @param usuariosMap mapa de usuarios.
     * @return mapa de productos indexados por título.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private Map<String, Producto> cargarProductos(Controlador controlador, Map<String, Usuario> usuariosMap) throws IOException {
        Map<String, Producto> productosMap = new HashMap<>();
        if (!Files.exists(Path.of(PRODUCTOS_CSV))) return productosMap;

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTOS_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 6) {
                    String titulo = partes[0];
                    String descripcion = partes[1];
                    Usuario vendedor = usuariosMap.get(partes[2]);
                    double precio = Double.parseDouble(partes[3]);
                    EstadoProducto estado = EstadoProducto.valueOf(partes[4]);

                    Producto producto = new Producto(titulo, descripcion, vendedor, precio);
                    producto.setEstado(estado);
                    
                    if (!vendedor.getProductosEnVenta().contains(producto)) {
                        vendedor.agregarProductoEnVenta(producto);
                    }
                    if (!controlador.getGestorProducto().getProductos().contains(producto)) {
                        controlador.getGestorProducto().agregarProducto(producto);
                        productosMap.put(titulo, producto);
                    }
                }
            }
        }
        return productosMap;
    }

    /**
     * Carga las ventas desde el archivo CSV.
     * @param controlador controlador del sistema.
     * @param usuariosMap mapa de usuarios.
     * @param productosMap mapa de productos.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private void cargarVentas(Controlador controlador, Map<String, Usuario> usuariosMap, Map<String, Producto> productosMap) throws IOException {
        if (!Files.exists(Path.of(VENTAS_CSV))) return;

        try (BufferedReader br = new BufferedReader(new FileReader(VENTAS_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 4) {
                    Usuario comprador = usuariosMap.get(partes[0]);
                    Producto producto = productosMap.get(partes[1]);
                    Usuario vendedor = usuariosMap.get(partes[2]);
                    LocalDateTime fecha = LocalDateTime.parse(partes[3]);

                    Venta venta = new Venta(comprador, vendedor, producto);
                    venta.setFecha(fecha);
                    controlador.getGestorVenta().getVentas().add(venta);
                }
            }
        }
    }

    /**
     * Carga los chats desde el archivo CSV.
     * @param controlador controlador del sistema.
     * @param usuariosMap mapa de usuarios.
     * @param productosMap mapa de productos.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private void cargarChats(Controlador controlador, Map<String, Usuario> usuariosMap, Map<String, Producto> productosMap) throws IOException {
        if (!Files.exists(Path.of(CHATS_CSV))) return;

        try (BufferedReader br = new BufferedReader(new FileReader(CHATS_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 3) {
                    Usuario usuario1 = usuariosMap.get(partes[0]);
                    Usuario usuario2 = usuariosMap.get(partes[1]);
                    Producto producto = productosMap.get(partes[2]);

                    Chat chat = new Chat(usuario1, usuario2, producto);
                    controlador.getGestorChat().agregarChat(chat);
                    usuario1.agregarChat(chat);
                    usuario2.agregarChat(chat);
                }
            }
        }
    }

    /**
     * Carga los mensajes de los chats desde el archivo CSV.
     * @param chats lista de chats existentes.
     * @param usuariosMap mapa de usuarios.
     * @param productosMap mapa de productos.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private void cargarMensajes(List<Chat> chats, Map<String, Usuario> usuariosMap, Map<String, Producto> productosMap) throws IOException {
        if (!Files.exists(Path.of(MENSAJES_CSV))) return;

        try (BufferedReader br = new BufferedReader(new FileReader(MENSAJES_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 5) {
                    Usuario emisor = usuariosMap.get(partes[0]);
                    Usuario receptor = usuariosMap.get(partes[1]);
                    String contenido = partes[2];
                    Producto producto = productosMap.get(partes[4]);

                    for (Chat chat : chats) {
                        if (chat.getProducto().equals(producto) && chat.participa(emisor) && chat.participa(receptor)) {
                            Mensaje mensaje = new Mensaje(emisor, receptor, contenido);
                            mensaje.setLeido(true);
                            chat.agregarMensaje(mensaje);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Carga los productos favoritos de cada usuario desde el archivo CSV.
     * @param controlador controlador del sistema.
     * @param usuariosMap mapa de usuarios.
     * @param productosMap mapa de productos.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private void cargarFavoritos(Controlador controlador, Map<String, Usuario> usuariosMap, Map<String, Producto> productosMap) throws IOException {
        if (!Files.exists(Path.of(FAVORITOS_CSV))) return;
    
        try (BufferedReader br = new BufferedReader(new FileReader(FAVORITOS_CSV))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 3) {
                    String emailUsuario = partes[0];
                    String tituloProducto = partes[1];
                    String emailVendedor = partes[2];
    
                    Usuario usuario = usuariosMap.get(emailUsuario);
                    Producto producto = productosMap.values().stream()
                        .filter(p -> p.getTitulo().equals(tituloProducto)
                                  && p.getVendedor().getEmail().equals(emailVendedor))
                        .findFirst().orElse(null);
    
                    if (usuario != null && producto != null) {
                        usuario.agregarFavorito(producto);
                    }
                }
            }
        }
    }    

    /**
     * Guarda todos los datos del sistema en archivos CSV.
     * @param usuarios lista de usuarios.
     * @param productos lista de productos.
     * @param ventas lista de ventas.
     * @param chats lista de chats.
     * @throws IOException si ocurre un error al escribir los archivos.
     */ 
    public void guardarDatos(List<Usuario> usuarios, List<Producto> productos, List<Venta> ventas, List<Chat> chats) throws IOException {
        Files.createDirectories(Path.of("adsof-p3-8261-07/datos"));
        guardarUsuarios(usuarios);
        guardarProductos(productos);
        guardarVentas(ventas);
        guardarChats(chats);
        guardarMensajes(chats);
        guardarFavoritos(usuarios);
    }

    /** Guarda los usuarios en el archivo CSV. */
    private void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_CSV))) {
            bw.write("nombre;email;contraseña");
            bw.newLine();
            for (Usuario u : usuarios) {
                bw.write(u.getNombre() + ";" + u.getEmail() + ";" + u.getContraseña());
                bw.newLine();
            }
        }
    }

    /** Guarda los productos en el archivo CSV. */
    private void guardarProductos(List<Producto> productos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCTOS_CSV))) {
            bw.write("titulo;descripcion;vendedor;precio;estado;fechaPublicacion");
            bw.newLine();
            for (Producto p : productos) {
                bw.write(p.getTitulo() + ";" + p.getDescripcion() + ";" + p.getVendedor().getEmail() + ";" +
                         p.getPrecio() + ";" + p.getEstado() + ";" + p.getFechaPublicacion());
                bw.newLine();
            }
        }
    }

    /** Guarda las ventas en el archivo CSV. */
    private void guardarVentas(List<Venta> ventas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENTAS_CSV))) {
            bw.write("vendedor;producto;comprador;fecha");
            bw.newLine();
            for (Venta v : ventas) {
                bw.write(v.getVendedor().getEmail() + ";" + v.getProducto().getTitulo() + ";" +
                         v.getComprador().getEmail() + ";" + v.getFecha());
                bw.newLine();
            }
        }
    }

    /** Guarda los chats en el archivo CSV. */
    private void guardarChats(List<Chat> chats) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CHATS_CSV))) {
            bw.write("usuario1;usuario2;producto");
            bw.newLine();
            for (Chat c : chats) {
                bw.write(c.getUsuario1().getEmail() + ";" + c.getUsuario2().getEmail() + ";" +
                         c.getProducto().getTitulo());
                bw.newLine();
            }
        }
    }

    /** Guarda los mensajes de los chats en el archivo CSV. */
    private void guardarMensajes(List<Chat> chats) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MENSAJES_CSV))) {
            bw.write("emisor;receptor;contenido;fecha;producto");
            bw.newLine();
            for (Chat chat : chats) {
                for (Mensaje m : chat.getMensajes()) {
                    bw.write(m.getEmisor().getEmail() + ";" + m.getReceptor().getEmail() + ";" +
                             m.getContenido().replace(";", ",") + ";" + m.getFecha() + ";" +
                             chat.getProducto().getTitulo());
                    bw.newLine();
                }
            }
        }
    }

    /** Guarda los productos favoritos de cada usuario en el archivo CSV. */
    private void guardarFavoritos(List<Usuario> usuarios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FAVORITOS_CSV))) {
            bw.write("usuario;producto;vendedor");
            bw.newLine();
            for (Usuario u : usuarios) {
                for (Producto p : u.getFavoritos()) {
                    bw.write(u.getEmail() + ";" + p.getTitulo() + ";" + p.getVendedor().getEmail());
                    bw.newLine();
                }
            }
        }
    }    
}
package modulos;

/**
 * Entidad que representa un elemento del juego (Fuego, Agua, Tierra, Aire).
 * Los elementos determinan las interacciones de daño entre cartas y estadios.
 */
public class Elemento {

    /** Identificador único en la base de datos. */
    private int id_elemento;
    /** Nombre del elemento (p.ej. "FUEGO", "AGUA"). */
    private String nombre;
    /** Descripción del elemento y su comportamiento. */
    private String descripcion;

    /**
     * Constructor completo usado al reconstruir un elemento desde la base de datos.
     *
     * @param id_elemento Identificador en la base de datos.
     * @param nombre      Nombre del elemento.
     * @param descripcion Descripción del elemento.
     */
    public Elemento(int id_elemento, String nombre, String descripcion) {
        this.id_elemento = id_elemento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param nombre      Nombre del elemento.
     * @param descripcion Descripción del elemento.
     */
    public Elemento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /** @return Identificador único del elemento. */
    public int getId_elemento() {
        return id_elemento;
    }

    /** @param id_elemento Nuevo identificador. */
    public void setId_elemento(int id_elemento) {
        this.id_elemento = id_elemento;
    }

    /** @return Nombre del elemento. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre del elemento. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Descripción del elemento. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion Nueva descripción del elemento. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Elemento [id_elemento=" + id_elemento + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }
}

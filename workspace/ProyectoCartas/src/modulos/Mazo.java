package modulos;

/**
 * Entidad que representa el mazo de cartas de un jugador.
 * Cada jugador tiene un mazo con un máximo de 10 cartas, gestionado desde {@link dao.MazoDAO}.
 */
public class Mazo {

    /** Identificador único en la base de datos. */
    private int id_mazo;
    /** Identificador del jugador propietario del mazo. */
    private int id_jugador;
    /** Nombre descriptivo del mazo. */
    private String nombre;

    /**
     * Constructor completo usado al reconstruir un mazo desde la base de datos.
     *
     * @param id_mazo    Identificador en la base de datos.
     * @param id_jugador Identificador del jugador propietario.
     * @param nombre     Nombre del mazo.
     */
    public Mazo(int id_mazo, int id_jugador, String nombre) {
        this.id_mazo = id_mazo;
        this.id_jugador = id_jugador;
        this.nombre = nombre;
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param id_jugador Identificador del jugador propietario.
     * @param nombre     Nombre del mazo.
     */
    public Mazo(int id_jugador, String nombre) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
    }

    /** @return Identificador único del mazo. */
    public int getId_mazo() {
        return id_mazo;
    }

    /** @param id_mazo Nuevo identificador. */
    public void setId_mazo(int id_mazo) {
        this.id_mazo = id_mazo;
    }

    /** @return Identificador del jugador propietario. */
    public int getId_jugador() {
        return id_jugador;
    }

    /** @param id_jugador Nuevo propietario del mazo. */
    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    /** @return Nombre del mazo. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre del mazo. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Mazo [id_mazo=" + id_mazo + ", id_jugador=" + id_jugador + ", nombre=" + nombre + "]";
    }
}

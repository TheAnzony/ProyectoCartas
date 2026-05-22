package modulos;

/**
 * Entidad que representa un estadio donde se desarrollan las partidas.
 * Cada estadio tiene un elemento inicial y un elemento activo que puede
 * cambiar durante la partida a través de cartas de tipo ESTADO.
 */
public class Estadio {

    /** Identificador único en la base de datos. */
    private int id_estadio;
    /** Nombre del estadio. */
    private String nombre;
    /** Descripción del estadio y sus características. */
    private String descripcion;
    /** Elemento con el que arranca el estadio al inicio de cada partida. */
    private int id_elemento_inicial;
    /** Elemento activo actualmente en el estadio; puede cambiar con cartas ESTADO. */
    private int id_elemento_activo;

    /**
     * Constructor completo usado al reconstruir un estadio desde la base de datos.
     *
     * @param id_estadio          Identificador en la base de datos.
     * @param nombre              Nombre del estadio.
     * @param descripcion         Descripción del estadio.
     * @param id_elemento_inicial Elemento inicial del estadio.
     * @param id_elemento_activo  Elemento activo actual.
     */
    public Estadio(int id_estadio, String nombre, String descripcion, int id_elemento_inicial, int id_elemento_activo) {
        this.id_estadio = id_estadio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento_inicial = id_elemento_inicial;
        this.id_elemento_activo = id_elemento_activo;
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param nombre              Nombre del estadio.
     * @param descripcion         Descripción del estadio.
     * @param id_elemento_inicial Elemento inicial del estadio.
     * @param id_elemento_activo  Elemento activo actual.
     */
    public Estadio(String nombre, String descripcion, int id_elemento_inicial, int id_elemento_activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento_inicial = id_elemento_inicial;
        this.id_elemento_activo = id_elemento_activo;
    }

    /** @return Identificador único del estadio. */
    public int getId_estadio() {
        return id_estadio;
    }

    /** @param id_estadio Nuevo identificador. */
    public void setId_estadio(int id_estadio) {
        this.id_estadio = id_estadio;
    }

    /** @return Nombre del estadio. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre del estadio. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Descripción del estadio. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion Nueva descripción. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return Identificador del elemento con el que arranca el estadio. */
    public int getId_elemento_inicial() {
        return id_elemento_inicial;
    }

    /** @param id_elemento_inicial Nuevo elemento inicial. */
    public void setId_elemento_inicial(int id_elemento_inicial) {
        this.id_elemento_inicial = id_elemento_inicial;
    }

    /** @return Identificador del elemento activo actualmente en el estadio. */
    public int getId_elemento_activo() {
        return id_elemento_activo;
    }

    /** @param id_elemento_activo Nuevo elemento activo (cambiado por cartas ESTADO). */
    public void setId_elemento_activo(int id_elemento_activo) {
        this.id_elemento_activo = id_elemento_activo;
    }

    @Override
    public String toString() {
        return "Estadio [id_estadio=" + id_estadio + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", id_elemento_inicial=" + id_elemento_inicial + ", id_elemento_activo=" + id_elemento_activo + "]";
    }
}

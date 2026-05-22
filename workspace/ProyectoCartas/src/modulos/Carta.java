package modulos;

/**
 * Clase abstracta que representa una carta del juego.
 * Las subclases concretas son {@link CartaOfensiva}, {@link CartaDefensiva} y {@link CartaEstado}.
 */
public abstract class Carta {

    private int id_carta;
    private String nombre;
    private String descripcion;
    private int id_elemento;
    private int coste_mana;
    private int dano;
    private int escudo;
    private int duracion;
    private int velocidad;
    private String rareza;
    private String imagen;

    /**
     * Constructor completo usado al reconstruir una carta desde la base de datos.
     *
     * @param id_carta    Identificador en la base de datos.
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Identificador del elemento al que pertenece.
     * @param coste_mana  Coste de maná para jugar la carta.
     * @param dano        Daño base de la carta (0 si es defensiva o de estado).
     * @param escudo      Escudo base de la carta (0 si es ofensiva o de estado).
     * @param duracion    Duración en turnos del escudo (0 si no aplica).
     * @param velocidad   Velocidad de resolución (mayor valor se resuelve primero).
     * @param rareza      Rareza de la carta (COMUN, POCO_COMUN, RARO, EPICO, LEGENDARIO).
     */
    public Carta(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        this.id_carta = id_carta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento = id_elemento;
        this.coste_mana = coste_mana;
        this.dano = dano;
        this.escudo = escudo;
        this.duracion = duracion;
        this.velocidad = velocidad;
        this.rareza = rareza;
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Identificador del elemento al que pertenece.
     * @param coste_mana  Coste de maná para jugar la carta.
     * @param dano        Daño base (0 si no aplica).
     * @param escudo      Escudo base (0 si no aplica).
     * @param duracion    Duración del escudo en turnos (0 si no aplica).
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public Carta(String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento = id_elemento;
        this.coste_mana = coste_mana;
        this.dano = dano;
        this.escudo = escudo;
        this.duracion = duracion;
        this.velocidad = velocidad;
        this.rareza = rareza;
    }

    /** Devuelve el tipo de carta: {@code "OFENSIVA"}, {@code "DEFENSIVA"} o {@code "ESTADO"}. */
    public abstract String getTipo();

    /** @return Identificador único de la carta. */
    public int getId_carta() {
        return id_carta;
    }

    /** @param id_carta Nuevo identificador. */
    public void setId_carta(int id_carta) {
        this.id_carta = id_carta;
    }

    /** @return Nombre de la carta. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Descripción del efecto de la carta. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion Nueva descripción. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return Identificador del elemento al que pertenece la carta. */
    public int getId_elemento() {
        return id_elemento;
    }

    /** @param id_elemento Nuevo elemento. */
    public void setId_elemento(int id_elemento) {
        this.id_elemento = id_elemento;
    }

    /** @return Coste de maná para jugar la carta. */
    public int getCoste_mana() {
        return coste_mana;
    }

    /** @param coste_mana Nuevo coste de maná. */
    public void setCoste_mana(int coste_mana) {
        this.coste_mana = coste_mana;
    }

    /** @return Daño base de la carta (antes de aplicar multiplicadores). */
    public int getDano() {
        return dano;
    }

    /** @param dano Nuevo daño base. */
    public void setDano(int dano) {
        this.dano = dano;
    }

    /** @return Valor de escudo que proporciona la carta. */
    public int getEscudo() {
        return escudo;
    }

    /** @param escudo Nuevo valor de escudo. */
    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }

    /** @return Duración en turnos del escudo (solo aplica a cartas DEFENSIVA). */
    public int getDuracion() {
        return duracion;
    }

    /** @param duracion Nueva duración en turnos. */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /** @return Velocidad de resolución de la carta (mayor valor se resuelve antes). */
    public int getVelocidad() {
        return velocidad;
    }

    /** @param velocidad Nueva velocidad. */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /** @return Rareza de la carta (COMUN, POCO_COMUN, RARO, EPICO, LEGENDARIO). */
    public String getRareza() {
        return rareza;
    }

    /** @param rareza Nueva rareza. */
    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    /** @return Nombre del fichero de imagen de la carta, o {@code null} si usa la imagen de elemento por defecto. */
    public String getImagen() {
        return imagen;
    }

    /** @param imagen Nombre del fichero de imagen. */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}

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

    /** Devuelve el tipo de carta: "OFENSIVA", "DEFENSIVA" o "ESTADO". */
    public abstract String getTipo();

    public int getId_carta() {
        return id_carta;
    }

    public void setId_carta(int id_carta) {
        this.id_carta = id_carta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_elemento() {
        return id_elemento;
    }

    public void setId_elemento(int id_elemento) {
        this.id_elemento = id_elemento;
    }

    public int getCoste_mana() {
        return coste_mana;
    }

    public void setCoste_mana(int coste_mana) {
        this.coste_mana = coste_mana;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getEscudo() {
        return escudo;
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}

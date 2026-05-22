package modulos;

/**
 * Entidad de relación que vincula un {@link Mazo} con una {@link Carta}.
 * Representa la tabla {@code MAZO_CARTA} de la base de datos (clave primaria compuesta).
 */
public class Mazo_carta {

    /** Identificador del mazo. */
    private int id_mazo;
    /** Identificador de la carta incluida en el mazo. */
    private int id_carta;

    /**
     * Crea la asociación entre un mazo y una carta.
     *
     * @param id_mazo  Identificador del mazo.
     * @param id_carta Identificador de la carta.
     */
    public Mazo_carta(int id_mazo, int id_carta) {
        this.id_mazo = id_mazo;
        this.id_carta = id_carta;
    }

    /** @return Identificador del mazo. */
    public int getId_mazo() {
        return id_mazo;
    }

    /** @param id_mazo Nuevo identificador de mazo. */
    public void setId_mazo(int id_mazo) {
        this.id_mazo = id_mazo;
    }

    /** @return Identificador de la carta. */
    public int getId_carta() {
        return id_carta;
    }

    /** @param id_carta Nuevo identificador de carta. */
    public void setId_carta(int id_carta) {
        this.id_carta = id_carta;
    }

    @Override
    public String toString() {
        return "Mazo_carta [id_mazo=" + id_mazo + ", id_carta=" + id_carta + "]";
    }
}

package modulos;

/**
 * Carta de tipo OFENSIVA. Causa daño directo al jugador rival,
 * aplicando el multiplicador elemental del estadio activo.
 */
public class CartaOfensiva extends Carta {

    /**
     * Constructor completo usado al reconstruir desde la base de datos.
     *
     * @param id_carta    Identificador en la base de datos.
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Elemento al que pertenece.
     * @param coste_mana  Coste de maná.
     * @param dano        Daño base antes de multiplicadores elementales.
     * @param escudo      Escudo (siempre 0 en cartas ofensivas).
     * @param duracion    Duración (siempre 0 en cartas ofensivas).
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaOfensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(id_carta, nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Elemento al que pertenece.
     * @param coste_mana  Coste de maná.
     * @param dano        Daño base.
     * @param escudo      Escudo (siempre 0).
     * @param duracion    Duración (siempre 0).
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaOfensiva(String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "OFENSIVA";
    }
}

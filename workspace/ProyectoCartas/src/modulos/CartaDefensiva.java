package modulos;

/**
 * Carta de tipo DEFENSIVA. Aplica un escudo al jugador activo con
 * un valor y una duración determinados por la propia carta.
 */
public class CartaDefensiva extends Carta {

    /**
     * Constructor completo usado al reconstruir desde la base de datos.
     *
     * @param id_carta    Identificador en la base de datos.
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Elemento al que pertenece.
     * @param coste_mana  Coste de maná.
     * @param dano        Daño (siempre 0 en cartas defensivas).
     * @param escudo      Valor de escudo base (antes de multiplicadores elementales).
     * @param duracion    Número de turnos que dura el escudo.
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaDefensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
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
     * @param dano        Daño (siempre 0).
     * @param escudo      Valor de escudo base.
     * @param duracion    Número de turnos que dura el escudo.
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaDefensiva(String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "DEFENSIVA";
    }
}

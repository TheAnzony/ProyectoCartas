package modulos;

/**
 * Carta de tipo ESTADO. Cambia el elemento activo del estadio
 * al elemento de esta carta (daño=0, escudo=0, duracion=0).
 */
public class CartaEstado extends Carta {

    /**
     * Constructor completo usado al reconstruir desde la base de datos.
     * Daño, escudo y duración son siempre 0 en cartas de estado.
     *
     * @param id_carta    Identificador en la base de datos.
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Elemento al que cambiará el estadio al jugar esta carta.
     * @param coste_mana  Coste de maná.
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaEstado(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int velocidad, String rareza) {
        super(id_carta, nombre, descripcion, id_elemento, coste_mana, 0, 0, 0, velocidad, rareza);
    }

    /**
     * Constructor de inserción (sin ID; la base de datos lo genera automáticamente).
     *
     * @param nombre      Nombre de la carta.
     * @param descripcion Descripción del efecto.
     * @param id_elemento Elemento al que cambiará el estadio al jugar esta carta.
     * @param coste_mana  Coste de maná.
     * @param velocidad   Velocidad de resolución.
     * @param rareza      Rareza de la carta.
     */
    public CartaEstado(String nombre, String descripcion, int id_elemento, int coste_mana,
            int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, 0, 0, 0, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "ESTADO";
    }
}

package modulos;

/**
 * Carta de tipo DEFENSIVA. Aplica un escudo al jugador activo con
 * un valor y una duración determinados por la propia carta.
 */
public class CartaDefensiva extends Carta {

    public CartaDefensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(id_carta, nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    public CartaDefensiva(String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "DEFENSIVA";
    }
}

package modulos;

/**
 * Carta de tipo OFENSIVA. Causa daño directo al jugador rival,
 * aplicando el multiplicador elemental del estadio activo.
 */
public class CartaOfensiva extends Carta {

    public CartaOfensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(id_carta, nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    public CartaOfensiva(String nombre, String descripcion, int id_elemento, int coste_mana,
            int dano, int escudo, int duracion, int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, dano, escudo, duracion, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "OFENSIVA";
    }
}

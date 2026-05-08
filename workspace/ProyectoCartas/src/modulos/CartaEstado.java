package modulos;

/**
 * Carta de tipo ESTADO. Cambia el elemento activo del estadio
 * al elemento de esta carta (daño=0, escudo=0, duracion=0).
 */
public class CartaEstado extends Carta {

    public CartaEstado(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana,
            int velocidad, String rareza) {
        super(id_carta, nombre, descripcion, id_elemento, coste_mana, 0, 0, 0, velocidad, rareza);
    }

    public CartaEstado(String nombre, String descripcion, int id_elemento, int coste_mana,
            int velocidad, String rareza) {
        super(nombre, descripcion, id_elemento, coste_mana, 0, 0, 0, velocidad, rareza);
    }

    @Override
    public String getTipo() {
        return "ESTADO";
    }
}

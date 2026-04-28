package modulos;

/**
 * Carta de tipo DEFENSIVA. Aplica un escudo al jugador activo con un valor y
 * una duración determinados por la propia carta.
 */
public class CartaDefensiva extends Carta {

	// ─── Constructores ───────────────────────────────────────────────────────

	/**
	 * Constructor con id — usado al leer desde la base de datos.
	 */
	public CartaDefensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana, int ataque,
			int escudo, int duracion, String rareza, String efecto) {
		super(id_carta, nombre, descripcion, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto);
	}

	/**
	 * Constructor sin id — usado al crear una carta nueva antes de insertarla en
	 * BD.
	 */
	public CartaDefensiva(String nombre, String descripcion, int id_elemento, int coste_mana, int ataque, int escudo,
			int duracion, String rareza, String efecto) {
		super(nombre, descripcion, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto);
	}

	// ─── Métodos abstractos implementados ────────────────────────────────────

	/**
	 * Devuelve siempre "DEFENSIVA" para persistencia en BD y visualización en UI.
	 */
	@Override
	public String getTipo() {
		return "DEFENSIVA";
	}

	
	@Override
	public String aplicarEfecto() {
		return "";
	}
}
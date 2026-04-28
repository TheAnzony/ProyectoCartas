package modulos;

/**
 * Carta de tipo OFENSIVA. Causa daño directo al jugador rival, aplicando el
 * multiplicador elemental y los bonificadores del estadio activo.
 */
public class CartaOfensiva extends Carta {

	// ─── Constructores ───────────────────────────────────────────────────────

	/**
	 * Constructor con id — usado al leer desde la base de datos.
	 */
	public CartaOfensiva(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana, int ataque,
			int escudo, int duracion, String rareza, String efecto) {
		super(id_carta, nombre, descripcion, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto);
	}

	/**
	 * Constructor sin id — usado al crear una carta nueva antes de insertarla en
	 * BD.
	 */
	public CartaOfensiva(String nombre, String descripcion, int id_elemento, int coste_mana, int ataque, int escudo,
			int duracion, String rareza, String efecto) {
		super(nombre, descripcion, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto);
	}

	// ─── Métodos abstractos implementados ────────────────────────────────────

	/**
	 * Devuelve siempre "OFENSIVA" para persistencia en BD y visualización en UI.
	 */
	@Override
	public String getTipo() {
		return "OFENSIVA";
	}

	
	@Override
	public String aplicarEfecto() {
		return "";
	}
}
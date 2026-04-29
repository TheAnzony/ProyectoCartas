package modulos;

/**
 * Clase abstracta que representa una carta del juego. Las subclases concretas
 * son {@link CartaOfensiva} y {@link CartaDefensiva}.
 */
public abstract class Carta {

	private int id_carta;
	private String nombre;
	private String descripcion;
	private int id_elemento;
	private int coste_mana;
	private int ataque;
	private int escudo;
	private int duracion;
	private String rareza; // COMUN, POCO_COMUN, RARA, EPICA, LEGENDARIA
	private String efecto;

	// ─── Constructor con id (lectura desde BD) ───────────────────────────────
	public Carta(int id_carta, String nombre, String descripcion, int id_elemento, int coste_mana, int ataque,
			int escudo, int duracion, String rareza, String efecto) {
		this.id_carta = id_carta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_elemento = id_elemento;
		this.coste_mana = coste_mana;
		this.ataque = ataque;
		this.escudo = escudo;
		this.duracion = duracion;
		this.rareza = rareza;
		this.efecto = efecto;
	}

	// ─── Constructor sin id (inserción nueva en BD) ──────────────────────────
	public Carta(String nombre, String descripcion, int id_elemento, int coste_mana, int ataque, int escudo,
			int duracion, String rareza, String efecto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_elemento = id_elemento;
		this.coste_mana = coste_mana;
		this.ataque = ataque;
		this.escudo = escudo;
		this.duracion = duracion;
		this.rareza = rareza;
		this.efecto = efecto;
	}

	// ─── Métodos abstractos ──────────────────────────────────────────────────

	/**
	 * Devuelve el tipo de carta como String para persistirlo en BD o mostrarlo en
	 * UI. Cada subclase devuelve su valor fijo: "OFENSIVA" o "DEFENSIVA".
	 *
	 * @return tipo de carta
	 */
	public abstract String getTipo();

	public abstract String aplicarEfecto();

	// ─── Getters y Setters ───────────────────────────────────────────────────

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

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
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

	public String getRareza() {
		return rareza;
	}

	public void setRareza(String rareza) {
		this.rareza = rareza;
	}

	public String getEfecto() {
		return efecto;
	}

	public void setEfecto(String efecto) {
		this.efecto = efecto;
	}

	// ─── toString ────────────────────────────────────────────────────────────

	@Override
	public String toString() {
		return "Carta [id_carta=" + id_carta + ", nombre=" + nombre + ", tipo=" + getTipo() + ", id_elemento="
				+ id_elemento + ", coste_mana=" + coste_mana + ", ataque=" + ataque + ", escudo=" + escudo
				+ ", duracion=" + duracion + ", rareza=" + rareza + "]";
	}
}
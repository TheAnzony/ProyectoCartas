package modulos;

/**
 * Entidad que representa la interacción entre dos elementos del juego.
 * Define el multiplicador de daño que se aplica cuando un elemento atacante
 * enfrenta a un elemento defensor (o al elemento activo del estadio).
 * Los valores se almacenan en la tabla {@code INTERACCION_ELEMENTO}.
 */
public class Interaccion_elemento {

	/** Identificador del elemento atacante. */
	private int id_elem_atacante;
	/** Identificador del elemento defensor (o del estadio activo). */
	private int id_elem_defensor;
	/** Factor multiplicador aplicado al daño (p.ej. 1.5 = ventaja, 0.5 = desventaja). */
	private double multiplicador;

	/**
	 * Crea una interacción elemental con su multiplicador de daño.
	 *
	 * @param id_elem_atacante Identificador del elemento atacante.
	 * @param id_elem_defensor Identificador del elemento defensor.
	 * @param multiplicador    Multiplicador de daño a aplicar.
	 */
	public Interaccion_elemento(int id_elem_atacante, int id_elem_defensor, double multiplicador) {
		this.id_elem_atacante = id_elem_atacante;
		this.id_elem_defensor = id_elem_defensor;
		this.multiplicador = multiplicador;
	}

	/** @return Identificador del elemento atacante. */
	public int getId_elem_atacante() {
		return id_elem_atacante;
	}

	/** @param id_elem_atacante Nuevo elemento atacante. */
	public void setId_elem_atacante(int id_elem_atacante) {
		this.id_elem_atacante = id_elem_atacante;
	}

	/** @return Identificador del elemento defensor. */
	public int getId_elem_defensor() {
		return id_elem_defensor;
	}

	/** @param id_elem_defensor Nuevo elemento defensor. */
	public void setId_elem_defensor(int id_elem_defensor) {
		this.id_elem_defensor = id_elem_defensor;
	}

	/** @return Multiplicador de daño entre los dos elementos. */
	public double getMultiplicador() {
		return multiplicador;
	}

	/** @param multiplicador Nuevo multiplicador de daño. */
	public void setMultiplicador(double multiplicador) {
		this.multiplicador = multiplicador;
	}

	@Override
	public String toString() {
		return "Interaccion_elemento [id_elem_atacante=" + id_elem_atacante
				+ ", id_elem_defensor=" + id_elem_defensor + ", multiplicador=" + multiplicador + "]";
	}
}

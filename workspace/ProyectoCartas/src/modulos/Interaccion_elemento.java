package modulos;

public class Interaccion_elemento {

	private int id_elem_atacante;
	private int id_elem_defensor;
	private double multiplicador;

	public Interaccion_elemento(int id_elem_atacante, int id_elem_defensor, double multiplicador) {
		this.id_elem_atacante = id_elem_atacante;
		this.id_elem_defensor = id_elem_defensor;
		this.multiplicador = multiplicador;
	}

	public int getId_elem_atacante() {
		return id_elem_atacante;
	}

	public void setId_elem_atacante(int id_elem_atacante) {
		this.id_elem_atacante = id_elem_atacante;
	}

	public int getId_elem_defensor() {
		return id_elem_defensor;
	}

	public void setId_elem_defensor(int id_elem_defensor) {
		this.id_elem_defensor = id_elem_defensor;
	}

	public double getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(double multiplicador) {
		this.multiplicador = multiplicador;
	}

	@Override
	public String toString() {
		return "Interaccion_elemento [id_elem_atacante=" + id_elem_atacante
				+ ", id_elem_defensor=" + id_elem_defensor + ", multiplicador=" + multiplicador + "]";
	}
}

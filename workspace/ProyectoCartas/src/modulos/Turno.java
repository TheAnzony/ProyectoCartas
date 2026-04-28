package modulos;

public class Turno {
	private int id_turno;
	private int id_partida;
	private int numero_turno;
	private int id_jugador_activo;
	private int mana_disponible;
	private int mana_gastado;
	private int vida_j1_restante;
	private int vida_j2_restante;

	public Turno(int id_turno, int id_partida, int numero_turno, int id_jugador_activo, int mana_disponible,
			int mana_gastado, int vida_j1_restante, int vida_j2_restante) {
		this.id_turno = id_turno;
		this.id_partida = id_partida;
		this.numero_turno = numero_turno;
		this.id_jugador_activo = id_jugador_activo;
		this.mana_disponible = mana_disponible;
		this.mana_gastado = mana_gastado;
		this.vida_j1_restante = vida_j1_restante;
		this.vida_j2_restante = vida_j2_restante;

	}

	public int getId_turno() {
		return id_turno;
	}

	public void setId_turno(int id_turno) {
		this.id_turno = id_turno;
	}

	public int getId_partida() {
		return id_partida;
	}

	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}

	public int getNumero_turno() {
		return numero_turno;
	}

	public void setNumero_turno(int numero_turno) {
		this.numero_turno = numero_turno;
	}

	public int getId_jugador_activo() {
		return id_jugador_activo;
	}

	public void setId_jugador_activo(int id_jugador_activo) {
		this.id_jugador_activo = id_jugador_activo;
	}

	public int getMana_disponible() {
		return mana_disponible;
	}

	public void setMana_disponible(int mana_disponible) {
		this.mana_disponible = mana_disponible;
	}

	public int getMana_gastado() {
		return mana_gastado;
	}

	public void setMana_gastado(int mana_gastado) {
		this.mana_gastado = mana_gastado;
	}

	public int getVida_j1_restante() {
		return vida_j1_restante;
	}

	public void setVida_j1_restante(int vida_j1_restante) {
		this.vida_j1_restante = vida_j1_restante;
	}

	public int getVida_j2_restante() {
		return vida_j2_restante;
	}

	public void setVida_j2_restante(int vida_j2_restante) {
		this.vida_j2_restante = vida_j2_restante;
	}

	@Override
	public String toString() {
		return "Turno [id_turno=" + id_turno + ", id_partida=" + id_partida + ", numero_turno=" + numero_turno
				+ ", id_jugador_activo=" + id_jugador_activo + ", mana_disponible=" + mana_disponible
				+ ", mana_gastado=" + mana_gastado + ", vida_j1_restante=" + vida_j1_restante + ", vida_j2_restante="
				+ vida_j2_restante + "]";
	}

}

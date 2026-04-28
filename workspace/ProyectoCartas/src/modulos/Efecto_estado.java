package modulos;

public class Efecto_estado {

	private int id_efecto;
	private int id_elemento;
	private int bonus_ataque_pct;
	private int penalty_ataque_pct;
	private int duracion_turnos;
	private String descripcion;

	public Efecto_estado(int id_efecto, int id_elemento, int bonus_ataque_pct,
			int penalty_ataque_pct, int duracion_turnos, String descripcion) {
		this.id_efecto = id_efecto;
		this.id_elemento = id_elemento;
		this.bonus_ataque_pct = bonus_ataque_pct;
		this.penalty_ataque_pct = penalty_ataque_pct;
		this.duracion_turnos = duracion_turnos;
		this.descripcion = descripcion;
	}

	public Efecto_estado(int id_elemento, int bonus_ataque_pct,
			int penalty_ataque_pct, int duracion_turnos, String descripcion) {
		this.id_elemento = id_elemento;
		this.bonus_ataque_pct = bonus_ataque_pct;
		this.penalty_ataque_pct = penalty_ataque_pct;
		this.duracion_turnos = duracion_turnos;
		this.descripcion = descripcion;
	}

	public int getId_efecto() {
		return id_efecto;
	}

	public void setId_efecto(int id_efecto) {
		this.id_efecto = id_efecto;
	}

	public int getId_elemento() {
		return id_elemento;
	}

	public void setId_elemento(int id_elemento) {
		this.id_elemento = id_elemento;
	}

	public int getBonus_ataque_pct() {
		return bonus_ataque_pct;
	}

	public void setBonus_ataque_pct(int bonus_ataque_pct) {
		this.bonus_ataque_pct = bonus_ataque_pct;
	}

	public int getPenalty_ataque_pct() {
		return penalty_ataque_pct;
	}

	public void setPenalty_ataque_pct(int penalty_ataque_pct) {
		this.penalty_ataque_pct = penalty_ataque_pct;
	}

	public int getDuracion_turnos() {
		return duracion_turnos;
	}

	public void setDuracion_turnos(int duracion_turnos) {
		this.duracion_turnos = duracion_turnos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Efecto_estado [id_efecto=" + id_efecto + ", id_elemento=" + id_elemento
				+ ", bonus_ataque_pct=" + bonus_ataque_pct + ", penalty_ataque_pct=" + penalty_ataque_pct
				+ ", duracion_turnos=" + duracion_turnos + ", descripcion=" + descripcion + "]";
	}
}

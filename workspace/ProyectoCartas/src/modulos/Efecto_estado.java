package modulos;

public class Efecto_estado {

	private int id_efecto;
	private String nombre;
	private String descripcion;
	private int id_elemento;
	private int duracion_turnos;
	private double mod_ataque;
	private double mod_defensa;
	public Efecto_estado(int id_efecto, String nombre, String descripcion, int id_elemento, int duracion_turnos,
			double mod_ataque, double mod_defensa) {
		super();
		this.id_efecto = id_efecto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_elemento = id_elemento;
		this.duracion_turnos = duracion_turnos;
		this.mod_ataque = mod_ataque;
		this.mod_defensa = mod_defensa;
	}
	public Efecto_estado(String nombre, String descripcion, int id_elemento, int duracion_turnos, double mod_ataque,
			double mod_defensa) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_elemento = id_elemento;
		this.duracion_turnos = duracion_turnos;
		this.mod_ataque = mod_ataque;
		this.mod_defensa = mod_defensa;
	}
	public int getId_efecto() {
		return id_efecto;
	}
	public void setId_efecto(int id_efecto) {
		this.id_efecto = id_efecto;
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
	public int getDuracion_turnos() {
		return duracion_turnos;
	}
	public void setDuracion_turnos(int duracion_turnos) {
		this.duracion_turnos = duracion_turnos;
	}
	public double getMod_ataque() {
		return mod_ataque;
	}
	public void setMod_ataque(double mod_ataque) {
		this.mod_ataque = mod_ataque;
	}
	public double getMod_defensa() {
		return mod_defensa;
	}
	public void setMod_defensa(double mod_defensa) {
		this.mod_defensa = mod_defensa;
	}
	@Override
	public String toString() {
		return "Efecto_estado [id_efecto=" + id_efecto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", id_elemento=" + id_elemento + ", duracion_turnos=" + duracion_turnos + ", mod_ataque=" + mod_ataque
				+ ", mod_defensa=" + mod_defensa + "]";
	}
	
	
}

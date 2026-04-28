package modulos;

import java.time.LocalDate;

public class Mazo {

	private int id_mazo;
	private int id_jugador;
	private String nombre;
	private LocalDate fecha_creacion;

	public Mazo(int id_mazo, int id_jugador, String nombre, LocalDate fecha_creacion) {
		this.id_mazo = id_mazo;
		this.id_jugador = id_jugador;
		this.nombre = nombre;
		this.fecha_creacion = fecha_creacion;
	}

	public Mazo(int id_jugador, String nombre, LocalDate fecha_creacion) {
		this.id_jugador = id_jugador;
		this.nombre = nombre;
		this.fecha_creacion = fecha_creacion;
	}

	public int getId_mazo() {
		return id_mazo;
	}

	public void setId_mazo(int id_mazo) {
		this.id_mazo = id_mazo;
	}

	public int getId_jugador() {
		return id_jugador;
	}

	public void setId_jugador(int id_jugador) {
		this.id_jugador = id_jugador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	@Override
	public String toString() {
		return "Mazo [id_mazo=" + id_mazo + ", id_jugador=" + id_jugador
				+ ", nombre=" + nombre + ", fecha_creacion=" + fecha_creacion + "]";
	}
}

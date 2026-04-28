package modulos;

import java.time.LocalDate;

public class Jugador {

	private int id_jugador;
	private String nombre;
	private String apellidos;
	private String email;
	private LocalDate fecha_registro;
	private int puntuacion_total;

	public Jugador(int id_jugador, String nombre, String apellidos, String email,
			LocalDate fecha_registro, int puntuacion_total) {
		this.id_jugador = id_jugador;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.fecha_registro = fecha_registro;
		this.puntuacion_total = puntuacion_total;
	}

	public Jugador(String nombre, String apellidos, String email) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.fecha_registro = LocalDate.now();
		this.puntuacion_total = 0;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(LocalDate fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public int getPuntuacion_total() {
		return puntuacion_total;
	}

	public void setPuntuacion_total(int puntuacion_total) {
		this.puntuacion_total = puntuacion_total;
	}

	@Override
	public String toString() {
		return "Jugador [id_jugador=" + id_jugador + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", email=" + email + ", fecha_registro=" + fecha_registro
				+ ", puntuacion_total=" + puntuacion_total + "]";
	}
}

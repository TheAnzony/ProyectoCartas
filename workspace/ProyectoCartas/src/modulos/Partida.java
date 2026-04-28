package modulos;

import java.time.LocalDateTime;

public class Partida {

	private int id_partida;
	private int id_jugador1;
	private int id_jugador2;
	private int id_mazo_j1;
	private int id_mazo_j2;
	private int id_estadio;
	private LocalDateTime fecha_inicio;
	private LocalDateTime fecha_fin;       // nullable: null si la partida está en curso
	private Integer id_ganador;            // nullable: null si la partida está en curso
	private int turnos_totales;
	private double velocidad_j1;
	private double velocidad_j2;

	public Partida(int id_partida, int id_jugador1, int id_jugador2, int id_mazo_j1, int id_mazo_j2,
			int id_estadio, LocalDateTime fecha_inicio, LocalDateTime fecha_fin,
			Integer id_ganador, int turnos_totales, double velocidad_j1, double velocidad_j2) {
		this.id_partida = id_partida;
		this.id_jugador1 = id_jugador1;
		this.id_jugador2 = id_jugador2;
		this.id_mazo_j1 = id_mazo_j1;
		this.id_mazo_j2 = id_mazo_j2;
		this.id_estadio = id_estadio;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.id_ganador = id_ganador;
		this.turnos_totales = turnos_totales;
		this.velocidad_j1 = velocidad_j1;
		this.velocidad_j2 = velocidad_j2;
	}

	public int getId_partida() {
		return id_partida;
	}

	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}

	public int getId_jugador1() {
		return id_jugador1;
	}

	public void setId_jugador1(int id_jugador1) {
		this.id_jugador1 = id_jugador1;
	}

	public int getId_jugador2() {
		return id_jugador2;
	}

	public void setId_jugador2(int id_jugador2) {
		this.id_jugador2 = id_jugador2;
	}

	public int getId_mazo_j1() {
		return id_mazo_j1;
	}

	public void setId_mazo_j1(int id_mazo_j1) {
		this.id_mazo_j1 = id_mazo_j1;
	}

	public int getId_mazo_j2() {
		return id_mazo_j2;
	}

	public void setId_mazo_j2(int id_mazo_j2) {
		this.id_mazo_j2 = id_mazo_j2;
	}

	public int getId_estadio() {
		return id_estadio;
	}

	public void setId_estadio(int id_estadio) {
		this.id_estadio = id_estadio;
	}

	public LocalDateTime getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(LocalDateTime fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public LocalDateTime getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(LocalDateTime fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public Integer getId_ganador() {
		return id_ganador;
	}

	public void setId_ganador(Integer id_ganador) {
		this.id_ganador = id_ganador;
	}

	public int getTurnos_totales() {
		return turnos_totales;
	}

	public void setTurnos_totales(int turnos_totales) {
		this.turnos_totales = turnos_totales;
	}

	public double getVelocidad_j1() {
		return velocidad_j1;
	}

	public void setVelocidad_j1(double velocidad_j1) {
		this.velocidad_j1 = velocidad_j1;
	}

	public double getVelocidad_j2() {
		return velocidad_j2;
	}

	public void setVelocidad_j2(double velocidad_j2) {
		this.velocidad_j2 = velocidad_j2;
	}

	@Override
	public String toString() {
		return "Partida [id_partida=" + id_partida + ", id_jugador1=" + id_jugador1
				+ ", id_jugador2=" + id_jugador2 + ", id_mazo_j1=" + id_mazo_j1
				+ ", id_mazo_j2=" + id_mazo_j2 + ", id_estadio=" + id_estadio
				+ ", fecha_inicio=" + fecha_inicio + ", fecha_fin=" + fecha_fin
				+ ", id_ganador=" + id_ganador + ", turnos_totales=" + turnos_totales
				+ ", velocidad_j1=" + velocidad_j1 + ", velocidad_j2=" + velocidad_j2 + "]";
	}
}

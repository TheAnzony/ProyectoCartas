package modulos;

import java.time.LocalDateTime;

/**
 * Entidad que representa una partida disputada entre dos jugadores.
 * Almacena la configuración inicial (jugadores, mazos, estadio, fecha) y
 * el resultado final (ganador y número de turnos).
 */
public class Partida {

	/** Identificador único en la base de datos. */
	private int id_partida;
	/** Identificador del primer jugador. */
	private int id_jugador1;
	/** Identificador del segundo jugador. */
	private int id_jugador2;
	/** Identificador del mazo usado por el jugador 1. */
	private int id_mazo_j1;
	/** Identificador del mazo usado por el jugador 2. */
	private int id_mazo_j2;
	/** Identificador del estadio donde se disputó la partida. */
	private int id_estadio;
	/** Fecha y hora de inicio de la partida. */
	private LocalDateTime fecha;
	/** Identificador del jugador ganador, o {@code null} si la partida no ha terminado. */
	private Integer id_ganador;
	/** Número de turnos jugados. */
	private int num_turnos;

	/**
	 * Constructor completo usado al reconstruir una partida desde la base de datos.
	 *
	 * @param id_partida  Identificador en la base de datos.
	 * @param id_jugador1 Identificador del jugador 1.
	 * @param id_jugador2 Identificador del jugador 2.
	 * @param id_mazo_j1  Identificador del mazo del jugador 1.
	 * @param id_mazo_j2  Identificador del mazo del jugador 2.
	 * @param id_estadio  Identificador del estadio.
	 * @param fecha       Fecha y hora de inicio.
	 * @param id_ganador  Identificador del ganador, o {@code null} si no ha terminado.
	 * @param num_turnos  Número de turnos jugados.
	 */
	public Partida(int id_partida, int id_jugador1, int id_jugador2, int id_mazo_j1, int id_mazo_j2, int id_estadio,
			LocalDateTime fecha, Integer id_ganador, int num_turnos) {
		this.id_partida = id_partida;
		this.id_jugador1 = id_jugador1;
		this.id_jugador2 = id_jugador2;
		this.id_mazo_j1 = id_mazo_j1;
		this.id_mazo_j2 = id_mazo_j2;
		this.id_estadio = id_estadio;
		this.fecha = fecha;
		this.id_ganador = id_ganador;
		this.num_turnos = num_turnos;
	}

	/**
	 * Constructor de inserción. Asigna la fecha actual, ganador nulo y 0 turnos.
	 *
	 * @param id_jugador1 Identificador del jugador 1.
	 * @param id_jugador2 Identificador del jugador 2.
	 * @param id_mazo_j1  Identificador del mazo del jugador 1.
	 * @param id_mazo_j2  Identificador del mazo del jugador 2.
	 * @param id_estadio  Identificador del estadio.
	 */
	public Partida(int id_jugador1, int id_jugador2, int id_mazo_j1, int id_mazo_j2, int id_estadio) {
		this.id_jugador1 = id_jugador1;
		this.id_jugador2 = id_jugador2;
		this.id_mazo_j1 = id_mazo_j1;
		this.id_mazo_j2 = id_mazo_j2;
		this.id_estadio = id_estadio;
		this.fecha = LocalDateTime.now();
		this.id_ganador = null;
		this.num_turnos = 0;
	}

	/** @return Identificador único de la partida. */
	public int getId_partida() {
		return id_partida;
	}

	/** @param id_partida Nuevo identificador. */
	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}

	/** @return Identificador del jugador 1. */
	public int getId_jugador1() {
		return id_jugador1;
	}

	/** @param id_jugador1 Nuevo jugador 1. */
	public void setId_jugador1(int id_jugador1) {
		this.id_jugador1 = id_jugador1;
	}

	/** @return Identificador del jugador 2. */
	public int getId_jugador2() {
		return id_jugador2;
	}

	/** @param id_jugador2 Nuevo jugador 2. */
	public void setId_jugador2(int id_jugador2) {
		this.id_jugador2 = id_jugador2;
	}

	/** @return Identificador del mazo del jugador 1. */
	public int getId_mazo_j1() {
		return id_mazo_j1;
	}

	/** @param id_mazo_j1 Nuevo mazo del jugador 1. */
	public void setId_mazo_j1(int id_mazo_j1) {
		this.id_mazo_j1 = id_mazo_j1;
	}

	/** @return Identificador del mazo del jugador 2. */
	public int getId_mazo_j2() {
		return id_mazo_j2;
	}

	/** @param id_mazo_j2 Nuevo mazo del jugador 2. */
	public void setId_mazo_j2(int id_mazo_j2) {
		this.id_mazo_j2 = id_mazo_j2;
	}

	/** @return Identificador del estadio de la partida. */
	public int getId_estadio() {
		return id_estadio;
	}

	/** @param id_estadio Nuevo estadio. */
	public void setId_estadio(int id_estadio) {
		this.id_estadio = id_estadio;
	}

	/** @return Fecha y hora de inicio de la partida. */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/** @param fecha Nueva fecha de inicio. */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/** @return Identificador del jugador ganador, o {@code null} si la partida no ha terminado. */
	public Integer getId_ganador() {
		return id_ganador;
	}

	/** @param id_ganador Identificador del ganador (puede ser {@code null}). */
	public void setId_ganador(Integer id_ganador) {
		this.id_ganador = id_ganador;
	}

	/** @return Número total de turnos jugados en la partida. */
	public int getNum_turnos() {
		return num_turnos;
	}

	/** @param num_turnos Nuevo número de turnos. */
	public void setNum_turnos(int num_turnos) {
		this.num_turnos = num_turnos;
	}

	@Override
	public String toString() {
		return "Partida [id_partida=" + id_partida + ", id_jugador1=" + id_jugador1 + ", id_jugador2=" + id_jugador2
				+ ", id_mazo_j1=" + id_mazo_j1 + ", id_mazo_j2=" + id_mazo_j2 + ", id_estadio=" + id_estadio
				+ ", fecha=" + fecha + ", id_ganador=" + id_ganador + ", num_turnos=" + num_turnos + "]";
	}

}

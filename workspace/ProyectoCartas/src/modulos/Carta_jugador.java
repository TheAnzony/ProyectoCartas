package modulos;

import java.time.LocalDateTime;

public class Carta_jugador {

	private int id_jugador;
	private int id_carta;
	private LocalDateTime fecha_obtencion; // REVISAR
	private int cantidad;

	public Carta_jugador(int id_jugador, int id_carta, LocalDateTime fecha_obtencion, int cantidad) {
		super();
		this.id_jugador = id_jugador;
		this.id_carta = id_carta;
		this.fecha_obtencion = fecha_obtencion;
		this.cantidad = cantidad;
	}

	public Carta_jugador(int id_carta, LocalDateTime fecha_obtencion, int cantidad) {
		super();
		this.id_carta = id_carta;
		this.fecha_obtencion = fecha_obtencion;
		this.cantidad = cantidad;
	}

	public int getId_jugador() {
		return id_jugador;
	}

	public void setId_jugador(int id_jugador) {
		this.id_jugador = id_jugador;
	}

	public int getId_carta() {
		return id_carta;
	}

	public void setId_carta(int id_carta) {
		this.id_carta = id_carta;
	}

	public LocalDateTime getFecha_obtencion() {
		return fecha_obtencion;
	}

	public void setFecha_obtencion(LocalDateTime fecha_obtencion) {
		this.fecha_obtencion = fecha_obtencion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Carta_jugador [id_jugador=" + id_jugador + ", id_carta=" + id_carta + ", fecha_obtencion="
				+ fecha_obtencion + ", cantidad=" + cantidad + "]";
	}

}

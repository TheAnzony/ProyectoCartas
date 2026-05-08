package modulos;

import java.time.LocalDate;

public class Carta_jugador {

    private int id_jugador;
    private int id_carta;
    private LocalDate fecha_obtencion;

    public Carta_jugador(int id_jugador, int id_carta, LocalDate fecha_obtencion) {
        this.id_jugador = id_jugador;
        this.id_carta = id_carta;
        this.fecha_obtencion = fecha_obtencion;
    }

    public Carta_jugador(int id_carta, LocalDate fecha_obtencion) {
        this.id_carta = id_carta;
        this.fecha_obtencion = fecha_obtencion;
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

    public LocalDate getFecha_obtencion() {
        return fecha_obtencion;
    }

    public void setFecha_obtencion(LocalDate fecha_obtencion) {
        this.fecha_obtencion = fecha_obtencion;
    }

    @Override
    public String toString() {
        return "Carta_jugador [id_jugador=" + id_jugador + ", id_carta=" + id_carta
                + ", fecha_obtencion=" + fecha_obtencion + "]";
    }
}

package modulos;

import java.time.LocalDateTime;

public class Partida {

    private int id_partida;
    private int id_jugador1;
    private int id_jugador2;
    private int id_mazo_j1;
    private int id_mazo_j2;
    private int id_estadio;
    private LocalDateTime fecha;
    private Integer id_ganador;
    private int num_turnos;

    public Partida(int id_partida, int id_jugador1, int id_jugador2, int id_mazo_j1, int id_mazo_j2,
            int id_estadio, LocalDateTime fecha, Integer id_ganador, int num_turnos) {
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getId_ganador() {
        return id_ganador;
    }

    public void setId_ganador(Integer id_ganador) {
        this.id_ganador = id_ganador;
    }

    public int getNum_turnos() {
        return num_turnos;
    }

    public void setNum_turnos(int num_turnos) {
        this.num_turnos = num_turnos;
    }

    @Override
    public String toString() {
        return "Partida [id_partida=" + id_partida + ", id_jugador1=" + id_jugador1
                + ", id_jugador2=" + id_jugador2 + ", id_mazo_j1=" + id_mazo_j1
                + ", id_mazo_j2=" + id_mazo_j2 + ", id_estadio=" + id_estadio
                + ", fecha=" + fecha + ", id_ganador=" + id_ganador
                + ", num_turnos=" + num_turnos + "]";
    }
}

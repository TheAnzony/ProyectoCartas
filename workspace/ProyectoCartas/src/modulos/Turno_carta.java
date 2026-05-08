package modulos;

public class Turno_carta {

    private int id_turno_carta;
    private int id_turno;
    private int id_jugador;
    private int id_carta;
    private int dano_real;
    private int orden_resolucion;

    public Turno_carta(int id_turno_carta, int id_turno, int id_jugador, int id_carta,
            int dano_real, int orden_resolucion) {
        this.id_turno_carta = id_turno_carta;
        this.id_turno = id_turno;
        this.id_jugador = id_jugador;
        this.id_carta = id_carta;
        this.dano_real = dano_real;
        this.orden_resolucion = orden_resolucion;
    }

    public Turno_carta(int id_turno, int id_jugador, int id_carta, int dano_real, int orden_resolucion) {
        this.id_turno = id_turno;
        this.id_jugador = id_jugador;
        this.id_carta = id_carta;
        this.dano_real = dano_real;
        this.orden_resolucion = orden_resolucion;
    }

    public int getId_turno_carta() {
        return id_turno_carta;
    }

    public void setId_turno_carta(int id_turno_carta) {
        this.id_turno_carta = id_turno_carta;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
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

    public int getDano_real() {
        return dano_real;
    }

    public void setDano_real(int dano_real) {
        this.dano_real = dano_real;
    }

    public int getOrden_resolucion() {
        return orden_resolucion;
    }

    public void setOrden_resolucion(int orden_resolucion) {
        this.orden_resolucion = orden_resolucion;
    }

    @Override
    public String toString() {
        return "Turno_carta [id_turno_carta=" + id_turno_carta + ", id_turno=" + id_turno
                + ", id_jugador=" + id_jugador + ", id_carta=" + id_carta
                + ", dano_real=" + dano_real + ", orden_resolucion=" + orden_resolucion + "]";
    }
}

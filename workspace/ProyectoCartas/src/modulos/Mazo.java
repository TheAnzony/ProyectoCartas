package modulos;

public class Mazo {

    private int id_mazo;
    private int id_jugador;
    private String nombre;

    public Mazo(int id_mazo, int id_jugador, String nombre) {
        this.id_mazo = id_mazo;
        this.id_jugador = id_jugador;
        this.nombre = nombre;
    }

    public Mazo(int id_jugador, String nombre) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Mazo [id_mazo=" + id_mazo + ", id_jugador=" + id_jugador + ", nombre=" + nombre + "]";
    }
}

package modulos;

import java.time.LocalDate;

/**
 * Entidad que representa a un jugador del juego.
 * Almacena la información personal, el apodo visible en partida y la puntuación MMR.
 */
public class Jugador {

    /** Identificador único en la base de datos. */
    private int id_jugador;
    /** Nombre real del jugador. */
    private String nombre;
    /** Apellidos del jugador. */
    private String apellidos;
    /** Correo electrónico (único en la base de datos). */
    private String email;
    /** Nombre visible en partida. */
    private String apodo;
    /** Fecha en que el jugador se registró. */
    private LocalDate fecha_registro;
    /** Puntuación de matchmaking (base 1000). */
    private int MMR;

    /**
     * Constructor completo usado al reconstruir un jugador desde la base de datos.
     *
     * @param id_jugador     Identificador en la base de datos.
     * @param nombre         Nombre real.
     * @param apellidos      Apellidos.
     * @param email          Correo electrónico único.
     * @param apodo          Apodo visible en partida.
     * @param fecha_registro Fecha de registro.
     * @param MMR            Puntuación de matchmaking.
     */
    public Jugador(int id_jugador, String nombre, String apellidos, String email,
            String apodo, LocalDate fecha_registro, int MMR) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.apodo = apodo;
        this.fecha_registro = fecha_registro;
        this.MMR = MMR;
    }

    /**
     * Constructor de registro. Asigna la fecha actual y un MMR base de 1000.
     *
     * @param nombre    Nombre real.
     * @param apellidos Apellidos.
     * @param email     Correo electrónico único.
     * @param apodo     Apodo visible en partida.
     */
    public Jugador(String nombre, String apellidos, String email, String apodo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.apodo = apodo;
        this.fecha_registro = LocalDate.now();
        this.MMR = 1000;
    }

    /** @return Identificador único del jugador. */
    public int getId_jugador() {
        return id_jugador;
    }

    /** @param id_jugador Nuevo identificador. */
    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    /** @return Nombre real del jugador. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre real. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Apellidos del jugador. */
    public String getApellidos() {
        return apellidos;
    }

    /** @param apellidos Nuevos apellidos. */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /** @return Correo electrónico del jugador. */
    public String getEmail() {
        return email;
    }

    /** @param email Nuevo correo electrónico. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return Apodo visible en partida. */
    public String getApodo() {
        return apodo;
    }

    /** @param apodo Nuevo apodo. */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    /** @return Fecha de registro del jugador. */
    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    /** @param fecha_registro Nueva fecha de registro. */
    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    /** @return Puntuación de matchmaking (MMR) del jugador. */
    public int getMMR() {
        return MMR;
    }

    /** @param MMR Nueva puntuación de matchmaking. */
    public void setMMR(int MMR) {
        this.MMR = MMR;
    }

    /** Devuelve el apodo, lo que permite que Swing lo pinte directamente en listas y combos. */
    @Override
    public String toString() {
        return this.apodo;
    }
}

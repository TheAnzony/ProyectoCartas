package modulos;

import java.time.LocalDate;

public class Jugador {

    private int id_jugador;
    private String nombre;
    private String apellidos;
    private String email;
    private String apodo;
    private LocalDate fecha_registro;
    private int MMR;

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

    public Jugador(String nombre, String apellidos, String email, String apodo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.apodo = apodo;
        this.fecha_registro = LocalDate.now();
        this.MMR = 1000;
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

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getMMR() {
        return MMR;
    }

    public void setMMR(int MMR) {
        this.MMR = MMR;
    }

    
    @Override
    public String toString() {
        return this.apodo; // Esto hace que Swing pinte el apodo en el menú
    }
}

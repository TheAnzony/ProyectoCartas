package modulos;

public class Turno {

    private int id_turno;
    private int id_partida;
    private int numero_turno;
    private int vida_j1;
    private int vida_j2;
    private int mana_disponible;
    private int id_elemento_activo;
    private int id_jugador_primero;
    private double media_velocidad_j1;
    private double media_velocidad_j2;

    public Turno(int id_turno, int id_partida, int numero_turno, int vida_j1, int vida_j2,
            int mana_disponible, int id_elemento_activo, int id_jugador_primero,
            double media_velocidad_j1, double media_velocidad_j2) {
        this.id_turno = id_turno;
        this.id_partida = id_partida;
        this.numero_turno = numero_turno;
        this.vida_j1 = vida_j1;
        this.vida_j2 = vida_j2;
        this.mana_disponible = mana_disponible;
        this.id_elemento_activo = id_elemento_activo;
        this.id_jugador_primero = id_jugador_primero;
        this.media_velocidad_j1 = media_velocidad_j1;
        this.media_velocidad_j2 = media_velocidad_j2;
    }

    public Turno(int id_partida, int numero_turno, int vida_j1, int vida_j2,
            int mana_disponible, int id_elemento_activo, int id_jugador_primero,
            double media_velocidad_j1, double media_velocidad_j2) {
        this.id_partida = id_partida;
        this.numero_turno = numero_turno;
        this.vida_j1 = vida_j1;
        this.vida_j2 = vida_j2;
        this.mana_disponible = mana_disponible;
        this.id_elemento_activo = id_elemento_activo;
        this.id_jugador_primero = id_jugador_primero;
        this.media_velocidad_j1 = media_velocidad_j1;
        this.media_velocidad_j2 = media_velocidad_j2;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public int getNumero_turno() {
        return numero_turno;
    }

    public void setNumero_turno(int numero_turno) {
        this.numero_turno = numero_turno;
    }

    public int getVida_j1() {
        return vida_j1;
    }

    public void setVida_j1(int vida_j1) {
        this.vida_j1 = vida_j1;
    }

    public int getVida_j2() {
        return vida_j2;
    }

    public void setVida_j2(int vida_j2) {
        this.vida_j2 = vida_j2;
    }

    public int getMana_disponible() {
        return mana_disponible;
    }

    public void setMana_disponible(int mana_disponible) {
        this.mana_disponible = mana_disponible;
    }

    public int getId_elemento_activo() {
        return id_elemento_activo;
    }

    public void setId_elemento_activo(int id_elemento_activo) {
        this.id_elemento_activo = id_elemento_activo;
    }

    public int getId_jugador_primero() {
        return id_jugador_primero;
    }

    public void setId_jugador_primero(int id_jugador_primero) {
        this.id_jugador_primero = id_jugador_primero;
    }

    public double getMedia_velocidad_j1() {
        return media_velocidad_j1;
    }

    public void setMedia_velocidad_j1(double media_velocidad_j1) {
        this.media_velocidad_j1 = media_velocidad_j1;
    }

    public double getMedia_velocidad_j2() {
        return media_velocidad_j2;
    }

    public void setMedia_velocidad_j2(double media_velocidad_j2) {
        this.media_velocidad_j2 = media_velocidad_j2;
    }

    @Override
    public String toString() {
        return "Turno [id_turno=" + id_turno + ", id_partida=" + id_partida + ", numero_turno=" + numero_turno
                + ", vida_j1=" + vida_j1 + ", vida_j2=" + vida_j2 + ", mana_disponible=" + mana_disponible
                + ", id_elemento_activo=" + id_elemento_activo + ", id_jugador_primero=" + id_jugador_primero
                + ", media_velocidad_j1=" + media_velocidad_j1 + ", media_velocidad_j2=" + media_velocidad_j2 + "]";
    }
}

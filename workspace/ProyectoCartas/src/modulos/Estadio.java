package modulos;

public class Estadio {

    private int id_estadio;
    private String nombre;
    private String descripcion;
    private int id_elemento_inicial;
    private int id_elemento_activo;

    public Estadio(int id_estadio, String nombre, String descripcion, int id_elemento_inicial, int id_elemento_activo) {
        this.id_estadio = id_estadio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento_inicial = id_elemento_inicial;
        this.id_elemento_activo = id_elemento_activo;
    }

    public Estadio(String nombre, String descripcion, int id_elemento_inicial, int id_elemento_activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_elemento_inicial = id_elemento_inicial;
        this.id_elemento_activo = id_elemento_activo;
    }

    public int getId_estadio() {
        return id_estadio;
    }

    public void setId_estadio(int id_estadio) {
        this.id_estadio = id_estadio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_elemento_inicial() {
        return id_elemento_inicial;
    }

    public void setId_elemento_inicial(int id_elemento_inicial) {
        this.id_elemento_inicial = id_elemento_inicial;
    }

    public int getId_elemento_activo() {
        return id_elemento_activo;
    }

    public void setId_elemento_activo(int id_elemento_activo) {
        this.id_elemento_activo = id_elemento_activo;
    }

    @Override
    public String toString() {
        return "Estadio [id_estadio=" + id_estadio + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", id_elemento_inicial=" + id_elemento_inicial + ", id_elemento_activo=" + id_elemento_activo + "]";
    }
}

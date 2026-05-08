package modulos;

public class Elemento {

    private int id_elemento;
    private String nombre;
    private String descripcion;

    public Elemento(int id_elemento, String nombre, String descripcion) {
        this.id_elemento = id_elemento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Elemento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_elemento() {
        return id_elemento;
    }

    public void setId_elemento(int id_elemento) {
        this.id_elemento = id_elemento;
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

    @Override
    public String toString() {
        return "Elemento [id_elemento=" + id_elemento + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }
}

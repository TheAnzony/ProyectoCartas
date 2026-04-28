package modulos;

public class Elemento {

	private int id_elemento;
	private String nombre;
	private String descripcion;
	private String color_hex;



	public Elemento(int id_elemento, String nombre, String descripcion, String color_hex) {
		this.id_elemento = id_elemento;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.color_hex = color_hex;
	}

	public Elemento(String nombre, String descripcion, String color_hex) {

		this.nombre = nombre;
		this.descripcion = descripcion;
		this.color_hex = color_hex;
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

	public String getColor_hex() {
		return color_hex;
	}

	public void setColor_hex(String color_hex) {
		this.color_hex = color_hex;
	}

	@Override
	public String toString() {
		return "Elemento [id_elemento=" + id_elemento + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", color_hex=" + color_hex + "]";
	}

}

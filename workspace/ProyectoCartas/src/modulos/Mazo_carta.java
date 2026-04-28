package modulos;

public class Mazo_carta {

	private int id_mazo;
	private int id_carta;
	private int cantidad;

	public Mazo_carta(int id_mazo, int id_carta, int cantidad) {
		super();
		this.id_mazo = id_mazo;
		this.id_carta = id_carta;
		this.cantidad = cantidad;
	}

	public Mazo_carta(int cantidad) {
		super();
		this.cantidad = cantidad;
	}

	public int getId_mazo() {
		return id_mazo;
	}

	public void setId_mazo(int id_mazo) {
		this.id_mazo = id_mazo;
	}

	public int getId_carta() {
		return id_carta;
	}

	public void setId_carta(int id_carta) {
		this.id_carta = id_carta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Mazo_carta [id_mazo=" + id_mazo + ", id_carta=" + id_carta + ", cantidad=" + cantidad + "]";
	}

}

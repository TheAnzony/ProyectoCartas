package modulos;

public class Turno_carta {

	private int id_turno;
	private int id_carta;
	private int orden_juego;
	private String tipo_accion; // REVISAR CON BDD
	private int dano_causado;
	private String resultado;
	public Turno_carta(int id_turno, int id_carta, int orden_juego, String tipo_accion, int dano_causado,
			String resultado) {
		super();
		this.id_turno = id_turno;
		this.id_carta = id_carta;
		this.orden_juego = orden_juego;
		this.tipo_accion = tipo_accion;
		this.dano_causado = dano_causado;
		this.resultado = resultado;
	}
	public int getId_turno() {
		return id_turno;
	}
	public void setId_turno(int id_turno) {
		this.id_turno = id_turno;
	}
	public int getId_carta() {
		return id_carta;
	}
	public void setId_carta(int id_carta) {
		this.id_carta = id_carta;
	}
	public int getOrden_juego() {
		return orden_juego;
	}
	public void setOrden_juego(int orden_juego) {
		this.orden_juego = orden_juego;
	}
	public String getTipo_accion() {
		return tipo_accion;
	}
	public void setTipo_accion(String tipo_accion) {
		this.tipo_accion = tipo_accion;
	}
	public int getDano_causado() {
		return dano_causado;
	}
	public void setDano_causado(int dano_causado) {
		this.dano_causado = dano_causado;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	@Override
	public String toString() {
		return "Turno_carta [id_turno=" + id_turno + ", id_carta=" + id_carta + ", orden_juego=" + orden_juego
				+ ", tipo_accion=" + tipo_accion + ", dano_causado=" + dano_causado + ", resultado=" + resultado + "]";
	}

	
}

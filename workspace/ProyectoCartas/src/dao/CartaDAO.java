package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modulos.Carta;
import modulos.CartaDefensiva;
import modulos.CartaOfensiva;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Carta}.
 *
 * Gestiona las operaciones CRUD sobre la tabla {@code CARTA} de la base de datos.
 * Dado que {@link Carta} es abstracta, instancia {@link CartaOfensiva} o {@link CartaDefensiva}
 * según el campo {@code tipo} almacenado en BD.
 */
public class CartaDAO {

	/**
	 * Constructor vacío para instanciar el DAO.
	 */
	public CartaDAO() {

	}

	/**
	 * Construye un objeto {@link Carta} concreto a partir de una fila del {@link ResultSet}.
	 * Devuelve {@link CartaOfensiva} o {@link CartaDefensiva} según el campo {@code tipo}.
	 *
	 * @param rs {@link ResultSet} posicionado en la fila a leer.
	 * @return Objeto {@link Carta} concreto, o {@code null} si ocurre un error.
	 */
	private Carta construirCarta(ResultSet rs) {

		try {
			int id = rs.getInt("id_carta");
			String nombre = rs.getString("nombre");
			String descripcion = rs.getString("descripcion");
			String tipo = rs.getString("tipo");
			int idElemento = rs.getInt("id_elemento");
			int costeMana = rs.getInt("coste_mana");
			int ataque = rs.getInt("ataque");
			int escudo = rs.getInt("escudo");
			int duracion = rs.getInt("duracion");
			String rareza = rs.getString("rareza");
			String efecto = rs.getString("efecto");

			switch (tipo) {
			case "OFENSIVA":
				return new CartaOfensiva(id, nombre, descripcion, idElemento, costeMana, ataque, escudo, duracion,
						rareza, efecto);
			case "DEFENSIVA":
				return new CartaDefensiva(id, nombre, descripcion, idElemento, costeMana, ataque, escudo, duracion,
						rareza, efecto);
			default:
				throw new IllegalArgumentException("Tipo de carta desconocido: " + tipo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Inserta una nueva carta en la base de datos.
	 *
	 * @param c Objeto {@link Carta} con los datos a insertar.
	 * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
	 */
	public boolean insertar(Carta c) {

		String sql = "INSERT INTO carta (id_carta, nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, c.getId_carta());
			ps.setString(2, c.getNombre());
			ps.setString(3, c.getDescripcion());
			ps.setString(4, c.getTipo());
			ps.setInt(5, c.getId_elemento());
			ps.setInt(6, c.getCoste_mana());
			ps.setInt(7, c.getAtaque());
			ps.setInt(8, c.getEscudo());
			ps.setInt(9, c.getDuracion());
			ps.setString(10, c.getRareza());
			ps.setString(11, c.getEfecto());

			int resultado = ps.executeUpdate();

			System.out.println(resultado > 0 ? "Carta insertada correctamente" : "No se ha podido insertar la carta");
			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina una carta de la base de datos por su identificador.
	 *
	 * @param id Identificador de la carta a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM carta WHERE id_carta = ?";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id);

			int resultado = ps.executeUpdate();

			System.out.println(resultado > 0 ? "Carta eliminada con exito" : "No se ha podido eliminar la carta");
			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Busca una carta en la base de datos por su identificador.
	 *
	 * @param id Identificador de la carta a buscar.
	 * @return Objeto {@link Carta} concreto, o {@code null} si no existe.
	 */
	public Carta buscar(int id) {
		String sql = "SELECT * FROM carta WHERE id_carta = ?";

		Carta c = null;

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				c = construirCarta(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Busca una carta en la base de datos por su nombre.
	 *
	 * @param nombre Nombre exacto de la carta a buscar.
	 * @return Objeto {@link Carta} concreto, o {@code null} si no existe.
	 */
	public Carta buscar(String nombre) {
		String sql = "SELECT * FROM carta WHERE nombre = ?";

		Carta c = null;

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setString(1, nombre);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				c = construirCarta(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Recupera todas las cartas de la base de datos.
	 *
	 * @return Lista de objetos {@link Carta}. Vacía si no hay ninguna.
	 */
	public List<Carta> listar() {
		String sql = "SELECT * FROM carta";

		Carta c = null;
		List<Carta> lista = new ArrayList<Carta>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				c = construirCarta(rs);
				if (c != null)
					lista.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;

	}

	/**
	 * Actualiza los datos de una carta existente en la base de datos.
	 *
	 * @param carta carta con los nuevos valores (debe tener id_carta válido)
	 * @return true si se actualizó correctamente, false si no se encontró la carta
	 */
	public boolean actualizarCarta(Carta carta) {
		String sql = "UPDATE CARTA SET nombre = ?, descripcion = ?, tipo = ?, "
				+ "id_elemento = ?, coste_mana = ?, ataque = ?, escudo = ?, " + "duracion = ?, rareza = ?, efecto = ? "
				+ "WHERE id_carta = ?";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setString(1, carta.getNombre());
			ps.setString(2, carta.getDescripcion());
			ps.setString(3, carta.getTipo());
			ps.setInt(4, carta.getId_elemento());
			ps.setInt(5, carta.getCoste_mana());
			ps.setInt(6, carta.getAtaque());
			ps.setInt(7, carta.getEscudo());
			ps.setInt(8, carta.getDuracion());
			ps.setString(9, carta.getRareza());
			ps.setString(10, carta.getEfecto());
			ps.setInt(11, carta.getId_carta());

			int resultado = ps.executeUpdate();
			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}

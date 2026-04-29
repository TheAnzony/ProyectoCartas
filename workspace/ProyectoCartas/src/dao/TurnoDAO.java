package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modulos.Turno;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Turno}.
 *
 * Gestiona las operaciones sobre la tabla {@code TURNO} de la base de datos.
 */
public class TurnoDAO {

	/**
	 * Constructor vacío para instanciar el DAO.
	 */
	public TurnoDAO() {

	}

	/**
	 * Inserta un nuevo turno en la base de datos.
	 *
	 * @param t Objeto {@link Turno} con los datos del turno a registrar.
	 * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
	 */
	public boolean insertar(Turno t) {

		String sql = "INSERT INTO turno (id_partida, id_jugador_activo, numero_turno, mana_disponible, mana_gastado, vida_j1_restante, vida_j2_restante) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, t.getId_partida());
			ps.setInt(2, t.getId_jugador_activo());
			ps.setInt(3, t.getNumero_turno());
			ps.setInt(4, t.getMana_disponible());
			ps.setInt(5, t.getMana_gastado());
			ps.setInt(6, t.getVida_j1_restante());
			ps.setInt(7, t.getVida_j2_restante());

			int resultado = ps.executeUpdate();

			System.out.println(resultado > 0 ? "Turno insertado" : "No se ha podido insertar");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Elimina un turno de la base de datos por su identificador.
	 *
	 * @param id Identificador del turno a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM turno WHERE id_turno = ?";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			int resultado = ps.executeUpdate();

			System.out.println(resultado > 0 ? "Eliminado" : "No se ha podido eliminar");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Construye un objeto {@link Turno} a partir de una fila del {@link ResultSet}.
	 *
	 * @param rs {@link ResultSet} posicionado en la fila a leer.
	 * @return Objeto {@link Turno}, o {@code null} si ocurre un error.
	 */
	private Turno construirTurno(ResultSet rs) {

		try {

			int idTurno = rs.getInt("id_turno");
			int idPartida = rs.getInt("id_partida");
			int idJugadorActivo = rs.getInt("id_jugador_activo");
			int numeroTurno = rs.getInt("numero_turno");
			int manaDispo = rs.getInt("mana_disponible");
			int manaGastado = rs.getInt("mana_gastado");
			int vidaJ1 = rs.getInt("vida_j1_restante");
			int vidaJ2 = rs.getInt("vida_j2_restante");

			return new Turno(idTurno, idPartida, idJugadorActivo, numeroTurno, manaDispo, manaGastado, vidaJ1, vidaJ2);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recupera todos los turnos de la base de datos.
	 *
	 * @return Lista de objetos {@link Turno}. Vacía si no hay ninguno.
	 */
	public List<Turno> listar() {

		String sql = "SELECT * FROM turno";

		List<Turno> lista = new ArrayList<>();

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Turno t = construirTurno(rs);
				if (t != null)
					lista.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	/**
	 * Recupera todos los turnos de una partida concreta.
	 *
	 * @param idPartida Identificador de la partida.
	 * @return Lista de objetos {@link Turno} ordenados por número de turno. Vacía si no hay ninguno.
	 */
	public List<Turno> listarPorPartida(int idPartida) {

		String sql = "SELECT * FROM turno WHERE id_partida = ? ORDER BY numero_turno ASC";

		List<Turno> lista = new ArrayList<>();

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idPartida);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Turno t = construirTurno(rs);
				if (t != null)
					lista.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

}

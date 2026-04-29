package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Turno_carta;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Turno_carta}.
 *
 * Gestiona las operaciones sobre la tabla {@code TURNO_CARTA} de la base de datos.
 */
public class TurnoCartaDAO {

	/**
	 * Constructor vacío para instanciar el DAO.
	 */
	public TurnoCartaDAO() {

	}

	/**
	 * Inserta una nueva carta jugada en un turno.
	 *
	 * @param tc Objeto {@link Turno_carta} con los datos a insertar.
	 * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
	 */
	public boolean insertar(Turno_carta tc) {

		String sql = "INSERT INTO turno_carta (id_turno, id_carta, orden_juego, tipo_accion, dano_causado, resultado) VALUES (?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, tc.getId_turno());
			ps.setInt(2, tc.getId_carta());
			ps.setInt(3, tc.getOrden_juego());
			ps.setString(4, tc.getTipo_accion());
			ps.setInt(5, tc.getDano_causado());
			ps.setString(6, tc.getResultado());

			int resultado = ps.executeUpdate();

			System.out.println(resultado > 0 ? "Turno_carta insertado" : "No se ha podido insertar");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Construye un objeto {@link Turno_carta} a partir de una fila del {@link ResultSet}.
	 *
	 * @param rs {@link ResultSet} posicionado en la fila a leer.
	 * @return Objeto {@link Turno_carta}, o {@code null} si ocurre un error.
	 */
	private Turno_carta construirTurno_carta(ResultSet rs) {

		try {

			int idTurno = rs.getInt("id_turno");
			int idCarta = rs.getInt("id_carta");
			int ordenJuego = rs.getInt("orden_juego");
			String tipoAccion = rs.getString("tipo_accion");
			int danoCausado = rs.getInt("dano_causado");
			String resultado = rs.getString("resultado");

			return new Turno_carta(idTurno, idCarta, ordenJuego, tipoAccion, danoCausado, resultado);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Recupera todas las cartas jugadas en un turno concreto, ordenadas por orden de juego.
	 *
	 * @param idTurno Identificador del turno.
	 * @return Lista de objetos {@link Turno_carta}. Vacía si no hay ninguna.
	 */
	public List<Turno_carta> listarPorTurno(int idTurno) {

		String sql = "SELECT * FROM turno_carta WHERE id_turno = ? ORDER BY orden_juego ASC";

		List<Turno_carta> lista = new ArrayList<>();

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idTurno);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Turno_carta tc = construirTurno_carta(rs);
				if (tc != null)
					lista.add(tc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

}

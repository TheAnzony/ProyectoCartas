package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Jugador;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Jugador}
 * 
 * Gestiona las operaciones CRUD sobre la tabla {@code JUGADOR} de la base de
 * datos
 */

public class JugadorDAO {

	/**
	 * Constructor vacio solo para usar metodos
	 */
	public JugadorDAO() {

	}

	/**
	 * Inserta un nuevo jugador a la base de datos.
	 * 
	 * @param jugador Objeto {@link Jugador} con los datos a insertar. El campo
	 *                {@code email} debe ser unico en la base de datos.
	 * @return {@code true} si la insercion fue exitosa, {@code false} en caso
	 *         contrario.
	 * @throws SQLException si el email ya existe o hay un error de conexion.
	 */
	public boolean insertar(Jugador jugador) {

		String sql = "INSERT INTO jugador (nombre, apellidos, email, fecha_registro, puntuacion_total) VALUES (?, ?, ?, ?, ?)";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setString(1, jugador.getNombre());
			ps.setString(2, jugador.getApellidos());
			ps.setString(3, jugador.getEmail());
			ps.setDate(4, Date.valueOf(jugador.getFecha_registro()));
			ps.setInt(5, jugador.getPuntuacion_total());

			int resultado = ps.executeUpdate();

			System.out
					.println(resultado > 0 ? "Jugador insertado correctamente" : "No se ha podido insertar el jugador");
			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca un jugador en la base de datos por su identificador.
	 *
	 * @param id Identificador del jugador a buscar.
	 * @return Objeto {@link Jugador} con los datos encontrados, o {@code null} si
	 *         no existe.
	 */
	public Jugador buscar(int id) {

		String sql = "SELECT * FROM jugador WHERE id_jugador = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int idj = resultado.getInt("id_jugador");
				String nombre = resultado.getString("nombre");
				String apellidos = resultado.getString("apellidos");
				String email = resultado.getString("email");
				LocalDate fecha = resultado.getDate("fecha_registro").toLocalDate();
				int puntuacion = resultado.getInt("puntuacion_total");

				return new Jugador(idj, nombre, apellidos, email, fecha, puntuacion);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Recupera todos los jugadores de la base de datos.
	 *
	 * @return Lista de objetos {@link Jugador}. Vacía si no hay ninguno.
	 */
	public List<Jugador> listar() {

		String sql = "SELECT * FROM jugador";
		List<Jugador> lista = new ArrayList<Jugador>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int id = resultado.getInt("id_jugador");
				String nombre = resultado.getString("nombre");
				String apellidos = resultado.getString("apellidos");
				String email = resultado.getString("email");
				LocalDate fecha = resultado.getDate("fecha_registro").toLocalDate();
				int puntuacion = resultado.getInt("puntuacion_total");

				lista.add(new Jugador(id, nombre, apellidos, email, fecha, puntuacion));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	/**
	 * Actualiza los datos de un jugador existente en la base de datos.
	 *
	 * @param j Objeto {@link Jugador} con los nuevos datos. Debe tener un
	 *          {@code id_jugador} válido.
	 * @return {@code true} si la actualización fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean actualizar(Jugador j) {
		String sql = "UPDATE jugador SET nombre = ?, apellidos = ?, email = ? WHERE id_jugador = ? ";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setString(1, j.getNombre());
			ps.setString(2, j.getApellidos());
			ps.setString(3, j.getEmail());
			ps.setInt(4, j.getId_jugador());

			int resultado = ps.executeUpdate();

			System.out
					.println(resultado > 0 ? "Cambios realizados con exito." : "No se han podido realizar los cambios");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina un jugador de la base de datos por su identificador.
	 *
	 * @param id Identificador del jugador a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM jugador WHERE id_jugador = ?";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			int resultado = ps.executeUpdate();

			System.out
					.println(resultado > 0 ? "El jugador ha sido eliminado correctamente" : "No se ha podido eliminar");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}

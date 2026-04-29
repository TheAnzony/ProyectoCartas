package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Mazo;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Mazo} Gestiona las
 * operaciones CRUD sobre la tabla {@code MAZO} de la base de datos
 */
public class MazoDAO {

	/**
	 * Constructor vacio solo para usar metodos
	 */
	public MazoDAO() {

	}

	/**
	 * Inserta un nuevo mazo a la base de datos. * @param mazo Objeto {@link Mazo}
	 * con los datos a insertar.
	 * 
	 * @return {@code true} si la insercion fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean insertar(Mazo mazo) {

		String sql = "INSERT INTO mazo (id_jugador, nombre, fecha_creacion) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, mazo.getId_jugador());
			ps.setString(2, mazo.getNombre());
			ps.setDate(3, Date.valueOf(mazo.getFecha_creacion()));

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Mazo insertado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido insertar el mazo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca un mazo en la base de datos por su identificador.
	 *
	 * @param id Identificador del mazo a buscar.
	 * @return Objeto {@link Mazo} con los datos encontrados, o {@code null} si no
	 *         existe.
	 */
	public Mazo buscar(int id) {

		String sql = "SELECT * FROM mazo WHERE id_mazo = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int idm = resultado.getInt("id_mazo");
				int idj = resultado.getInt("id_jugador");
				String nombre = resultado.getString("nombre");
				LocalDate fecha = resultado.getDate("fecha_creation").toLocalDate();

				return new Mazo(idm, idj, nombre, fecha);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recupera todos los mazos de la base de datos.
	 *
	 * @return Lista de objetos {@link Mazo}. Vacía si no hay ninguno.
	 */
	public List<Mazo> listar() {

		String sql = "SELECT * FROM mazo";
		List<Mazo> lista = new ArrayList<Mazo>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int idm = resultado.getInt("id_mazo");
				int idj = resultado.getInt("id_jugador");
				String nombre = resultado.getString("nombre");
				LocalDate fecha = resultado.getDate("fecha_creacion").toLocalDate();

				lista.add(new Mazo(idm, idj, nombre, fecha));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Actualiza los datos de un mazo existente en la base de datos.
	 *
	 * @param m Objeto {@link Mazo} con los nuevos datos.
	 * @return {@code true} si la actualización fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean actualizar(Mazo m) {
		String sql = "UPDATE mazo SET id_jugador = ?, nombre = ?, fecha_creacion = ? WHERE id_mazo = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, m.getId_jugador());
			ps.setString(2, m.getNombre());
			ps.setDate(3, Date.valueOf(m.getFecha_creacion()));
			ps.setInt(4, m.getId_mazo());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Mazo actualizado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido actualizar el mazo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina un mazo de la base de datos por su identificador.
	 *
	 * @param id Identificador del mazo a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM mazo WHERE id_mazo = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id);

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Mazo eliminado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido eliminar el mazo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
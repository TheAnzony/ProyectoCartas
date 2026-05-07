package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modulos.Efecto_estado;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Efecto_estado}
 * Gestiona las operaciones CRUD sobre la tabla {@code EFECTO_ESTADO}
 */
public class Efecto_estadoDAO {

	/**
	 * Constructor vacío para usar los métodos
	 */
	public Efecto_estadoDAO() {

	}

	/**
	 * Inserta un nuevo efecto de estado en la base de datos.
	 * Nota: No se inserta el id_efecto porque se asume autoincremental en la BD.
	 * 
	 * @param ee Objeto {@link Efecto_estado} a insertar
	 * @return true si se insertó correctamente, false si hubo un error
	 */
	public boolean insertar(Efecto_estado ee) {
		String sql = "INSERT INTO efecto_estado (id_elemento, bonus_ataque_pct, penalty_ataque_pct, duracion_turnos, descripcion) VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, ee.getId_elemento());
			ps.setInt(2, ee.getBonus_ataque_pct());
			ps.setInt(3, ee.getPenalty_ataque_pct());
			ps.setInt(4, ee.getDuracion_turnos());
			ps.setString(5, ee.getDescripcion());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("El efecto de estado se ha insertado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido insertar el efecto de estado");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca un efecto de estado por su ID.
	 * 
	 * @param id_efecto Identificador único del efecto
	 * @return El objeto {@link Efecto_estado} encontrado, o null si no existe
	 */
	public Efecto_estado buscar(int id_efecto) {
		String sql = "SELECT * FROM efecto_estado WHERE id_efecto = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id_efecto);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int idEfecto = resultado.getInt("id_efecto");
				int idElemento = resultado.getInt("id_elemento");
				int bonus = resultado.getInt("bonus_ataque_pct");
				int penalty = resultado.getInt("penalty_ataque_pct");
				int duracion = resultado.getInt("duracion_turnos");
				String descripcion = resultado.getString("descripcion");

				// Usamos el constructor completo porque ya tenemos el ID de la base de datos
				return new Efecto_estado(idEfecto, idElemento, bonus, penalty, duracion, descripcion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Devuelve una lista con todos los efectos de estado registrados.
	 * 
	 * @return Lista de objetos {@link Efecto_estado}
	 */
	public List<Efecto_estado> listar() {
		String sql = "SELECT * FROM efecto_estado";
		List<Efecto_estado> lista = new ArrayList<>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int idEfecto = resultado.getInt("id_efecto");
				int idElemento = resultado.getInt("id_elemento");
				int bonus = resultado.getInt("bonus_ataque_pct");
				int penalty = resultado.getInt("penalty_ataque_pct");
				int duracion = resultado.getInt("duracion_turnos");
				String descripcion = resultado.getString("descripcion");

				lista.add(new Efecto_estado(idEfecto, idElemento, bonus, penalty, duracion, descripcion));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Actualiza los datos de un efecto de estado existente.
	 * 
	 * @param ee Objeto {@link Efecto_estado} con los datos modificados (debe incluir el id_efecto)
	 * @return true si se actualizó correctamente, false si hubo un error
	 */
	public boolean actualizar(Efecto_estado ee) {
		String sql = "UPDATE efecto_estado SET id_elemento = ?, bonus_ataque_pct = ?, penalty_ataque_pct = ?, duracion_turnos = ?, descripcion = ? WHERE id_efecto = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, ee.getId_elemento());
			ps.setInt(2, ee.getBonus_ataque_pct());
			ps.setInt(3, ee.getPenalty_ataque_pct());
			ps.setInt(4, ee.getDuracion_turnos());
			ps.setString(5, ee.getDescripcion());
			ps.setInt(6, ee.getId_efecto());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("El efecto de estado se ha actualizado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido actualizar el efecto de estado");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina un efecto de estado de la base de datos.
	 * 
	 * @param id_efecto Identificador único del efecto a borrar
	 * @return true si se eliminó correctamente, false si hubo un error
	 */
	public boolean eliminar(int id_efecto) {
		String sql = "DELETE FROM efecto_estado WHERE id_efecto = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id_efecto);

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("El efecto de estado se ha eliminado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido eliminar el efecto de estado");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
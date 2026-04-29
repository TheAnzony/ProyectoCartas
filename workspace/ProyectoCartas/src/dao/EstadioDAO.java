
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Estadio;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Estadio} Gestiona las
 * operaciones CRUD sobre la tabla {@code ESTADIO} de la base de datos
 */
public class EstadioDAO {

	/**
	 * Constructor vacío solo para usar métodos
	 */
	public EstadioDAO() {

	}

	/**
	 * Inserta un nuevo estadio a la base de datos. * @param estadio Objeto
	 * {@link Estadio} con los datos a insertar.
	 * 
	 * @return {@code true} si la inserción fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean insertar(Estadio estadio) {

		String sql = "INSERT INTO estadio (nombre, descripcion, id_elemento_activo) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setString(1, estadio.getNombre());
			ps.setString(2, estadio.getDescripcion());
			ps.setInt(3, estadio.getId_elemento_activo());

			int resultado = ps.executeUpdate();

			System.out
					.println(resultado > 0 ? "Estadio insertado correctamente" : "No se ha podido insertar el estadio");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca un estadio en la base de datos por su identificador.
	 *
	 * @param id Identificador del estadio a buscar.
	 * @return Objeto {@link Estadio} con los datos encontrados, o {@code null} si
	 *         no existe.
	 */
	public Estadio buscar(int id) {

		String sql = "SELECT * FROM estadio WHERE id_estadio = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int ide = resultado.getInt("id_estadio");
				String nombre = resultado.getString("nombre");
				String descripcion = resultado.getString("descripcion");
				int idElemento = resultado.getInt("id_elemento_activo");

				return new Estadio(ide, nombre, descripcion, idElemento);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recupera todos los estadios de la base de datos.
	 *
	 * @return Lista de objetos {@link Estadio}. Vacía si no hay ninguno.
	 */
	public List<Estadio> listar() {

		String sql = "SELECT * FROM estadio";
		List<Estadio> lista = new ArrayList<Estadio>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int ide = resultado.getInt("id_estadio");
				String nombre = resultado.getString("nombre");
				String descripcion = resultado.getString("descripcion");
				int idElemento = resultado.getInt("id_elemento_activo");

				lista.add(new Estadio(ide, nombre, descripcion, idElemento));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Actualiza los datos de un estadio existente en la base de datos.
	 *
	 * @param e Objeto {@link Estadio} con los nuevos datos.
	 * @return {@code true} si la actualización fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean actualizar(Estadio e) {
		String sql = "UPDATE estadio SET nombre = ?, descripcion = ?, id_elemento_activo = ? WHERE id_estadio = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setString(1, e.getNombre());
			ps.setString(2, e.getDescripcion());
			ps.setInt(3, e.getId_elemento_activo());
			ps.setInt(4, e.getId_estadio());

			int resultado = ps.executeUpdate();

			System.out.println(
					resultado > 0 ? "Estadio actualizado correctamente" : "No se ha podido actualizar el estadio");

			return resultado > 0;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina un estadio de la base de datos por su identificador.
	 *
	 * @param id Identificador del estadio a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM estadio WHERE id_estadio = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id);

			int resultado = ps.executeUpdate();

			System.out
					.println(resultado > 0 ? "Estadio eliminado correctamente" : "No se ha podido eliminar el estadio");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}

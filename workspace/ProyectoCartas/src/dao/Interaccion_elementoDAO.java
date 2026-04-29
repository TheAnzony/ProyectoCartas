
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Interaccion_elemento;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Interaccion_elemento}
 * Gestiona las operaciones CRUD sobre la tabla {@code INTERACCION_ELEMENTO} de
 * la base de datos
 */
public class Interaccion_elementoDAO {

	/**
	 * Constructor vacío solo para usar métodos
	 */
	public Interaccion_elementoDAO() {

	}

	/**
	 * Inserta una nueva interacción de elementos a la base de datos. * @param ie
	 * Objeto {@link Interaccion_elemento} con los datos a insertar.
	 * 
	 * @return {@code true} si la inserción fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean insertar(Interaccion_elemento ie) {

		String sql = "INSERT INTO interaccion_elemento (id_elem_atacante, id_elem_defensor, multiplicador) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, ie.getId_elem_atacante());
			ps.setInt(2, ie.getId_elem_defensor());
			ps.setDouble(3, ie.getMultiplicador());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Interacción de elementos insertada correctamente");
				return true;
			} else {
				System.out.println("No se ha podido insertar la interacción");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca una interacción específica por el ID del atacante y el ID del defensor.
	 *
	 * @param idAtacante Identificador del elemento atacante.
	 * @param idDefensor Identificador del elemento defensor.
	 * @return Objeto {@link Interaccion_elemento} con los datos encontrados, o
	 *         {@code null} si no existe.
	 */
	public Interaccion_elemento buscar(int idAtacante, int idDefensor) {

		String sql = "SELECT * FROM interaccion_elemento WHERE id_elem_atacante = ? AND id_elem_defensor = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idAtacante);
			ps.setInt(2, idDefensor);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int atacante = resultado.getInt("id_elem_atacante");
				int defensor = resultado.getInt("id_elem_defensor");
				double multiplicador = resultado.getDouble("multiplicador");

				return new Interaccion_elemento(atacante, defensor, multiplicador);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recupera todas las interacciones de elementos de la base de datos.
	 *
	 * @return Lista de objetos {@link Interaccion_elemento}. Vacía si no hay
	 *         ninguna.
	 */
	public List<Interaccion_elemento> listar() { // esto se queda con list o arraylist?? 

		String sql = "SELECT * FROM interaccion_elemento";
		List<Interaccion_elemento> lista = new ArrayList<Interaccion_elemento>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int atacante = resultado.getInt("id_elem_atacante");
				int defensor = resultado.getInt("id_elem_defensor");
				double multiplicador = resultado.getDouble("multiplicador");

				lista.add(new Interaccion_elemento(atacante, defensor, multiplicador));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Actualiza el multiplicador de una interacción existente.
	 *
	 * @param ie Objeto {@link Interaccion_elemento} con los nuevos datos.
	 * @return {@code true} si la actualización fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean actualizar(Interaccion_elemento ie) {
		String sql = "UPDATE interaccion_elemento SET multiplicador = ? WHERE id_elem_atacante = ? AND id_elem_defensor = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setDouble(1, ie.getMultiplicador());
			ps.setInt(2, ie.getId_elem_atacante());
			ps.setInt(3, ie.getId_elem_defensor());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Interacción actualizada correctamente");
				return true;
			} else {
				System.out.println("No se ha podido actualizar la interacción");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina una interacción de la base de datos.
	 *
	 * @param idAtacante Identificador del elemento atacante.
	 * @param idDefensor Identificador del elemento defensor.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean eliminar(int idAtacante, int idDefensor) {

		String sql = "DELETE FROM interaccion_elemento WHERE id_elem_atacante = ? AND id_elem_defensor = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idAtacante);
			ps.setInt(2, idDefensor);

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Interacción eliminada correctamente");
				return true;
			} else {
				System.out.println("No se ha podido eliminar la interacción");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
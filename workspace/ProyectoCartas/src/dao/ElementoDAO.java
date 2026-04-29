
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Elemento;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Elemento} * Gestiona las
 * operaciones CRUD sobre la tabla {@code ELEMENTO} de la base de datos
 */
public class ElementoDAO {

	/**
	 * Constructor vacio solo para usar metodos
	 */
	public ElementoDAO() {

	}

	/**
	 * Inserta un nuevo elemento a la base de datos. * @param elemento Objeto
	 * {@link Elemento} con los datos a insertar.
	 * 
	 * @return {@code true} si la insercion fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean insertar(Elemento elemento) {

		String sql = "INSERT INTO elemento (nombre, descripcion, color_hex) VALUES (?, ?, ?)";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setString(1, elemento.getNombre());
			ps.setString(2, elemento.getDescripcion());
			ps.setString(3, elemento.getColor_hex());

			int resultado = ps.executeUpdate();
			
			System.out.println(resultado > 0 ? "Elemento insertado correctamente" : "No se ha podido insertar el elemento");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca un elemento en la base de datos por su identificador.
	 *
	 * @param id Identificador del elemento a buscar.
	 * @return Objeto {@link Elemento} con los datos encontrados, o {@code null} si
	 *         no existe.
	 */
	public Elemento buscar(int id) {

		String sql = "SELECT * FROM elemento WHERE id_elemento = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int ide = resultado.getInt("id_elemento");
				String nombre = resultado.getString("nombre");
				String descripcion = resultado.getString("descripcion");
				String color_hex = resultado.getString("color_hex");

				return new Elemento(ide, nombre, descripcion, color_hex);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Recupera todos los elementos de la base de datos.
	 *
	 * @return Lista de objetos {@link Elemento}. Vacía si no hay ninguno.
	 */
	public List<Elemento> listar() {

		String sql = "SELECT * FROM elemento";
		List<Elemento> lista = new ArrayList<Elemento>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int id = resultado.getInt("id_elemento");
				String nombre = resultado.getString("nombre");
				String descripcion = resultado.getString("descripcion");
				String color_hex = resultado.getString("color_hex");

				lista.add(new Elemento(id, nombre, descripcion, color_hex));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	/**
	 * Actualiza los datos de un elemento existente en la base de datos.
	 *
	 * @param e Objeto {@link Elemento} con los nuevos datos. Debe tener un
	 *          {@code id_elemento} válido.
	 * @return {@code true} si la actualización fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean actualizar(Elemento e) {
		String sql = "UPDATE elemento SET nombre = ?, descripcion = ?, color_hex = ? WHERE id_elemento = ? ";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setString(1, e.getNombre());
			ps.setString(2, e.getDescripcion());
			ps.setString(3, e.getColor_hex());
			ps.setInt(4, e.getId_elemento());

			int resultado = ps.executeUpdate();
			
			System.out.println(resultado > 0 ? "Elemento actualizado correctamente" : "No se ha podido actualizar el elemento");

			return resultado > 0;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina un elemento de la base de datos por su identificador.
	 *
	 * @param id Identificador del elemento a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean eliminar(int id) {

		String sql = "DELETE FROM elemento WHERE id_elemento = ?";

		try {

			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);

			ps.setInt(1, id);

			int resultado = ps.executeUpdate();
			
			System.out.println(resultado > 0 ? "Elemento eliminado correctamente" : "No se ha podido eliminar el elemento");

			return resultado > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}

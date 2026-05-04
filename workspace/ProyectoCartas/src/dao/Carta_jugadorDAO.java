package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modulos.Carta_jugador;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Carta_jugador}
 * Gestiona el inventario/colección de cartas de los jugadores en la tabla {@code CARTA_JUGADOR}
 */
public class Carta_jugadorDAO {

	/**
	 * Constructor vacío para usar los métodos
	 */
	public Carta_jugadorDAO() {

	}

	/**
	 * Inserta una nueva carta en la colección de un jugador.
	 * 
	 * @param cj Objeto {@link Carta_jugador} con los datos de la obtención
	 * @return true si se insertó correctamente, false si hubo un error
	 */
	public boolean insertar(Carta_jugador cj) {
		String sql = "INSERT INTO carta_jugador (id_jugador, id_carta, cantidad, fecha_obtencion) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, cj.getId_jugador());
			ps.setInt(2, cj.getId_carta());
			ps.setInt(3, cj.getCantidad());
			ps.setObject(4, cj.getFecha_obtencion()); // Guardamos el LocalDate directamente

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Carta añadida a la colección del jugador correctamente");
				return true;
			} else {
				System.out.println("No se ha podido añadir la carta al jugador");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Busca el registro de una carta específica dentro de la colección de un jugador.
	 * 
	 * @param idJugador Identificador del jugador
	 * @param idCarta   Identificador de la carta
	 * @return El objeto {@link Carta_jugador} encontrado, o null si el jugador no tiene esa carta
	 */
	public Carta_jugador buscar(int idJugador, int idCarta) {
		String sql = "SELECT * FROM carta_jugador WHERE id_jugador = ? AND id_carta = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idJugador);
			ps.setInt(2, idCarta);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				int idJ = resultado.getInt("id_jugador");
				int idC = resultado.getInt("id_carta");
				int cantidad = resultado.getInt("cantidad");
				
				// Extraemos la fecha moderna indicándole a Java que es un LocalDate
				LocalDate fechaObtencion = resultado.getObject("fecha_obtencion", LocalDate.class);

				return new Carta_jugador(idJ, idC, cantidad, fechaObtencion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MÈTODO LÓGICO DE NEGOCIO:
	 * Recupera TODA la colección de cartas de un jugador específico.
	 * Ideal para cargar la pantalla de "Mi Colección" o "Mis Cartas".
	 * 
	 * @param idJugador Identificador del jugador del que queremos ver las cartas
	 * @return Lista de objetos {@link Carta_jugador} pertenecientes a ese jugador
	 */
	public List<Carta_jugador> listarCartasJugador(int idJugador) {
		String sql = "SELECT * FROM carta_jugador WHERE id_jugador = ?";
		List<Carta_jugador> coleccion = new ArrayList<>();

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idJugador);
			
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				int idJ = resultado.getInt("id_jugador");
				int idC = resultado.getInt("id_carta");
				int cantidad = resultado.getInt("cantidad");
				LocalDate fechaObtencion = resultado.getObject("fecha_obtencion", LocalDate.class);

				coleccion.add(new Carta_jugador(idJ, idC, cantidad, fechaObtencion));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coleccion;
	}

	/**
	 * Actualiza la cantidad o la fecha de obtención de una carta que ya tiene el jugador.
	 * 
	 * @param cj Objeto {@link Carta_jugador} con los datos actualizados
	 * @return true si se actualizó correctamente, false si hubo un error
	 */
	public boolean actualizar(Carta_jugador cj) {
		String sql = "UPDATE carta_jugador SET cantidad = ?, fecha_obtencion = ? WHERE id_jugador = ? AND id_carta = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, cj.getCantidad());
			ps.setObject(2, cj.getFecha_obtencion());
			ps.setInt(3, cj.getId_jugador());
			ps.setInt(4, cj.getId_carta());

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Inventario del jugador actualizado correctamente");
				return true;
			} else {
				System.out.println("No se ha podido actualizar el inventario del jugador");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Elimina una carta completamente de la colección de un jugador.
	 * 
	 * @param idJugador Identificador del jugador
	 * @param idCarta   Identificador de la carta a borrar
	 * @return true si se eliminó correctamente, false si hubo un error
	 */
	public boolean eliminar(int idJugador, int idCarta) {
		String sql = "DELETE FROM carta_jugador WHERE id_jugador = ? AND id_carta = ?";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, idJugador);
			ps.setInt(2, idCarta);

			int resultado = ps.executeUpdate();

			if (resultado > 0) {
				System.out.println("Carta eliminada de la colección correctamente");
				return true;
			} else {
				System.out.println("No se ha podido eliminar la carta de la colección");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
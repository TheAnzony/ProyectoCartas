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
 * Clase de acceso a datos (DAO) para la entidad {@link Carta_jugador}.
 * Gestiona el inventario de cartas de los jugadores en la tabla {@code CARTA_JUGADOR}.
 */
public class Carta_jugadorDAO {

    public Carta_jugadorDAO() {}

    /**
     * Añade una carta a la colección de un jugador.
     *
     * @param cj Objeto {@link Carta_jugador} con los datos de la obtención.
     * @return {@code true} si se insertó correctamente, {@code false} si hubo un error.
     */
    public boolean insertar(Carta_jugador cj) {
        String sql = "INSERT INTO carta_jugador (id_jugador, id_carta, fecha_obtencion) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, cj.getId_jugador());
            ps.setInt(2, cj.getId_carta());
            ps.setObject(3, cj.getFecha_obtencion());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Carta añadida a la colección del jugador correctamente" : "No se ha podido añadir la carta al jugador");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca una carta específica dentro de la colección de un jugador.
     *
     * @param idJugador Identificador del jugador.
     * @param idCarta   Identificador de la carta.
     * @return Objeto {@link Carta_jugador}, o {@code null} si el jugador no tiene esa carta.
     */
    public Carta_jugador buscar(int idJugador, int idCarta) {
        String sql = "SELECT * FROM carta_jugador WHERE id_jugador = ? AND id_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, idCarta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate fecha = rs.getObject("fecha_obtencion", LocalDate.class);
                return new Carta_jugador(rs.getInt("id_jugador"), rs.getInt("id_carta"), fecha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera toda la colección de cartas de un jugador.
     *
     * @param idJugador Identificador del jugador.
     * @return Lista de objetos {@link Carta_jugador}. Vacía si no tiene ninguna.
     */
    public List<Carta_jugador> listarCartasJugador(int idJugador) {
        String sql = "SELECT * FROM carta_jugador WHERE id_jugador = ?";
        List<Carta_jugador> coleccion = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idJugador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDate fecha = rs.getObject("fecha_obtencion", LocalDate.class);
                coleccion.add(new Carta_jugador(rs.getInt("id_jugador"), rs.getInt("id_carta"), fecha));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coleccion;
    }

    /**
     * Actualiza la fecha de obtención de una carta en la colección de un jugador.
     *
     * @param cj Objeto {@link Carta_jugador} con los datos actualizados.
     * @return {@code true} si se actualizó correctamente, {@code false} si hubo un error.
     */
    public boolean actualizar(Carta_jugador cj) {
        String sql = "UPDATE carta_jugador SET fecha_obtencion = ? WHERE id_jugador = ? AND id_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setObject(1, cj.getFecha_obtencion());
            ps.setInt(2, cj.getId_jugador());
            ps.setInt(3, cj.getId_carta());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Colección del jugador actualizada correctamente" : "No se ha podido actualizar la colección");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una carta de la colección de un jugador.
     *
     * @param idJugador Identificador del jugador.
     * @param idCarta   Identificador de la carta a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} si hubo un error.
     */
    public boolean eliminar(int idJugador, int idCarta) {
        String sql = "DELETE FROM carta_jugador WHERE id_jugador = ? AND id_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, idCarta);

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Carta eliminada de la colección correctamente" : "No se ha podido eliminar la carta de la colección");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

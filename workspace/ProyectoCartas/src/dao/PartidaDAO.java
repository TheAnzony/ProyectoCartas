package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Partida;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Partida}.
 * Gestiona las operaciones CRUD sobre la tabla {@code PARTIDA} de la base de datos.
 */
public class PartidaDAO {

    public PartidaDAO() {}

    private Partida construirPartida(ResultSet rs) {
        try {
            int idPartida = rs.getInt("id_partida");
            int idJugador1 = rs.getInt("id_jugador1");
            int idJugador2 = rs.getInt("id_jugador2");
            int idMazoJ1 = rs.getInt("id_mazo_j1");
            int idMazoJ2 = rs.getInt("id_mazo_j2");
            int idEstadio = rs.getInt("id_estadio");
            LocalDateTime fecha = rs.getObject("fecha", LocalDateTime.class);
            int ganadorRaw = rs.getInt("id_ganador");
            Integer idGanador = rs.wasNull() ? null : ganadorRaw;
            int numTurnos = rs.getInt("num_turnos");

            return new Partida(idPartida, idJugador1, idJugador2, idMazoJ1, idMazoJ2,
                    idEstadio, fecha, idGanador, numTurnos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta una nueva partida en la base de datos.
     *
     * @param pa Objeto {@link Partida} con los datos a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Partida pa) {
        String sql = "INSERT INTO partida (id_jugador1, id_jugador2, id_mazo_j1, id_mazo_j2, id_estadio, fecha, id_ganador, num_turnos) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, pa.getId_jugador1());
            ps.setInt(2, pa.getId_jugador2());
            ps.setInt(3, pa.getId_mazo_j1());
            ps.setInt(4, pa.getId_mazo_j2());
            ps.setInt(5, pa.getId_estadio());
            ps.setObject(6, pa.getFecha());
            ps.setObject(7, pa.getId_ganador());
            ps.setInt(8, pa.getNum_turnos());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Partida insertada correctamente" : "No se ha podido insertar la partida");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca una partida por su identificador.
     *
     * @param idPartida Identificador de la partida.
     * @return Objeto {@link Partida}, o {@code null} si no existe.
     */
    public Partida buscar(int idPartida) {
        String sql = "SELECT * FROM partida WHERE id_partida = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idPartida);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirPartida(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera el historial de partidas de un jugador.
     *
     * @param idJugador Identificador del jugador.
     * @return Lista de objetos {@link Partida} en las que participó ese jugador.
     */
    public List<Partida> listarHistorialJugador(int idJugador) {
        String sql = "SELECT * FROM partida WHERE id_jugador1 = ? OR id_jugador2 = ? ORDER BY fecha DESC";
        List<Partida> misPartidas = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, idJugador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Partida p = construirPartida(rs);
                if (p != null) misPartidas.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return misPartidas;
    }

    /**
     * Actualiza los datos de una partida existente (ganador y número de turnos).
     *
     * @param pa Objeto {@link Partida} con los nuevos datos (debe tener id_partida válido).
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean actualizar(Partida pa) {
        String sql = "UPDATE partida SET id_ganador = ?, num_turnos = ? WHERE id_partida = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setObject(1, pa.getId_ganador());
            ps.setInt(2, pa.getNum_turnos());
            ps.setInt(3, pa.getId_partida());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Partida actualizada correctamente" : "No se ha podido actualizar la partida");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una partida por su identificador.
     *
     * @param id Identificador de la partida a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM partida WHERE id_partida = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Partida eliminada correctamente" : "No se ha podido eliminar la partida");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

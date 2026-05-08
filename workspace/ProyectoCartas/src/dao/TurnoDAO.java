package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Turno;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Turno}.
 * Gestiona las operaciones sobre la tabla {@code TURNO} de la base de datos.
 */
public class TurnoDAO {

    public TurnoDAO() {}

    private Turno construirTurno(ResultSet rs) {
        try {
            int idTurno = rs.getInt("id_turno");
            int idPartida = rs.getInt("id_partida");
            int numeroTurno = rs.getInt("numero_turno");
            int vidaJ1 = rs.getInt("vida_j1");
            int vidaJ2 = rs.getInt("vida_j2");
            int manaDisponible = rs.getInt("mana_disponible");
            int idElementoActivo = rs.getInt("id_elemento_activo");
            int idJugadorPrimero = rs.getInt("id_jugador_primero");
            double mediaVelJ1 = rs.getDouble("media_velocidad_j1");
            double mediaVelJ2 = rs.getDouble("media_velocidad_j2");

            return new Turno(idTurno, idPartida, numeroTurno, vidaJ1, vidaJ2,
                    manaDisponible, idElementoActivo, idJugadorPrimero, mediaVelJ1, mediaVelJ2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta un nuevo turno en la base de datos.
     *
     * @param t Objeto {@link Turno} con los datos del turno a registrar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Turno t) {
        String sql = "INSERT INTO turno (id_partida, numero_turno, vida_j1, vida_j2, mana_disponible, "
                + "id_elemento_activo, id_jugador_primero, media_velocidad_j1, media_velocidad_j2) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, t.getId_partida());
            ps.setInt(2, t.getNumero_turno());
            ps.setInt(3, t.getVida_j1());
            ps.setInt(4, t.getVida_j2());
            ps.setInt(5, t.getMana_disponible());
            ps.setInt(6, t.getId_elemento_activo());
            ps.setInt(7, t.getId_jugador_primero());
            ps.setDouble(8, t.getMedia_velocidad_j1());
            ps.setDouble(9, t.getMedia_velocidad_j2());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Turno insertado correctamente" : "No se ha podido insertar el turno");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca un turno por su identificador.
     *
     * @param id Identificador del turno.
     * @return Objeto {@link Turno}, o {@code null} si no existe.
     */
    public Turno buscar(int id) {
        String sql = "SELECT * FROM turno WHERE id_turno = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirTurno(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera todos los turnos de una partida, ordenados por número de turno.
     *
     * @param idPartida Identificador de la partida.
     * @return Lista de objetos {@link Turno}. Vacía si no hay ninguno.
     */
    public List<Turno> listarPorPartida(int idPartida) {
        String sql = "SELECT * FROM turno WHERE id_partida = ? ORDER BY numero_turno ASC";
        List<Turno> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idPartida);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Turno t = construirTurno(rs);
                if (t != null) lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina un turno por su identificador.
     *
     * @param id Identificador del turno a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM turno WHERE id_turno = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Turno eliminado correctamente" : "No se ha podido eliminar el turno");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

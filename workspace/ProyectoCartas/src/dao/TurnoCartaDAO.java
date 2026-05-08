package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Turno_carta;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Turno_carta}.
 * Gestiona las operaciones sobre la tabla {@code TURNO_CARTA} de la base de datos.
 */
public class TurnoCartaDAO {

    public TurnoCartaDAO() {}

    private Turno_carta construirTurno_carta(ResultSet rs) {
        try {
            int idTurnoCarta = rs.getInt("id_turno_carta");
            int idTurno = rs.getInt("id_turno");
            int idJugador = rs.getInt("id_jugador");
            int idCarta = rs.getInt("id_carta");
            int danoReal = rs.getInt("daño_real");
            int ordenResolucion = rs.getInt("orden_resolucion");

            return new Turno_carta(idTurnoCarta, idTurno, idJugador, idCarta, danoReal, ordenResolucion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta una carta jugada en un turno.
     *
     * @param tc Objeto {@link Turno_carta} con los datos a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Turno_carta tc) {
        String sql = "INSERT INTO turno_carta (id_turno, id_jugador, id_carta, daño_real, orden_resolucion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, tc.getId_turno());
            ps.setInt(2, tc.getId_jugador());
            ps.setInt(3, tc.getId_carta());
            ps.setInt(4, tc.getDano_real());
            ps.setInt(5, tc.getOrden_resolucion());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Turno_carta insertado correctamente" : "No se ha podido insertar el turno_carta");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca un turno_carta por su identificador.
     *
     * @param id Identificador del turno_carta.
     * @return Objeto {@link Turno_carta}, o {@code null} si no existe.
     */
    public Turno_carta buscar(int id) {
        String sql = "SELECT * FROM turno_carta WHERE id_turno_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirTurno_carta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera todas las cartas jugadas en un turno, ordenadas por orden de resolución.
     *
     * @param idTurno Identificador del turno.
     * @return Lista de objetos {@link Turno_carta}. Vacía si no hay ninguna.
     */
    public List<Turno_carta> listarPorTurno(int idTurno) {
        String sql = "SELECT * FROM turno_carta WHERE id_turno = ? ORDER BY orden_resolucion ASC";
        List<Turno_carta> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idTurno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Turno_carta tc = construirTurno_carta(rs);
                if (tc != null) lista.add(tc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina un turno_carta por su identificador.
     *
     * @param id Identificador del turno_carta a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM turno_carta WHERE id_turno_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Turno_carta eliminado correctamente" : "No se ha podido eliminar el turno_carta");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

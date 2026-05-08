package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Jugador;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Jugador}.
 * Gestiona las operaciones CRUD sobre la tabla {@code JUGADOR} de la base de datos.
 */
public class JugadorDAO {

    public JugadorDAO() {}

    private Jugador construirJugador(ResultSet rs) {
        try {
            int id = rs.getInt("id_jugador");
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            String email = rs.getString("email");
            String apodo = rs.getString("apodo");
            LocalDate fecha = rs.getDate("fecha_registro").toLocalDate();
            int mmr = rs.getInt("MMR");

            return new Jugador(id, nombre, apellidos, email, apodo, fecha, mmr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta un nuevo jugador en la base de datos.
     *
     * @param jugador Objeto {@link Jugador} con los datos a insertar. El campo {@code email} debe ser único.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Jugador jugador) {
        String sql = "INSERT INTO jugador (nombre, apellidos, email, apodo, fecha_registro, MMR) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getApellidos());
            ps.setString(3, jugador.getEmail());
            ps.setString(4, jugador.getApodo());
            ps.setDate(5, Date.valueOf(jugador.getFecha_registro()));
            ps.setInt(6, jugador.getMMR());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Jugador insertado correctamente" : "No se ha podido insertar el jugador");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca un jugador por su identificador.
     *
     * @param id Identificador del jugador.
     * @return Objeto {@link Jugador}, o {@code null} si no existe.
     */
    public Jugador buscar(int id) {
        String sql = "SELECT * FROM jugador WHERE id_jugador = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirJugador(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera todos los jugadores de la base de datos.
     *
     * @return Lista de objetos {@link Jugador}. Vacía si no hay ninguno.
     */
    public List<Jugador> listar() {
        String sql = "SELECT * FROM jugador";
        List<Jugador> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Jugador j = construirJugador(rs);
                if (j != null) lista.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Actualiza los datos de un jugador existente.
     *
     * @param j Objeto {@link Jugador} con los nuevos datos (debe tener id_jugador válido).
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean actualizar(Jugador j) {
        String sql = "UPDATE jugador SET nombre = ?, apellidos = ?, email = ?, apodo = ? WHERE id_jugador = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setString(1, j.getNombre());
            ps.setString(2, j.getApellidos());
            ps.setString(3, j.getEmail());
            ps.setString(4, j.getApodo());
            ps.setInt(5, j.getId_jugador());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Jugador actualizado correctamente" : "No se ha podido actualizar el jugador");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un jugador por su identificador.
     *
     * @param id Identificador del jugador a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM jugador WHERE id_jugador = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Jugador eliminado correctamente" : "No se ha podido eliminar el jugador");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void mostrarLista(List<Jugador> lista) {
        for (Jugador j : lista) {
            System.out.println(j);
        }
    }
}

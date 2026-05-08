package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Mazo;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Mazo}.
 * Gestiona las operaciones CRUD sobre la tabla {@code MAZO} de la base de datos.
 */
public class MazoDAO {

    public MazoDAO() {}

    /**
     * Inserta un nuevo mazo en la base de datos.
     *
     * @param mazo Objeto {@link Mazo} con los datos a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Mazo mazo) {
        String sql = "INSERT INTO mazo (id_jugador, nombre) VALUES (?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, mazo.getId_jugador());
            ps.setString(2, mazo.getNombre());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Mazo insertado correctamente" : "No se ha podido insertar el mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca un mazo por su identificador.
     *
     * @param id Identificador del mazo.
     * @return Objeto {@link Mazo}, o {@code null} si no existe.
     */
    public Mazo buscar(int id) {
        String sql = "SELECT * FROM mazo WHERE id_mazo = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Mazo(
                    rs.getInt("id_mazo"),
                    rs.getInt("id_jugador"),
                    rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera todos los mazos de la base de datos.
     *
     * @return Lista de objetos {@link Mazo}. Vacía si no hay ninguno.
     */
    public List<Mazo> listar() {
        String sql = "SELECT * FROM mazo";
        List<Mazo> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Mazo(
                    rs.getInt("id_mazo"),
                    rs.getInt("id_jugador"),
                    rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Recupera todos los mazos de un jugador concreto.
     *
     * @param idJugador Identificador del jugador.
     * @return Lista de objetos {@link Mazo} del jugador. Vacía si no tiene ninguno.
     */
    public List<Mazo> listarPorJugador(int idJugador) {
        String sql = "SELECT * FROM mazo WHERE id_jugador = ?";
        List<Mazo> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idJugador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Mazo(
                    rs.getInt("id_mazo"),
                    rs.getInt("id_jugador"),
                    rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Actualiza el nombre de un mazo existente.
     *
     * @param m Objeto {@link Mazo} con los nuevos datos (debe tener id_mazo válido).
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean actualizar(Mazo m) {
        String sql = "UPDATE mazo SET nombre = ? WHERE id_mazo = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getId_mazo());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Mazo actualizado correctamente" : "No se ha podido actualizar el mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un mazo por su identificador.
     *
     * @param id Identificador del mazo a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mazo WHERE id_mazo = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Mazo eliminado correctamente" : "No se ha podido eliminar el mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

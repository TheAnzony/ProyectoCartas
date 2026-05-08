package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import modulos.Mazo_carta;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Mazo_carta}.
 * Gestiona las operaciones CRUD sobre la tabla {@code MAZO_CARTA} de la base de datos.
 */
public class Mazo_cartaDAO {

    public Mazo_cartaDAO() {}

    /**
     * Añade una carta a un mazo.
     *
     * @param mc Objeto {@link Mazo_carta} con los identificadores del mazo y la carta.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(Mazo_carta mc) {
        String sql = "INSERT INTO mazo_carta (id_mazo, id_carta) VALUES (?, ?)";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, mc.getId_mazo());
            ps.setInt(2, mc.getId_carta());

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Carta añadida al mazo correctamente" : "No se ha podido añadir la carta al mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca si una carta concreta está en un mazo concreto.
     *
     * @param idMazo  Identificador del mazo.
     * @param idCarta Identificador de la carta.
     * @return Objeto {@link Mazo_carta} si existe, o {@code null} si no está en ese mazo.
     */
    public Mazo_carta buscar(int idMazo, int idCarta) {
        String sql = "SELECT * FROM mazo_carta WHERE id_mazo = ? AND id_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idMazo);
            ps.setInt(2, idCarta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Mazo_carta(rs.getInt("id_mazo"), rs.getInt("id_carta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera todas las cartas de un mazo.
     *
     * @param idMazo Identificador del mazo.
     * @return Lista de objetos {@link Mazo_carta}. Vacía si el mazo no tiene cartas.
     */
    public List<Mazo_carta> listarPorMazo(int idMazo) {
        String sql = "SELECT * FROM mazo_carta WHERE id_mazo = ?";
        List<Mazo_carta> lista = new ArrayList<>();

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idMazo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Mazo_carta(rs.getInt("id_mazo"), rs.getInt("id_carta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina una carta de un mazo.
     *
     * @param idMazo  Identificador del mazo.
     * @param idCarta Identificador de la carta a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminar(int idMazo, int idCarta) {
        String sql = "DELETE FROM mazo_carta WHERE id_mazo = ? AND id_carta = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idMazo);
            ps.setInt(2, idCarta);

            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Carta eliminada del mazo correctamente" : "No se ha podido eliminar la carta del mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina todas las cartas de un mazo.
     *
     * @param idMazo Identificador del mazo a vaciar.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminarPorMazo(int idMazo) {
        String sql = "DELETE FROM mazo_carta WHERE id_mazo = ?";

        try {
            PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
            ps.setInt(1, idMazo);
            int resultado = ps.executeUpdate();
            System.out.println(resultado > 0 ? "Mazo vaciado correctamente" : "No se ha podido vaciar el mazo");
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

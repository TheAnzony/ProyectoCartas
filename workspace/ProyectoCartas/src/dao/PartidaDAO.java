package dao;

import java.awt.List;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

import conexion.ConexionBD;
import modulos.Partida;

public class PartidaDAO {

	/**
	 * Constructo vacio para usar metodos..
	 */

	public PartidaDAO() {

	}

	/**
	 * Insertar una nueva partida a la base de datos
	 * 
	 * @return Devuelve true si la operacion fue exitosa
	 */
	public boolean insertar(Partida pa) {
		String sql = "INSERT INTO partida (id_partida, id_jugador1, id_juugador2, id_mazo_j1, id_mazo_j2, id_estadio. fecha_inicio, fecha_fin, id_ganador, turnos_totales, velocidad_j1, velocidad_j2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  )";

		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, pa.getId_partida());
			ps.setInt(2, pa.getId_jugador1());
			ps.setInt(3, pa.getId_jugador2());
			ps.setInt(4, pa.getId_mazo_j1());
			ps.setInt(5, pa.getId_mazo_j2());
			ps.setInt(6, pa.getId_estadio());
			ps.setObject(7, pa.getFecha_inicio());
			ps.setObject(8, pa.getFecha_fin());
			ps.setInt(9, pa.getId_ganador());
			ps.setInt(10, pa.getTurnos_totales());
			ps.setObject(11, pa.getVelocidad_j1());
			ps.setObject(12, pa.getVelocidad_j2());

			int comprobacion = ps.executeUpdate();
			if (comprobacion > 0) {
				System.out.println("La partida se ha insertado correctamente");
				return true;
			} else {
				System.out.println("La partida no se ha podido insertar..");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Partida buscar(int id_partida1) {
		
		
		String sql = "SELECT * FROM partida WHERE id_partida = ?";
		
		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ps.setInt(1, id_partida1);
			
			ResultSet resultado = ps.executeQuery();
			
			while (resultado.next()) {
				int id_partida = resultado.getInt("id_partida");
				int id_jugador1 = resultado.getInt("id_jugador1");
				int id_jugador2 = resultado.getInt("id_jugador");
				int id_mazo_j1 = resultado.getInt("id_mazo_j1");
				int id_mazo_j2 = resultado.getInt("id_mazo_j2");
				int id_estadio = resultado.getInt("id_estadio");
				LocalDateTime fecha_inicio = resultado.getObject("fecha_inicio", LocalDateTime.class); // REVISAR
				LocalDateTime fecha_fin = resultado.getObject("fecha_fin", LocalDateTime.class) ; // REVISAR
				int id_ganador = resultado.getInt("id_ganador");
				int turnos_totales = resultado.getInt("turnos_totales");
				double velocidad_j1 = resultado.getDouble("velocidad_j1");
				double velocidad_j2 = resultado.getDouble("velocidad_j2");
				
				return new Partida(id_partida, id_jugador1, id_jugador2, id_mazo_j1, id_mazo_j2, id_estadio, fecha_inicio, fecha_fin, id_ganador, turnos_totales, velocidad_j1, velocidad_j2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Partida> listarHistorialJugador (int id_jugador) {
		
		String sql = "SELECT * FROM partida WHERE id_jugador = ?";
		ArrayList<Partida> misPartidas = new ArrayList<>();
		
		try {
			PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();
			
			while (resultado.next()) {
				int id_partida = resultado.getInt("id_partida");
				int id_jugador1 = resultado.getInt("id_jugador1");
				int id_jugador2 = resultado.getInt("id_jugador");
				int id_mazo_j1 = resultado.getInt("id_mazo_j1");
				int id_mazo_j2 = resultado.getInt("id_mazo_j2");
				int id_estadio = resultado.getInt("id_estadio");
				LocalDateTime fecha_inicio = resultado.getObject("fecha_inicio", LocalDateTime.class); // REVISAR
				LocalDateTime fecha_fin = resultado.getObject("fecha_fin", LocalDateTime.class) ; // REVISAR
				int id_ganador = resultado.getInt("id_ganador");
				int turnos_totales = resultado.getInt("turnos_totales");
				double velocidad_j1 = resultado.getDouble("velocidad_j1");
				double velocidad_j2 = resultado.getDouble("velocidad_j2");
				
				misPartidas.add(new Partida(id_partida, id_jugador1, id_jugador2, id_mazo_j1, id_mazo_j2, id_estadio, fecha_inicio, fecha_fin, id_ganador, turnos_totales, velocidad_j1, velocidad_j2));
				return misPartidas;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}

package test;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaConexion {

	public static void mostrarClientes(Connection conexion) {

		String query = "SELECT * FROM elemento";

		try {
			Statement comando = conexion.createStatement();

			ResultSet resultado = comando.executeQuery(query);

			/* Se imprimen los registros que estén guardados en la base de datos */
			while (resultado.next()) {
				System.out.println("id: " + resultado.getInt("id_elemento") + "\nNombre: " + resultado.getString("nombre") + "\ndescripccion: " + resultado.getString("descripcion"));

				System.out.println("------------------------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] MySQLConnection) {
	

		Connection conexion = ConexionBD.getInstancia().getConexion();

		// insertarClientes(conexion);
		mostrarClientes(conexion);

		System.out.println("Fin - Cerramos conexión");

		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

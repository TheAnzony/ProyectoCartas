package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Constantes de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/juego_cartas";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // La única instancia de esta clase
    private static ConexionBD instancia;

    // El objeto conexión
    private Connection conexion;

    // Constructor privado — nadie puede hacer new ConexionBD() desde fuera
    private ConexionBD() {
        try {
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }

    // Punto de acceso único — si no existe la crea, si existe la devuelve
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // Devuelve el objeto Connection para usarlo en los DAOs
    public Connection getConexion() {
        return conexion;
    }
}
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton que gestiona la conexión única a la base de datos MySQL.
 * Todos los DAOs obtienen la conexión a través de {@link #getInstancia()}.
 *
 * <p>Configuración: {@code jdbc:mysql://localhost:3306/juego_cartas}, usuario {@code root}, sin contraseña.</p>
 */
public class ConexionBD {

    private static final String URL      = "jdbc:mysql://localhost:3306/juego_cartas";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    /** Única instancia de la clase (patrón Singleton). */
    private static ConexionBD instancia;

    /** Objeto JDBC de conexión compartido por todos los DAOs. */
    private Connection conexion;

    /**
     * Constructor privado. Establece la conexión con la base de datos al instanciarse.
     * Lanza un mensaje de error por consola si no se puede conectar.
     */
    private ConexionBD() {
        try {
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la única instancia de {@code ConexionBD}, creándola si aún no existe.
     *
     * @return Instancia singleton de {@code ConexionBD}.
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Proporciona el objeto {@link Connection} JDBC para su uso en los DAOs.
     *
     * @return Conexión activa a la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }
}
package declarativo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Paradigma Declarativo - Conexión a MySQL/MariaDB mediante JDBC.
 *
 * Las credenciales se leen de variables de entorno para no escribir
 * contraseñas en el código. Los valores por defecto sirven para una
 * instalación local típica (Laragon / XAMPP: root sin contraseña).
 */
public class ConexionBD {

    private static final String HOST = env("MYSQL_HOST", "localhost");
    private static final String PUERTO = env("MYSQL_PORT", "3306");
    private static final String USUARIO = env("MYSQL_USER", "root");
    private static final String CLAVE = env("MYSQL_PASSWORD", "");
    private static final String BASE_DATOS = env("MYSQL_DATABASE", "control_gastos");

    private ConexionBD() {
    }

    private static String env(String nombre, String porDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }

    /** Abre la conexión, crea la base de datos y la tabla si no existen. */
    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://" + HOST + ":" + PUERTO
                + "/?useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC&characterEncoding=UTF-8";

        Connection conexion = DriverManager.getConnection(url, USUARIO, CLAVE);

        try (Statement st = conexion.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + BASE_DATOS
                    + " CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
        }
        conexion.setCatalog(BASE_DATOS);
        crearTabla(conexion);
        return conexion;
    }

    /** Declara la estructura de la tabla de gastos (DDL). */
    private static void crearTabla(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement()) {
            st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS gastos (
                        id        INT AUTO_INCREMENT PRIMARY KEY,
                        concepto  VARCHAR(100)   NOT NULL,
                        monto     DECIMAL(12, 2) NOT NULL CHECK (monto > 0),
                        categoria VARCHAR(50)    NOT NULL
                    )
                    """);
        }
    }
}

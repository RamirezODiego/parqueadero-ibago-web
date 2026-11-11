package co.com.ibago.parqueadero.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexion JDBC con MySQL.
 * Ajuste USUARIO y CLAVE segun su servidor local.
 */
public final class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/parqueadero_ibago?useSSL=false&serverTimezone=America/Bogota";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("No se encontro el driver JDBC de MySQL: " + e.getMessage());
        }
    }

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}

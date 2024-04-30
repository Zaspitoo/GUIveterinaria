package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Clase para la conexión con una base de datos MySQL
 * @author Francisco Jesús Delgado Almirón
 */
public class ConexionMySQL {
    private static Connection connection; // Conexión estática para reutilizar
    private static final String HOST = "sql.freedb.tech"; // Host de la base de datos
    private static final String USER = "freedb_andav"; // Usuario de la base de datos
    private static final String PASS = "6q4X3*dFM$$@JAV"; // Contraseña del usuario
    private static final String DB_NAME = "freedb_veterinariaclinica"; // Nombre de la base de datos

    /**
     * Constructor de la clase
     * @param usuario Usuario de la base de datos
     * @param pass Contraseña del usuario
     * @param bd Base de datos a la que nos conectamos
     */
    public ConexionMySQL(String usuario, String pass, String bd) {
        // Establecer la conexión inicial
        connect();
    }

    /**
     * Registra el driver de MySQL y establece la conexión si no existe o está cerrada.
     */
    static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                TimeZone timeZone = Calendar.getInstance().getTimeZone();
                String timeZoneParam = "&serverTimezone=" + timeZone.getID();
                connection = DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":3306/" + DB_NAME + "?user=" + USER +
                    "&password=" + PASS + "&useLegacyDatetimeCode=false" + timeZoneParam
                );
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Cierra la conexión con la base de datos.
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Ejecuta una consulta SELECT
     * @param query Consulta SELECT a ejecutar
     * @return Resultado de la consulta
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public static ResultSet executeSelect(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Ejecuta una consulta INSERT, DELETE o UPDATE
     * @param query Consulta INSERT, DELETE o UPDATE a ejecutar
     * @return Cantidad de filas afectadas
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public static int executeInsertDeleteUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    /**
     * Método para obtener la conexión actual a la base de datos.
     * @return Conexión a la base de datos
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
        }
        return connection;
    }

    public static Connection connect(String host2, String user2, String pass2, String dbName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'connect'");
    }
}

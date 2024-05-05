package com.example.BASEDEDATOS;
import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Clase para la conexión con una base de datos MySQL y manejo de operaciones específicas.
 */
public class ConexionMySQL {
    private static Connection connection; // Conexión estática para reutilizar
    private static final String HOST = "sql.freedb.tech"; // Host de la base de datos
    private static final String USER = "freedb_andav"; // Usuario de la base de datos
    private static final String PASS = "6q4X3*dFM$$@JAV"; // Usar variable de entorno para la contraseña
    private static final String DB_NAME = "freedb_veterinariaclinica"; // Nombre de la base de datos

    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                TimeZone timeZone = Calendar.getInstance().getTimeZone();
                String timeZoneParam = "&serverTimezone=" + timeZone.getID();
                String url = "jdbc:mysql://" + HOST + ":3306/" + DB_NAME + "?user=" + USER +
                    "&password=" + PASS + "&useLegacyDatetimeCode=false" + timeZoneParam;
                connection = DriverManager.getConnection(url);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Método para validar credenciales
    public static boolean validarCredenciales(String nombreUsuario, String contraseña) {
        try (Connection conn = connect()) {
            String sql = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombreUsuario);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        return contraseña.equals(storedPassword);
                    }
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }

    public static void agregarCita(String fechaHora, String motivo, String username) {
        String sql = "INSERT INTO citas (fecha_hora, motivo, username) VALUES (?, ?, ?)";
        try (Connection conn = ConexionMySQL.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fechaHora);
            pstmt.setString(2, motivo);
            pstmt.setString(3, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cita agregada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar cita: " + e.getMessage());
        }
    }
    

    
    // Método para verificar si un usuario existe por DNI
    public static boolean usuarioExistes(String dni) throws SQLException {
    String sql = "SELECT COUNT(*) FROM users WHERE dni = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, dni);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;  // returns true if count is greater than 0
            }
        }
    }
    return false;
}


    // Método para ejecutar una consulta SQL y devolver un ResultSet
    public static ResultSet executeQuery(String sql, String... parametros) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setString(i + 1, parametros[i]);
            }
            return pstmt.executeQuery();
        }
    }

    // Método para insertar una mascota
    public boolean insertarMascota(String dniUsuario, String nombreMascota, String sexoMascota) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();  // Ensure connection is open
        }
        String sql = "INSERT INTO mascotas (Usuario_DNI, nombre, Sexo) VALUES (?, ?, ?)";
        return executeInsert(sql, dniUsuario, nombreMascota, sexoMascota);
    }

    // Método para ejecutar una inserción SQL
    public boolean executeInsert(String sql, String... parametros) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setString(i + 1, parametros[i]);
            }
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // Método para consultar mascotas
    public static ResultSet consultarMascotas() throws SQLException {
        String sql = "SELECT * FROM Mascotas";
        return executeQuery(sql);
    }

    // Método para consultar citas
    public static ResultSet consultarCitas() throws SQLException {
        String sql = "SELECT * FROM Cita";
        return executeQuery(sql);
    }

    // Método para insertar un usuario
    public static boolean insertarUsuario(String username, String password, String DNI) throws SQLException {
        String sql = "INSERT INTO users (username, password, DNI) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, DNI);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // Método para cerrar la conexión
    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // Método para verificar si un usuario existe
    public static boolean usuarioExiste(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar si el usuario existe: " + e.getMessage());
        }
        return false;
    }
}

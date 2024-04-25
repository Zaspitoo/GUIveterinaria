package com.example;

// Importa todas las clases necesarias para manejar la base de datos.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBmanager {
    private Connection conexion; // Variable para almacenar la conexión con la base de datos.

    // Constructor que intenta establecer una conexión con la base de datos.
    public DBmanager() {
        try {
            // Intenta obtener la conexión usando la clase ConexionMySQL.
            this.conexion = ConexionMySQL.getConexion();
        } catch (SQLException e) {
            // Si hay un error, imprime un mensaje.
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Método para cerrar la conexión con la base de datos.
    public void cerrarConexion() {
        try {
            if (this.conexion != null) this.conexion.close(); // Cierra la conexión si no es nula.
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Método para añadir un nuevo usuario a la base de datos.
    public boolean insertarUsuario(String username, String password, String dniUsuario) throws SQLException {
        String sql = "INSERT INTO users (username, password, DNI) VALUES (?, ?, ?)"; // Consulta SQL.
        return executeInsert(sql, username, password, dniUsuario); // Ejecuta la inserción.
    }

    // Método para verificar las credenciales de un usuario.
    public boolean verificarCredenciales(String nombreUsuario, String contraseña) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return contraseña.equals(rs.getString("password")); // Compara las contraseñas.
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return false; // Devuelve falso si nohay coincidencias o si ocurre un error.
    }
// Método para verificar si un usuario ya existe en la base de datos.
public boolean usuarioExiste(String nombreUsuario) {
    String sql = "SELECT COUNT(*) AS cantidad FROM users WHERE username = ?";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, nombreUsuario);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cantidad") > 0; // Devuelve true si encuentra algún registro.
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar la existencia del usuario: " + e.getMessage());
    }
    return false;
}

// Método para añadir una nueva mascota a la base de datos.
public boolean insertarMascota(String dniUsuario, String nombreMascota, String sexoMascota) throws SQLException {
    String sql = "INSERT INTO Mascotas (Usuario_DNI, nombre, Sexo) VALUES (?, ?, ?)";
    return executeInsert(sql, dniUsuario, nombreMascota, sexoMascota); // Ejecuta la inserción y devuelve el resultado.
}

// Método para agregar una cita utilizando un procedimiento almacenado.
public void agregarCita(String fechaHora, String motivo, int mascotaId) {
    String call = "{ CALL AgregarCita(?, ?, ?) }";
    try (PreparedStatement stmt = conexion.prepareCall(call)) {
        stmt.setString(1, fechaHora);
        stmt.setString(2, motivo);
        stmt.setInt(3, mascotaId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error al agregar cita: " + e.getMessage());
    }
}

// Método para ejecutar consultas SQL y obtener un ResultSet.
public ResultSet executeQuery(String sql, String... parametros) throws SQLException {
    PreparedStatement pstmt = conexion.prepareStatement(sql);
    for (int i = 0; i < parametros.length; i++) {
        pstmt.setString(i + 1, parametros[i]);
    }
    return pstmt.executeQuery();
}

// Método para ejecutar actualizaciones SQL (como inserciones) y devolver si fueron exitosas.
public boolean executeInsert(String sql, String... parametros) throws SQLException {
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        for (int i = 0; i < parametros.length; i++) {
            pstmt.setString(i + 1, parametros[i]);
        }
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0;
    }
}
}
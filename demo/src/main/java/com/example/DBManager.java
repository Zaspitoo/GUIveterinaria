package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBmanager {
    private Connection conexion; // Variable para almacenar la conexión con la base de datos.

    // Constructor que intenta establecer una conexión con la base de datos.
    public DBmanager() throws SQLException {
        this.conexion = ConexionMySQL.getConnection();
    }
    
    public ResultSet consultarMascotas() throws SQLException {
        String sql = "SELECT * FROM Mascotas";
        return executeQuery(sql);
    }

    public ResultSet consultarCitas() throws SQLException {
        String sql = "SELECT * FROM Cita";
        return executeQuery(sql);
    }

    // Método para cerrar la conexión con la base de datos.
    public void cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                ConexionMySQL.disconnect(); // Usa el método estático para cerrar la conexión.
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Método para añadir un nuevo usuario a la base de datos.
    public boolean insertarUsuario(String username, String password, String dniUsuario) throws SQLException {
        String sql = "INSERT INTO users (username, password, DNI) VALUES (?, ?, ?)";
        return executeInsert(sql, username, password, dniUsuario);
    }

    // Método para verificar las credenciales de un usuario.
    public boolean verificarCredenciales(String nombreUsuario, String contraseña) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = this.conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return contraseña.equals(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar las credenciales: " + e.getMessage());
        }
        return false;
    }

    // Método para añadir una nueva mascota a la base de datos.
    public boolean insertarMascota(String dniUsuario, String nombreMascota, String sexoMascota) throws SQLException {
        String sql = "INSERT INTO Mascotas (Usuario_DNI, nombre, Sexo) VALUES (?, ?, ?)";
        return executeInsert(sql, dniUsuario, nombreMascota, sexoMascota);
    }

    
// Método para verificar si un usuario existe en la base de datos por nombre de usuario y DNI.
public boolean usuarioExiste(String nombreUsuario, String esDni) {
    // Actualiza la consulta SQL para incluir una verificación del DNI
    String sql = "SELECT username FROM users WHERE username = ? AND DNI = ? LIMIT 1";
    try (PreparedStatement pstmt = this.conexion.prepareStatement(sql)) {
        pstmt.setString(1, nombreUsuario); // Establece el nombre de usuario en el primer parámetro
        pstmt.setString(2, esDni); // Establece el DNI en el segundo parámetro
        ResultSet rs = pstmt.executeQuery(); // Ejecuta la consulta
        boolean existe = rs.next(); // Comprueba si hay algún resultado
        rs.close(); // Cierra el ResultSet
        return existe; // Devuelve true si el usuario existe, false si no
    } catch (SQLException e) {
        System.out.println("Error al verificar la existencia del usuario: " + e.getMessage());
        return false; // Devuelve false si ocurre un error
    }
}



    // Método para agregar una cita utilizando un procedimiento almacenado.
    public void agregarCita(String fechaHora, String motivo, int mascotaId) {
        String call = "{ CALL AgregarCita(?, ?, ?) }";
        try (PreparedStatement stmt = this.conexion.prepareCall(call)) {
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
        PreparedStatement pstmt = this.conexion.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            pstmt.setString(i + 1, parametros[i]);
        }
        return pstmt.executeQuery();
    }

    // Método para ejecutar actualizaciones SQL (como inserciones) y devolver si fueron exitosas.
    public boolean executeInsert(String sql, String... parametros) throws SQLException {
        try (PreparedStatement pstmt = this.conexion.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setString(i + 1, parametros[i]);
            }
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
}

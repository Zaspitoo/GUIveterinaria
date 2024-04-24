package com.example;
import java.sql.*;

public class DBManager {
    private Connection connection;

    // Constructor ajustado para aceptar los parámetros de conexión
    public DBManager(String host, int port, String databaseName, String user, String password) throws SQLException {
        System.out.println("🔌 Intentando conectar a la base de datos...");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("✅ Conexión establecida exitosamente!");
    }

    public DBManager(String url, String user, String password) throws SQLException {
        System.out.println("🔌 Intentando conectar a la base de datos...");
        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("✅ Conexión establecida exitosamente!");
    }

    // Método para insertar usuarios con seguridad de contraseña
    public boolean insertarUsuario(String nombre, String email, String hashedPassword) {
        String query = "INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, nombre);
            pst.setString(2, email);
            pst.setString(3, hashedPassword);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Ejemplo de método para consultar usuarios
    public ResultSet consultarUsuarios() {
        String query = "SELECT * FROM usuarios";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error al consultar usuarios: " + e.getMessage());
            return null;
        }
    }

    // Ejemplo de método para actualizar usuarios
    public boolean actualizarUsuario(int id, String nuevoNombre) {
        String query = "UPDATE usuarios SET nombre = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, nuevoNombre);
            pst.setInt(2, id);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Ejemplo de método para eliminar usuarios
    public boolean eliminarUsuario(int id) {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
    public boolean verificarCredenciales(String usuario, String password) {
        String query = "SELECT password FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, usuario);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Obtener la contraseña almacenada
                String storedPassword = rs.getString("password");
                // Comprobar si las contraseñas coinciden
                // Aquí deberías implementar la lógica de comparación de hashing si estás usando contraseñas hasheadas
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales: " + e.getMessage());
        }
        return false;
    }
    public boolean registrarUsuario(String nombre, String email, String password) {
        String query = "INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, nombre);
            pst.setString(2, email);
            pst.setString(3, password);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // Método para ejecutar inserciones, actualizaciones y eliminaciones
    public int executeInsert(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);  // Retorna el número de filas afectadas
    }

}

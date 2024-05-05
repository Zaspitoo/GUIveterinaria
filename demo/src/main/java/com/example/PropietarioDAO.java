// Paquete que organiza las clases relacionadas con el acceso a datos de propietarios.
package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Clase que implementa las operaciones de base de datos para los propietarios.
public class PropietarioDAO {

    private Connection connection; // Conexión a la base de datos.

    // Constructor que recibe una conexión a base de datos.
    public PropietarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar un nuevo propietario a la base de datos.
    public boolean agregarPropietario(Connection conn, Propietario propietario) throws SQLException {
        String sql = "INSERT INTO propietarios (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propietario.getID());
            pstmt.setString(2, propietario.getNombre());
            pstmt.setString(3, propietario.getTelefono());
            pstmt.setString(4, propietario.getDireccion());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Retorna true si se insertó al menos un registro.
        }
    }

    // Método para actualizar la información de un propietario existente en la base de datos.
    public boolean actualizarPropietario(Connection conn, Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setInt(4, propietario.getID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Retorna true si se modificó al menos un registro.
        }
    }

    // Método para eliminar un propietario de la base de datos utilizando su ID.
    public boolean eliminarPropietario(Connection conn, int ID) throws SQLException {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Retorna true si se eliminó al menos un registro.
        }
    }

    // Método para buscar un propietario por su ID y retornar un objeto Propietario si existe.
    public Optional<Propietario> buscarPropietario(Connection conn, int ID) throws SQLException {
        String sql = "SELECT * FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Propietario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                    ));
                }
            }
        }
        return Optional.empty(); // Retorna un Optional vacío si no se encuentra el propietario.
    }

    // Método para listar todos los propietarios registrados en la base de datos.
    public List<Propietario> listarPropietarios(Connection conn) throws SQLException {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT * FROM propietarios";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                propietarios.add(new Propietario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                ));
            }
        }
        return propietarios; // Retorna la lista de propietarios.
    }
}

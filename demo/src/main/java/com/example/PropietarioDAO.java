package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropietarioDAO {
    private Connection connection;

    // Constructor que permite inyección de la conexión para mejor manejo de pruebas y transacciones
    public PropietarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Constructor que permite la creación de una conexión a la base de datos
    public PropietarioDAO() {
        this.connection = ConexionMySQL.getConnection();
    }

    public boolean agregarPropietario(Propietario propietario) throws SQLException {
        String sql = "INSERT INTO propietarios (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, propietario.getID());  // Cambiado de setInt a setString
            pstmt.setString(2, propietario.getNombre());
            pstmt.setString(3, propietario.getTelefono());
            pstmt.setString(4, propietario.getDireccion());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean actualizarPropietario(Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setInt(4, propietario.getID());  // Cambiado de setInt a setString
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean eliminarPropietario(int ID) throws SQLException {  // Cambio del tipo de ID a String
        String sql = "DELETE FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Optional<Propietario> buscarPropietario(int ID) throws SQLException {  // Cambio del tipo de ID a String
        String sql = "SELECT * FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        return Optional.empty();
    }

    public List<Propietario> listarPropietarios() throws SQLException {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT * FROM propietarios";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
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
        return propietarios;
    }
}

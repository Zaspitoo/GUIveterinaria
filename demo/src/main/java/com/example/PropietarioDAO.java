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

    public PropietarioDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean agregarPropietario(Connection conn, Propietario propietario) throws SQLException {
        String sql = "INSERT INTO propietarios (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propietario.getID());
            pstmt.setString(2, propietario.getNombre());
            pstmt.setString(3, propietario.getTelefono());
            pstmt.setString(4, propietario.getDireccion());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean actualizarPropietario(Connection conn, Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setInt(4, propietario.getID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean eliminarPropietario(Connection conn, int ID) throws SQLException {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

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
        return Optional.empty();
    }

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
        return propietarios;
    }
}

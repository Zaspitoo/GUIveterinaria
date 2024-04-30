package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropietarioDAO {
    private Connection conexion;

    public PropietarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean agregarPropietario(Propietario propietario) throws SQLException {
        String sql = "INSERT INTO propietarios (nombre, telefono, direccion, ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setLong(4, propietario.getID());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean actualizarPropietario(Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre = ?, telefono = ?, direccion = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setInt(4, propietario.getID());
    
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean eliminarPropietario(Integer ID) throws SQLException {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Optional<Propietario> buscarPropietarioPorId(Integer ID) throws SQLException {
        String sql = "SELECT * FROM propietarios WHERE ID = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Propietario(
                        rs.getInt("ID"),
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
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Propietario propietario = new Propietario(
                    rs.getInt("ID"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
                propietarios.add(propietario);
            }
        }
        return propietarios;
    }

    @SuppressWarnings("exports")
    public Connection getConnect() {
        return conexion;
    }
}

package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropietarioDAO {
    private Connection conexion;

    public PropietarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean agregarPropietario(Propietario propietario) throws SQLException {
        String sql = "INSERT INTO propietarios (nombre, telefono, direccion) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean actualizarPropietario(Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, propietario.getNombre());
            pstmt.setString(2, propietario.getTelefono());
            pstmt.setString(3, propietario.getDireccion());
            pstmt.setInt(4, propietario.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarPropietario(int id) throws SQLException {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Propietario obtenerPropietario(int id) throws SQLException {
        String sql = "SELECT * FROM propietarios WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Propietario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
            }
        }
        return null;
    }

    public List<Propietario> listarPropietarios() throws SQLException {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT * FROM propietarios";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Propietario propietario = new Propietario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
                propietarios.add(propietario);
            }
        }
        return propietarios;
    }
}

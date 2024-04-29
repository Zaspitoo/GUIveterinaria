package com.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistorialMedicoDAO {

    private Connection conexion;

    public HistorialMedicoDAO(@SuppressWarnings("exports") Connection conexion) {
        this.conexion = conexion;
    }

    public boolean agregarHistorial(HistorialMedico historial) {
        String sql = "INSERT INTO historiales_medicos (mascotaId, fecha, detalles) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, historial.getMascotaId());
            pstmt.setString(2, historial.getFecha());
            pstmt.setString(3, historial.getDetalles());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar historial médico: " + e.getMessage());
            return false;
        }
    }

    public HistorialMedico obtenerHistorial(int id) {
        String sql = "SELECT * FROM historiales_medicos WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new HistorialMedico(
                        rs.getInt("id"),
                        rs.getInt("mascotaId"),
                        rs.getString("fecha"),
                        rs.getString("detalles")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener historial médico: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminarHistorial(int id) {
        String sql = "DELETE FROM historiales_medicos WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar historial médico: " + e.getMessage());
            return false;
        }
    }
}


package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    private Connection conexion;

    public CitaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean agregarCitas(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (propietario_id, fecha_hora, motivo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, cita.getPropietarioId());
            pstmt.setString(2, cita.getFechaHora());
            pstmt.setString(3, cita.getMotivo());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean actualizarCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET propietario_id = ?, fecha_hora = ?, motivo = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, cita.getPropietarioId());
            pstmt.setString(2, cita.getFechaHora());
            pstmt.setString(3, cita.getMotivo());
            pstmt.setString(4, cita.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean eliminarCita(String id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Cita obtenerCita(String id) throws SQLException {
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cita(
                        
                        rs.getString("propietario_id"),
                        rs.getString("fecha_hora"),
                        rs.getString("motivo")
                    );
                }
            }
        }
        return null;
    }

    public List<Cita> listarCitas() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                citas.add(new Cita(
                    
                    rs.getString("propietario_id"),
                    rs.getString("fecha_hora"),
                    rs.getString("motivo")
                ));
            }
        }
        return citas;
    }
}

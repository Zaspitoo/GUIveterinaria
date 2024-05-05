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

    public boolean agregarCitas(Connection conn, Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (username, motivo, fecha_hora) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cita.getusername());
            pstmt.setString(2, cita.getMotivo());
            pstmt.setString(3, cita.getFechaHora());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean actualizarCita(Connection conn, Cita cita) throws SQLException {
        String sql = "UPDATE citas SET username = ?, fecha_hora = ?, motivo = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cita.getusername());
            pstmt.setString(2, cita.getFechaHora());
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean eliminarCita(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Cita obtenerCita(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cita(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("fecha_hora"),
                        rs.getString("motivo")
                    );
                }
            }
        }
        return null;
    }

    public List<Cita> listarCitas(Connection conn) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                citas.add(new Cita(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("fecha_hora"),
                    rs.getString("motivo")
                ));
            }
        }
        return citas;
    }
}

package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para manejar las operaciones de la base de datos para las citas.
 */
public class CitaDAO {
    private Connection conexion;  // Conexión a la base de datos.

    /**
     * Constructor que inicializa la conexión a la base de datos.
     * conexion La conexión activa a la base de datos.
     */
    public CitaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Agrega una nueva cita a la base de datos.
     * conn La conexión a la base de datos.
     * cita El objeto Cita que contiene los datos a insertar.
     * return true si la inserción es exitosa, false en caso contrario.
     * throws SQLException Si ocurre un error de SQL.
     */
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

    /**
     * Actualiza los datos de una cita existente en la base de datos.
     * conn La conexión a la base de datos.
     * cita El objeto Cita que contiene los datos actualizados.
     * return true si la actualización es exitosa, false en caso contrario.
     * throws SQLException Si ocurre un error de SQL.
     */
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

    /**
     * Elimina una cita de la base de datos usando su ID.
     * conn La conexión a la base de datos.
     * id El identificador de la cita a eliminar.
     * return true si la eliminación es exitosa, false en caso contrario.
     * throws SQLException Si ocurre un error de SQL.
     */
    public boolean eliminarCita(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Obtiene una cita de la base de datos usando su ID.
     * conn La conexión a la base de datos.
     * id El identificador de la cita a buscar.
     * return Un objeto Cita si se encuentra, null en caso contrario.
     * throws SQLException Si ocurre un error de SQL.
     */
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

    /**
     * Devuelve una lista de todas las citas registradas en la base de datos.
     *  conn La conexión a la base de datos.
     * return Una lista de objetos Cita.
     * throws SQLException Si ocurre un error de SQL.
     */
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

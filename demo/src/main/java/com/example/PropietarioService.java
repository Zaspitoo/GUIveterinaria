package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PropietarioService {
    private static PropietarioDAO propietarioDao;
    private static final String HOST = "sql.freedb.tech";
    private static final String USER = "freedb_andav";
    private static final String PASS = "6q4X3*dFM$$@JAV";
    private static final String DB_NAME = "freedb_veterinariaclinica";
    private Connection conexion;

    

    public PropietarioService(PropietarioDAO propietarioDao2 ) {
        this.conexion = propietarioDao2.getConnect();
        PropietarioService.propietarioDao = new PropietarioDAO(this.conexion);
    }

    public static PropietarioService createPropietarioService() throws SQLException {
        PropietarioService service = new PropietarioService(propietarioDao);
        service.conexion = ConexionMySQL.connect(HOST, USER, PASS, DB_NAME);
        PropietarioService.propietarioDao = new PropietarioDAO(service.conexion);
        return service;
    }

    public void close() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    public boolean registrarPropietario(Propietario propietario) {
        try {
            return propietarioDao.agregarPropietario(propietario);
        } catch (SQLException e) {
            System.out.println("Error al registrar el propietario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPropietario(Propietario propietario) {
        try {
            boolean resultado = propietarioDao.actualizarPropietario(propietario);
            if (!resultado) {
                System.out.println("No se pudo actualizar el propietario debido a un problema en la base de datos.");
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el propietario: " + e.getMessage());
            
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrarPropietario(Integer ID) {
        try {
            return propietarioDao.eliminarPropietario(ID);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el propietario: " + e.getMessage());
            return false;
        }
    }

    public Optional<Propietario> buscarPropietario(Integer ID) throws SQLException {
        String sql = "SELECT * FROM propietarios WHERE id = ?";
        try (Connection conexion = Connection(); // Asume que tienes un método para obtener la conexión
                PreparedStatement pstmt = conexion.prepareStatement(sql)) {
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

    private Connection Connection() throws SQLException {
        return ConexionMySQL.getConnection();
    }

    public List<Propietario> listarPropietarios() {
        try {
            return propietarioDao.listarPropietarios();
        } catch (SQLException e) {
            System.out.println("Error al listar los propietarios: " + e.getMessage());
            return List.of();
        }
    }
}

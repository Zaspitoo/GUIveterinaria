package com.example.SERVICIOS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.CitaDAO;
import com.example.MODELOS.Cita;
import com.example.BASEDEDATOS.ConexionMySQL;

/**
 * Clase de servicio que gestiona las operaciones sobre citas mediante un objeto DAO.
 */
public class CitaService {

    private CitaDAO citaDAO;  // Objeto DAO para manejar las operaciones de base de datos para citas.

    /**
     * Constructor que inicializa el DAO con la conexión a la base de datos.
     *  citaDAO DAO para manejar las operaciones de base de datos para citas.
     */
    public CitaService(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    /**
     * Registra una nueva cita en la base de datos.
     *  cita Objeto Cita que será registrado.
     *  true si el registro es exitoso, false de lo contrario.
     */
    public boolean registrarCita(Cita cita) {
        try (Connection conn = ConexionMySQL.connect()) {
            return citaDAO.agregarCitas(conn, cita);
        } catch (SQLException e) {
            System.out.println("Error al registrar la cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza una cita existente.
     * cita Objeto Cita con datos actualizados.
     *  true si la actualización es exitosa, false de lo contrario.
     */
    public boolean actualizarCita(Cita cita) {
        try (Connection conn = ConexionMySQL.connect()) {
            return citaDAO.actualizarCita(conn, cita);
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una cita de la base de datos.
     * id Identificador de la cita a eliminar.
     *  true si la eliminación es exitosa, false de lo contrario.
     */
    public boolean borrarCita(int id) {
        try (Connection conn = ConexionMySQL.connect()) {
            return citaDAO.eliminarCita(conn, id);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca una cita por su ID.
     *  Identificador de la cita.
     *  Cita si se encuentra, null de lo contrario.
     */
    public Cita buscarCita(int id) {
        try (Connection conn = ConexionMySQL.connect()) {
            return citaDAO.obtenerCita(conn, id);
        } catch (SQLException e) {
            System.out.println("Error al buscar la cita: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todas las citas registradas en la base de datos.
     *  Una lista de citas.
     */
    public List<Cita> listarCitas() {
        try (Connection conn = ConexionMySQL.connect()) {
            return citaDAO.listarCitas(conn);
        } catch (SQLException e) {
            System.out.println("Error al listar las citas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

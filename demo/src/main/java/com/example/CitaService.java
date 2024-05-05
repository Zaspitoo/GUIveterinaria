package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaService {

    private CitaDAO citaDAO;

    /**
     * Constructor that initializes the DAO with the database connection.
     * @param citaDAO DAO to handle database operations for appointments.
     */
    public CitaService(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    /**
     * Registers a new appointment in the database.
     * @param cita Appointment object to be registered.
     * @return true if the registration is successful, false otherwise.
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
     * Updates an existing appointment.
     * @param cita Appointment object with updated data.
     * @return true if the update is successful, false otherwise.
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
     * Deletes an appointment from the database.
     * @param id Identifier of the appointment to delete.
     * @return true if the deletion is successful, false otherwise.
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
     * Searches for an appointment by its ID.
     * @param id Identifier of the appointment.
     * @return Appointment if found, null otherwise.
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
     * Lists all registered appointments in the database.
     * @return A list of appointments.
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

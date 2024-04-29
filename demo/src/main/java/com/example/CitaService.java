package com.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaService {

    private CitaDAO citaDAO;

    public CitaService(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    public boolean registrarCita(Cita cita) {
        try {
            return citaDAO.agregarCita(cita);
        } catch (SQLException e) {
            System.out.println("Error al registrar la cita: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCita(Cita cita) {
        try {
            return citaDAO.actualizarCita(cita);
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cita: " + e.getMessage());
            return false;
        }
    }

    public boolean borrarCita(int id) {
        try {
            return citaDAO.eliminarCita(id);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la cita: " + e.getMessage());
            return false;
        }
    }

    public Cita buscarCita(int id) {
        try {
            return citaDAO.obtenerCita(id);
        } catch (SQLException e) {
            System.out.println("Error al buscar la cita: " + e.getMessage());
            return null;
        }
    }

    public List<Cita> listarCitas() {
        try {
            return citaDAO.listarCitas();
        } catch (SQLException e) {
            System.out.println("Error al listar las citas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

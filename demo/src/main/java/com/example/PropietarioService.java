package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropietarioService {
    private PropietarioDAO propietarioDao;

    /**
     * Constructor that initializes the DAO.
     * @param propietarioDao DAO for managing database operations for proprietors.
     */
    public PropietarioService(PropietarioDAO propietarioDao) {
        this.propietarioDao = propietarioDao;
    }

    /**
     * Registers a new proprietor in the database.
     * @param propietario Proprietor object to be registered.
     * @return true if the registration is successful, false otherwise.
     */
    public boolean registrarPropietario(Propietario propietario) {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.agregarPropietario(conn, propietario);
        } catch (SQLException e) {
            System.err.println("Error al registrar el propietario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifies the data of an existing proprietor.
     * @param propietario Proprietor object with updated data.
     * @return true if the modification is successful, false otherwise.
     */
    public boolean modificarPropietario(Propietario propietario) {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.actualizarPropietario(conn, propietario);
        } catch (SQLException e) {
            System.err.println("Error al modificar el propietario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a proprietor from the database.
     * @param ID Identifier of the proprietor to delete.
     * @return true if the deletion is successful, false otherwise.
     */
    public boolean eliminarPropietario(int ID) {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.eliminarPropietario(conn, ID);
        } catch (SQLException e) {
            System.err.println("Error al eliminar el propietario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a proprietor by their ID.
     * @param ID Identifier of the proprietor.
     * @return An Optional containing the proprietor if found, or an empty Optional if not found.
     */
    public Optional<Propietario> obtenerPropietario(int ID) {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.buscarPropietario(conn, ID);
        } catch (SQLException e) {
            System.err.println("Error al buscar el propietario: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean actualizarPropietario(Propietario propietario) {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.actualizarPropietario(conn, propietario);
        } catch (SQLException e) {
            System.err.println("Error updating propietario: " + e.getMessage());
            return false;
        }
    }
    

    /**
     * Lists all registered proprietors in the database.
     * @return A list of proprietors.
     */
    public List<Propietario> listarPropietarios() {
        try (Connection conn = ConexionMySQL.connect()) {
            return propietarioDao.listarPropietarios(conn);
        } catch (SQLException e) {
            System.err.println("Error al listar los propietarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

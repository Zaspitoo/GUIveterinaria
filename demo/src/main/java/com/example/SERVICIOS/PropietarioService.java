// Paquete que organiza las clases relacionadas con el servicio de propietarios.
package com.example.SERVICIOS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.DAO.PropietarioDAO;
import com.example.MODELOS.Propietario;
import com.example.BASEDEDATOS.ConexionMySQL;

// Clase que maneja las operaciones del negocio relacionadas con los propietarios.
public class PropietarioService {
    private PropietarioDAO propietarioDao; // DAO asociado para las operaciones de base de datos.

    /**
     * Constructor que inicializa el DAO de propietarios.
     * @param propietarioDao DAO para gestionar operaciones de base de datos de propietarios.
     */
    public PropietarioService(PropietarioDAO propietarioDao) {
        this.propietarioDao = propietarioDao;
    }

    /**
     * Registra un nuevo propietario en la base de datos.
     *  Objeto Propietario a registrar.
     * true si el registro es exitoso, false de lo contrario.
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
     * Modifica los datos de un propietario existente.
     *  Objeto Propietario con datos actualizados.
     *  true si la modificación es exitosa, false de lo contrario.
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
     * Elimina un propietario de la base de datos.
     * Identificador del propietario a eliminar.
     *  true si la eliminación es exitosa, false de lo contrario.
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
     * Recupera un propietario por su ID.
     * 
     *  Un Optional que contiene al propietario si se encuentra, o un Optional vacío si no se encuentra.
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
     * Lista todos los propietarios registrados en la base de datos.
     *  Una lista de propietarios.
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

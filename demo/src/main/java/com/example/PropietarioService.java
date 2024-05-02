package com.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PropietarioService {
    private PropietarioDAO propietarioDao;

    public PropietarioService(PropietarioDAO propietarioDao) {
        this.propietarioDao = propietarioDao;
    }

    public boolean registrarPropietario(Propietario propietario) {
        try {
            return propietarioDao.agregarPropietario(propietario);
        } catch (SQLException e) {
            System.out.println("Error al registrar el propietario: " + e.getMessage());
            return false;
        }
    }

    public boolean modificarPropietario(Propietario propietario) {
        try {
            return propietarioDao.actualizarPropietario(propietario);
        } catch (SQLException e) {
            System.out.println("Error al modificar el propietario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPropietario(int ID) {  // Cambiado el tipo de ID a String
        try {
            return propietarioDao.eliminarPropietario(ID);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el propietario: " + e.getMessage());
            return false;
        }
    }

    public Optional<Propietario> obtenerPropietario(int ID) {  // Cambiado el tipo de ID a String
        try {
            return propietarioDao.buscarPropietario(ID);
        } catch (SQLException e) {
            System.out.println("Error al buscar el propietario: " + e.getMessage());
            return Optional.empty();
        }
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

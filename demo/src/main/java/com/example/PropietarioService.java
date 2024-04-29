package com.example;

import java.sql.SQLException;
import java.util.List;

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

    public boolean actualizarPropietario(Propietario propietario) {
        try {
            return propietarioDao.actualizarPropietario(propietario);
        } catch (SQLException e) {
            System.out.println("Error al actualizar el propietario: " + e.getMessage());
            return false;
        }
    }

    public boolean borrarPropietario(int id) {
        try {
            return propietarioDao.eliminarPropietario(id);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el propietario: " + e.getMessage());
            return false;
        }
    }

    public Propietario buscarPropietario(int id) {
        try {
            return propietarioDao.obtenerPropietario(id);
        } catch (SQLException e) {
            System.out.println("Error al buscar el propietario: " + e.getMessage());
            return null;
        }
    }

    public List<Propietario> listarPropietarios() {
        try {
            return propietarioDao.listarPropietarios();
        } catch (SQLException e) {
            System.out.println("Error al listar propietarios: " + e.getMessage());
            return null;
        }
    }
}

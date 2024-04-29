package com.example;

import java.sql.SQLException;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBmanager gestorDB;
        try {
            gestorDB = new DBmanager(); // Intenta crear un objeto para gestionar la base de datos.
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return; // Termina la ejecución si no se puede establecer conexión con la base de datos.
        }

        // Opciones para el usuario al iniciar la aplicación, eliminando la opción de Registrar Mascota.
        Object[] opciones = {"Iniciar Sesión", "Registrarse", "Administrar Veterinaria"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Qué acción desea realizar?",
                "Inicio de aplicación",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        // Manejo de la respuesta del usuario mediante un switch.
        switch (respuesta) {
            case 0: // Opción de iniciar sesión.
                new LoginFrame(gestorDB).setVisible(true);
                break;
            case 1: // Opción para registrarse como nuevo usuario.
                new RegistroFrame(gestorDB, null).setVisible(true);
                break;
            case 2: // Opción para administrar la clínica (requiere login de administrador).
                AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
                adminLoginFrame.setVisible(true);
                break;
            default:
                System.exit(0); // Cierra la aplicación si no se elige una opción válida o se cierra el diálogo.
                break;
        }
    }
}

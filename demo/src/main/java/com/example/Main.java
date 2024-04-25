package com.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DBmanager gestorDB = new DBmanager();  // Crea un objeto para gestionar la base de datos.
        MainFrame mainFrame = new MainFrame(gestorDB); // Crea la ventana principal de la aplicación.

        // Crea un diálogo que permite al usuario elegir entre iniciar sesión, registrarse o registrar una mascota.
        Object[] opciones = {"Iniciar Sesión", "Registrarse", "Registrar Mascota", "Administrar Veterinaria"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Deseas iniciar sesión, registrarte, registrar una mascota o administrar la veterinaria?",
                "Inicio de aplicación",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones, opciones[0]);
        switch (respuesta) {
            case JOptionPane.YES_OPTION: // Iniciar sesión
                new LoginFrame(gestorDB).setVisible(true);
                break;
            case JOptionPane.NO_OPTION: // Registrarse
                new RegistroFrame(gestorDB).setVisible(true);
                break;
            case JOptionPane.CANCEL_OPTION: // Registrar Mascota
                new RegistroMascotas(gestorDB).setVisible(true);
                break;
            case JOptionPane.CLOSED_OPTION: // Opción adicional para manejar la clínica veterinaria
                mainFrame.setVisible(true);
                break;
            default:
                System.exit(0); // Cierra la aplicación si se cierra el diálogo o se elige una opción no contemplada.
                break;
        }
    }
}

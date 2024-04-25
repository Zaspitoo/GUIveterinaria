package com.example;

import javax.swing.*;  // Importa las clases necesarias para crear interfaces gráficas.

public class Main {
    public static void main(String[] args) {
        DBmanager gestorDB = new DBmanager();  // Crea un objeto para gestionar la base de datos.

        // Crea un diálogo que permite al usuario elegir entre iniciar sesión, registrarse o registrar una mascota.
        Object[] opciones = {"Iniciar Sesión", "Registrarse", "Registrar Mascota"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Deseas iniciar sesión, registrarte o registrar una mascota?",
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
            default:
                System.exit(0); // Cierra la aplicación si se cierra el diálogo o se elige una opción no contemplada.
                break;
        }
    }
}

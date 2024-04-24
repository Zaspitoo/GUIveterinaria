package com.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DBmanager dbManager = new DBmanager();

        int response = JOptionPane.showOptionDialog(null, "¿Deseas iniciar sesión o registrarte?",
                "Inicio de aplicación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Iniciar Sesión", "Registrarse"}, "Iniciar Sesión");

        if (response == JOptionPane.YES_OPTION) {
            new LoginFrame(dbManager).setVisible(true);
        } else {
            new RegistroFrame(dbManager).setVisible(true);
        }
    }
}

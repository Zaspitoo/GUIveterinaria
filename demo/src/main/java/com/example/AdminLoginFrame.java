package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminLoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ConexionMySQL gestorDB; // Reference to the database connection manager

    public AdminLoginFrame() throws SQLException {
        gestorDB = new ConexionMySQL(); // Initialize the database connection manager

        setTitle("Administración de la Clínica Veterinaria");
        setSize(300, 125);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(this::adminLogin);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(btnLogin);

        add(panel);
    }

    private void adminLogin(ActionEvent e) {
        String adminUsername = "admin";
        String adminPassword = "test";

        String enteredUsername = txtUsername.getText();
        String enteredPassword = new String(txtPassword.getPassword());

        if (adminUsername.equals(enteredUsername) && adminPassword.equals(enteredPassword)) {
            launchAdminMainFrame(gestorDB);
            this.dispose(); // Close the login window on successful login.
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void launchAdminMainFrame(ConexionMySQL gestorDB) {
        // This method will initialize AdminMainFrame with the required services
        Connection dbConnection = ConexionMySQL.connect(); // This method should exist and return a valid connection

        PropietarioDAO propietarioDao = new PropietarioDAO(dbConnection);
        CitaDAO citaDao = new CitaDAO(dbConnection);

        PropietarioService propietarioService = new PropietarioService((PropietarioDAO) propietarioDao);
        CitaService citaService = new CitaService(citaDao);

        AdminMainFrame adminMainFrame = new AdminMainFrame(gestorDB, propietarioService, citaService);
        adminMainFrame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
                adminLoginFrame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}

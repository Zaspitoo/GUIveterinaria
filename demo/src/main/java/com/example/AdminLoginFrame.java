package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AdminLoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private DBmanager gestorDB; // Agregar la referencia al gestor de base de datos

    public AdminLoginFrame() throws SQLException {
        // Inicializar el gestor de base de datos
        gestorDB = new DBmanager();

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
        String adminUsername = "admin"; // Define aquí el usuario administrador.
        String adminPassword = "test"; // Define aquí la contraseña del administrador.
        
        String enteredUsername = txtUsername.getText();
        String enteredPassword = new String(txtPassword.getPassword());

        if (adminUsername.equals(enteredUsername) && adminPassword.equals(enteredPassword)) {
            // Pasar la instancia de DBmanager al AdminMainFrame
            AdminMainFrame adminMainFrame = new AdminMainFrame(gestorDB);
            adminMainFrame.setVisible(true);
            this.dispose(); // Cierra la ventana de inicio de sesión.
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

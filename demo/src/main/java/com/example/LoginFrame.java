package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private DBmanager dbManager;

    public LoginFrame(DBmanager dbManager) {
        this.dbManager = dbManager;
        setTitle("Inicio de Sesión - Clínica Veterinaria");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(this::loginUser);
        panel.add(btnLogin);

        add(panel);
    }

    private void loginUser(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        try {
            if (dbManager.verifyCredentials(usuario, password)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!");
                new MainFrame(dbManager).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        DBmanager dbManager = new DBmanager();
        new LoginFrame(dbManager).setVisible(true);
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class RegistroFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private DBmanager dbManager;

    public RegistroFrame(DBmanager dbManager) {
        this.dbManager = dbManager;
        setTitle("Registro de Usuario - Clínica Veterinaria");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Usuario:"));
        txtUsername = new JTextField(15);
        panel.add(txtUsername);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword);

        btnRegister = new JButton("Registrar");
        btnRegister.addActionListener(this::registerUser);
        panel.add(btnRegister);

        add(panel);
    }

    private void registerUser(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        try {
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dbManager.userExists(username)) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String hashedPassword = hashPassword(password);
            if (dbManager.addUser(username, hashedPassword)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
                this.dispose();
                new LoginFrame(dbManager).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    public static void main(String[] args) {
        DBmanager dbManager = new DBmanager();
        new RegistroFrame(dbManager).setVisible(true);
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JTextField txtDniUsuario;
    private JButton btnRegistrar;
    private DBmanager gestorDB;

    public RegistroFrame(DBmanager gestorDB) {
        this.gestorDB = gestorDB;
        setTitle("Registro de Usuario - Clínica Veterinaria");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));  // Usar GridLayout para ordenar los componentes.

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        panel.add(new JLabel("DNI:"));
        txtDniUsuario = new JTextField(15);
        panel.add(txtDniUsuario);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this::registrarUsuario);
        panel.add(new JLabel());  // Espaciador para alinear el botón en la grilla.
        panel.add(btnRegistrar);

        add(panel);
    }

    private void registrarUsuario(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String dniUsuario = txtDniUsuario.getText().trim();

        try {
            if (usuario.isEmpty() || contraseña.isEmpty() || dniUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorDB.usuarioExiste(usuario, dniUsuario)) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario o DNI ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorDB.insertarUsuario(usuario, contraseña, dniUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                this.dispose();
                new LoginFrame(gestorDB).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DBmanager gestorDB = null;
            try {
                gestorDB = new DBmanager();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new RegistroFrame(gestorDB).setVisible(true);
        });
    }
}

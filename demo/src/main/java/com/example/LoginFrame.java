package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnIniciarSesion;

    public LoginFrame(ConexionMySQL gestorDB) {
        setTitle("Inicio de Sesión - Clínica Veterinaria");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this::iniciarSesion);
        panel.add(btnIniciarSesion);

        add(panel);
    }

    private void iniciarSesion(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim();
        char[] contraseña = txtContraseña.getPassword();

        try {
            if (ConexionMySQL.validarCredenciales(usuario, new String(contraseña))) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!");
                // Aquí deberías abrir el frame principal después del inicio de sesión
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                this.dispose();  // Cerrar el frame de inicio de sesión después del inicio de sesión exitoso
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Limpiar la contraseña de la memoria tan pronto como sea posible
            Arrays.fill(contraseña, '0');
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionMySQL gestorDB = new ConexionMySQL();
            LoginFrame loginFrame = new LoginFrame(gestorDB);
            loginFrame.setVisible(true);
        });
    }
}

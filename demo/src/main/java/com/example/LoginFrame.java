package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;  // Campo de texto para el nombre de usuario.
    private JPasswordField txtContraseña;  // Campo de texto para la contraseña.
    private JButton btnIniciarSesion;  // Botón para iniciar sesión.
    private DBmanager gestorDB;  // Gestor de la base de datos.

    // Constructor del marco de inicio de sesión.
    public LoginFrame(DBmanager gestorDB) {
        this.gestorDB = gestorDB;
        setTitle("Inicio de Sesión - Clínica Veterinaria");  // Título de la ventana.
        setSize(300, 150);  // Tamaño de la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamiento al cerrar la ventana.
        initUI();  // Inicializa los componentes de la interfaz.
        setLocationRelativeTo(null);  // Centra la ventana.
    }

    // Inicializa la interfaz de usuario.
    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));  // Diseño de la cuadrícula.

        panel.add(new JLabel("Usuario:"));  // Etiqueta para el nombre de usuario.
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));  // Etiqueta para la contraseña.
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this::iniciarSesion);  // Añade acción al botón.
        panel.add(btnIniciarSesion);

        add(panel);  // Añade el panel al marco.
    }

    // Método que se llama al hacer clic en el botón de inicio de sesión.
    private void iniciarSesion(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim();  // Obtiene el nombre de usuario.
        String contraseña = new String(txtContraseña.getPassword()).trim();  // Obtiene la contraseña.

        try {
            if (gestorDB.verificarCredenciales(usuario, contraseña)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!");
                new MainFrame(gestorDB).setVisible(true);  // Abre el marco principal.
                this.dispose();  // Cierra el marco de inicio de sesión.
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

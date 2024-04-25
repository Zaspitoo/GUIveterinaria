package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private JTextField txtUsuario;  // Campo de texto para el nombre de usuario.
    private JPasswordField txtContraseña;  // Campo de texto para la contraseña.
    private JTextField txtDniUsuario;  // Campo de texto para el DNI del usuario.   
    private JButton btnRegistrar;  // Botón para registrar al usuario.
    private DBmanager gestorDB;  // Gestor de la base de datos.

    // Constructor del marco de registro.
    public RegistroFrame(DBmanager gestorDB) {
        this.gestorDB = gestorDB;
        setTitle("Registro de Usuario - Clínica Veterinaria");  // Título de la ventana.
        setSize(300, 200);  // Tamaño de la ventana.
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

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this::registrarUsuario);  // Añade acción al botón.
        panel.add(btnRegistrar);

        add(panel);  // Añade el panel al marco.
    }

    // Método que se llama al hacer clic en el botón de registro.
    private void registrarUsuario(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim();  // Obtiene el nombre de usuario.
        String contraseña = new String(txtContraseña.getPassword()).trim();  // Obtiene la contraseña.
        String dniUsuario = txtDniUsuario.getText().trim();  // Obtiene el DNI del usuario.

        try {
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorDB.usuarioExiste(usuario)) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorDB.insertarUsuario(usuario, contraseña, dniUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
                this.dispose();  // Cierra el marco de registro.
                new LoginFrame(gestorDB).setVisible(true);  // Abre el marco de inicio de sesión.
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

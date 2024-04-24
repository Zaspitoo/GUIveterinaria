package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroFrame extends JFrame {
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private DBManager dbManager;

    public RegistroFrame(DBManager dbManager) {
        this.dbManager = dbManager;
        setTitle("Registro de Usuario - Clínica Veterinaria");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        txtNombre = new JTextField(15);
        txtEmail = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnRegistrar = new JButton("Registrar");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(btnRegistrar);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        add(panel);
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Implementar validaciones adicionales aquí (formato de email, complejidad de contraseña)
        
        if (dbManager.registrarUsuario(nombre, email, password)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");
            dispose();  // Cerrar la ventana de registro tras un registro exitoso
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario. Es posible que el email ya esté en uso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

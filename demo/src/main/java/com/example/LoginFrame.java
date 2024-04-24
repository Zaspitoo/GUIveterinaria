package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private DBManager dbManager;

    public LoginFrame(DBManager dbManager) {
        this.dbManager = dbManager;
        setTitle("Inicio de Sesión - Clínica Veterinaria");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Iniciar Sesión");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                if (usuario.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (dbManager.verificarCredenciales(usuario, password)) {
                    dispose();
                    new MainFrame(dbManager).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JTextField txtDniUsuario;
    private JTextField txtNombrePropietario;
    private JTextField txtTelefonoPropietario;
    private JTextField txtDireccionPropietario;
    private JButton btnRegistrar;
    private ConexionMySQL gestorDB;
    private PropietarioService propietarioService;

    public RegistroFrame(ConexionMySQL gestorDB, PropietarioService propietarioService) {
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        setTitle("Registro de Usuario - Clínica Veterinaria");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        panel.add(new JLabel("DNI:"));
        txtDniUsuario = new JTextField(15);
        panel.add(txtDniUsuario);

        panel.add(new JLabel("Nombre Propietario:"));
        txtNombrePropietario = new JTextField(15);
        panel.add(txtNombrePropietario);

        panel.add(new JLabel("Teléfono Propietario:"));
        txtTelefonoPropietario = new JTextField(15);
        panel.add(txtTelefonoPropietario);

        panel.add(new JLabel("Dirección Propietario:"));
        txtDireccionPropietario = new JTextField(15);
        panel.add(txtDireccionPropietario);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this::registrarUsuario);
        panel.add(btnRegistrar);

        add(panel);
    }

    private void registrarUsuario(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String dniUsuario = txtDniUsuario.getText().trim();
        String nombreProp = txtNombrePropietario.getText().trim();
        String telefonoProp = txtTelefonoPropietario.getText().trim();
        String direccionProp = txtDireccionPropietario.getText().trim();

        if (usuario.isEmpty() || contraseña.isEmpty() || dniUsuario.isEmpty() ||
            nombreProp.isEmpty() || telefonoProp.isEmpty() || direccionProp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!ConexionMySQL.usuarioExistes(usuario)) {
                Propietario propietario = new Propietario(dniUsuario, nombreProp, telefonoProp, direccionProp);
                if (propietarioService.registrarPropietario(propietario)) {
                    if (ConexionMySQL.insertarUsuario(usuario, contraseña, dniUsuario)) {
                        JOptionPane.showMessageDialog(this, "Usuario y propietario registrados correctamente.");
                        this.dispose();
                        new LoginFrame(gestorDB).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar propietario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de usuario o DNI ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionMySQL gestorDB = new ConexionMySQL();
            PropietarioDAO propietarioDao = new PropietarioDAO(ConexionMySQL.connect());
            PropietarioService propietarioService = new PropietarioService(propietarioDao);
            RegistroFrame registroFrame = new RegistroFrame(gestorDB, propietarioService);
            registroFrame.setVisible(true);
        });
    }
}

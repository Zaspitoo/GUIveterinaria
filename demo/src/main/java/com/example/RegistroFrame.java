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
    private DBmanager gestorDB;
    private PropietarioService propietarioService;

    

    public RegistroFrame(DBmanager gestorDB, PropietarioService propietarioService) {
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        setTitle("Registro de Usuario - Clínica Veterinaria");
        setSize(350, 300); // Adjusted size to fit new fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));  // Adjusted grid layout to 6 rows

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
        btnRegistrar.addActionListener(evento -> {
            try {
                registrarUsuario(evento);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException here
            }
        });
        panel.add(new JLabel());  // Spacer
        panel.add(btnRegistrar);

        add(panel);
    }

    private void registrarUsuario(ActionEvent evento) throws HeadlessException, SQLException {
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String IDTexto = txtDniUsuario.getText().trim();
        String nombreProp = txtNombrePropietario.getText().trim();
        String telefonoProp = txtTelefonoPropietario.getText().trim();
        String direccionProp = txtDireccionPropietario.getText().trim();
    
        if (usuario.isEmpty() || contraseña.isEmpty() || IDTexto.isEmpty() ||
            nombreProp.isEmpty() || telefonoProp.isEmpty() || direccionProp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            Integer ID = Integer.parseInt(IDTexto);
    
            if (gestorDB.usuarioExiste(usuario, ID)) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario o DNI ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            Propietario propietario = new Propietario(ID, nombreProp, telefonoProp, direccionProp);
            if (propietarioService.registrarPropietario(propietario)) {
                if (gestorDB.insertarUsuario(usuario, contraseña, ID)) {
                    JOptionPane.showMessageDialog(this, "Usuario y propietario registrados correctamente.");
                    this.dispose();
                    new LoginFrame(gestorDB).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar propietario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DBmanager gestorDB = null;
            PropietarioDAO propietarioDAO = new PropietarioDAO(gestorDB.getConnection());
            PropietarioService propietarioService = new PropietarioService(propietarioDAO);
            try {
                gestorDB = new DBmanager();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new RegistroFrame(gestorDB, propietarioService).setVisible(true);
        });
    }
}
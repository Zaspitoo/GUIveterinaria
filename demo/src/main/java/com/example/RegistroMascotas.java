package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegistroMascotas extends JFrame {
    private JTextField txtDniUsuario;
    private JTextField txtNombreMascota;
    private JTextField txtSexoMascota;
    private JButton btnRegistrarMascota;
    private ConexionMySQL gestorDB;

    public RegistroMascotas(ConexionMySQL gestorDB) {
        if (gestorDB == null) {
            throw new IllegalArgumentException("Database manager cannot be null");
        }
        this.gestorDB = gestorDB;
        setTitle("Registro de Mascota - Clínica Veterinaria");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("DNI del Usuario:"));
        txtDniUsuario = new JTextField();
        panel.add(txtDniUsuario);

        panel.add(new JLabel("Nombre de la Mascota:"));
        txtNombreMascota = new JTextField();
        panel.add(txtNombreMascota);

        panel.add(new JLabel("Sexo de la Mascota (M/F):"));
        txtSexoMascota = new JTextField();
        panel.add(txtSexoMascota);

        btnRegistrarMascota = new JButton("Registrar Mascota");
        btnRegistrarMascota.addActionListener(this::registrarMascota);
        add(panel, BorderLayout.CENTER);
        add(btnRegistrarMascota, BorderLayout.SOUTH);

        pack(); // Adjust the frame to fit the contents
    }

    private void registrarMascota(ActionEvent evento) {
        String dniUsuario = txtDniUsuario.getText().trim();
        String nombreMascota = txtNombreMascota.getText().trim();
        String sexoMascota = txtSexoMascota.getText().trim();

        try {
            if (ConexionMySQL.usuarioExistes(dniUsuario)) {
                if (gestorDB.insertarMascota(dniUsuario, nombreMascota, sexoMascota)) {
                    JOptionPane.showMessageDialog(this, "Mascota registrada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "DNI del usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al añadir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) throws SQLException {
        ConexionMySQL dbManager = new ConexionMySQL();
        RegistroMascotas frame = new RegistroMascotas(dbManager);
        frame.setVisible(true);
    }
}

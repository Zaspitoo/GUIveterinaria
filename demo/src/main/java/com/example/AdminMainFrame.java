package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMainFrame extends JFrame {
    private DBmanager gestorDB;
    private JTextArea txtAreaDatos;

    public AdminMainFrame(DBmanager gestorDB) {
        this.gestorDB = gestorDB;
        setTitle("Panel de Administración - Clínica Veterinaria");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton btnVerMascotas = new JButton("Ver Mascotas");
        btnVerMascotas.addActionListener(this::verMascotas);

        JButton btnVerCitas = new JButton("Ver Citas");
        btnVerCitas.addActionListener(this::verCitas);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(btnVerMascotas);
        buttonsPanel.add(btnVerCitas);

        txtAreaDatos = new JTextArea(10, 50);
        txtAreaDatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDatos);

        panel.add(buttonsPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        this.add(panel);
    }

    private void verMascotas(ActionEvent e) {
        try {
            ResultSet rs = gestorDB.consultarMascotas();
            llenarAreaTextoConResultadosMascotas(rs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos de mascotas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verCitas(ActionEvent e) {
        try {
            ResultSet rs = gestorDB.consultarCitas();
            llenarAreaTextoConResultadosCitas(rs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos de citas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarAreaTextoConResultadosMascotas(ResultSet rs) throws SQLException {
        String result = "Mascotas:\n";
        while (rs.next()) {
            result += "ID Mascota: " + rs.getInt("IdMascota") + ", " +
                      "Nombre: " + rs.getString("nombre") + ", " +
                      "Especie: " + rs.getString("Especie") + ", " +
                      "Raza: " + rs.getString("Raza") + ", " +
                      "Fecha Nacimiento: " + rs.getDate("FechaNacimiento") + ", " +
                      "Sexo: " + rs.getString("Sexo") + "\n";
        }
        txtAreaDatos.setText(result);
    }

    private void llenarAreaTextoConResultadosCitas(ResultSet rs) throws SQLException {
        String result = "Citas:\n";
        while (rs.next()) {
            result += "ID Cita: " + rs.getInt("IdCita") + ", " +
                      "Fecha y Hora: " + rs.getTimestamp("FechaHora") + ", " +
                      "Motivo: " + rs.getString("Motivo") + ", " +
                      "ID Mascota: " + rs.getInt("Mascota_IdMascota") + "\n";
        }
        txtAreaDatos.setText(result);
    }
}

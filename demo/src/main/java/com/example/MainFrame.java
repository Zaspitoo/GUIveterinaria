package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
    private DBmanager dbManager;
    private JTextArea textArea;
    private JTextField txtFechaCita, txtMotivo, txtMascotaId;



    public MainFrame(DBmanager dbManager) {
        this.dbManager = dbManager;
        setTitle("Sistema de Gestión - Clínica Veterinaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Archivo");
        menuBar.add(menu);

        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menu.add(menuItemSalir);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        add(panel);

        panel.add(new JLabel("Fecha Cita (yyyy-mm-dd):"));
        txtFechaCita = new JTextField(15);
        panel.add(txtFechaCita);

        panel.add(new JLabel("Motivo:"));
        txtMotivo = new JTextField(15);
        panel.add(txtMotivo);

        panel.add(new JLabel("ID Mascota:"));
        txtMascotaId = new JTextField(15);
        panel.add(txtMascotaId);

        JButton btnAdd = new JButton("Añadir Datos");
        JButton btnView = new JButton("Ver Datos");
        JButton btnAddCita = new JButton("Agregar Cita");

        btnAdd.addActionListener(this::addData);
        btnView.addActionListener(this::fetchData);
        btnAddCita.addActionListener(this::agregarCita);

        panel.add(btnAdd);
        panel.add(btnView);
        panel.add(btnAddCita);

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea));
    }

    // Ajuste de métodos para aceptar ActionEvent
    private void addData(ActionEvent e) {
        // Lógica para añadir datos
    }

    private void fetchData(ActionEvent e) {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT nombre, especie FROM mascotas");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Nombre: ").append(rs.getString("nombre")).append(", Especie: ").append(rs.getString("especie")).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCita(ActionEvent e) {
        try {
            String fechaHora = txtFechaCita.getText().trim();
            String motivo = txtMotivo.getText().trim();
            int mascotaId = Integer.parseInt(txtMascotaId.getText().trim());

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date test = dateFormatter.parse(fechaHora); // Valida la fecha.

            dbManager.agregarCita(fechaHora, motivo, mascotaId);
            JOptionPane.showMessageDialog(this, "Cita agregada correctamente.");
        } catch (NumberFormatException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, verifique que todos los campos están correctamente llenados y que la fecha es válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DBmanager dbManager = null;
            try {
                dbManager = new DBmanager();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new MainFrame(dbManager).setVisible(true);
        });
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame {
    private DBmanager dbManager;
    private JTextArea textArea;

    public MainFrame(DBmanager dbManager) {
        this.dbManager = dbManager;
        setTitle("Sistema de Gestión - Clínica Veterinaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);  // Centrar ventana
    }

    private void initUI() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menu.add(menuItemSalir);
        menuBar.add(menu);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnAdd = new JButton("Añadir Datos");
        JButton btnView = new JButton("Ver Datos");
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });

        panel.add(btnAdd);
        panel.add(btnView);
        panel.add(new JScrollPane(textArea));
        add(panel);

        setJMenuBar(menuBar);
    }

    private void addData() {
        try {
            dbManager.executeInsert("INSERT INTO Mascotas (nombre, especie) VALUES ('Bobby', 'Perro')");
            JOptionPane.showMessageDialog(this, "Datos añadidos correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al añadir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchData() {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT nombre, especie FROM Mascotas");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Nombre: ").append(rs.getString("nombre")).append(", Especie: ").append(rs.getString("especie")).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Aquí deberías crear una instancia de DBManager y conectarla a tu base de datos.
        // DBManager dbManager = new DBManager();
        // new MainFrame(dbManager).setVisible(true);
    }
}

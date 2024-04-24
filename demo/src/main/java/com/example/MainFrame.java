package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame {
    private DBManager dbManager;
    private JTextArea textArea;

    public MainFrame(DBManager dbManager) {
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
            ResultSet rs = dbManager.executeQuery("SELECT * FROM Mascotas");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("nombre")).append(" - ").append(rs.getString("especie")).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        String host = "sql.freedb.tech";
        int port = 3306; 
        String databaseName = "freedb_veterinariaclinica";
        String user = "freedb_andav";
        String password = "6q4x3^dFMS$@JAV";
      
        
        try {
            DBManager dbManager = new DBManager(host, port, databaseName, user, password);
            new MainFrame(dbManager).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
}

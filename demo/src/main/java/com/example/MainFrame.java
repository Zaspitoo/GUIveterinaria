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
        
        String usuario = "freedb_andav";
        String pass = "6q4x3^dFMS$@JAV";
        String bd = "freedb_veterinariaclinica";
        String host = "sql.freebd.tech";
    
        
        ConexionMySQL conexion = new ConexionMySQL(usuario, pass, bd, host);
    
        try {
            // Intentar conectar a la base de datos
            conexion.conectar();
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            // Manejar la excepción si la conexión falla
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    
      
    }
        
       
    }


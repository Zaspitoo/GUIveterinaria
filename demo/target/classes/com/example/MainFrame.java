package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// Clase MainFrame que extiende de JFrame para crear una ventana de aplicación.
public class MainFrame extends JFrame {
    private DBmanager dbManager; // Administrador de la base de datos.
    private JTextArea textArea; // Área de texto para mostrar datos.

    // Constructor de la clase que configura la ventana y sus componentes.
    public MainFrame(DBmanager dbManager) {
        this.dbManager = dbManager; // Asignación del administrador de la base de datos.
        setTitle("Sistema de Gestión - Clínica Veterinaria"); // Título de la ventana.
        setSize(800, 600); // Tamaño de la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Operación de cierre.
        initUI(); // Inicializa la interfaz de usuario.
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.
    }

    // Método para inicializar la interfaz de usuario.
    private void initUI() {
        JMenuBar menuBar = new JMenuBar(); // Barra de menú.
        JMenu menu = new JMenu("Archivo"); // Menú Archivo.
        JMenuItem menuItemSalir = new JMenuItem("Salir"); // Opción Salir.
        menuItemSalir.addActionListener(e -> System.exit(0)); // Acción para salir.
        menu.add(menuItemSalir); // Agrega el ítem al menú.
        menuBar.add(menu); // Agrega el menú a la barra.

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10)); // Panel para botones.
        JButton btnAdd = new JButton("Añadir Datos"); // Botón para añadir datos.
        JButton btnView = new JButton("Ver Datos"); // Botón para ver datos.
        textArea = new JTextArea(10, 40); // Área de texto.
        textArea.setEditable(false); // No editable.

        // Listener para el botón de añadir datos.
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addData(); // Llama al método addData.
            }
        });

        // Listener para el botón de ver datos.
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchData(); // Llama al método fetchData.
            }
        });

        // Agrega componentes al panel y a la ventana.
        panel.add(btnAdd);
        panel.add(btnView);
        panel.add(new JScrollPane(textArea));
        add(panel);
        setJMenuBar(menuBar); // Establece la barra de menú en la ventana.
    }

    // Método para añadir datos a la base de datos.
    private void addData() {
        try {
            dbManager.executeInsert("INSERT INTO Mascotas (nombre, especie) VALUES ('Bobby', 'Perro')");
            JOptionPane.showMessageDialog(this, "Datos añadidos correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al añadir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener datos de la base de datos y mostrarlos.
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
        // DBManager dbManager = new DBManager();
        // new MainFrame(dbManager).setVisible(true);
    }
}

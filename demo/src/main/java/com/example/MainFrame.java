// Paquete que organiza las clases relacionadas con la aplicación principal de la clínica veterinaria.
package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


// Clase principal que hereda de JFrame, proporcionando la interfaz gráfica principal de la aplicación.
public class MainFrame extends JFrame {
    private JTextField txtMotivo, txtFechaCita, txtUsername;
    private JButton btnRegistrarMascota, btnVerDatos, btnAgregarCita;
    private JTextArea textArea;  // Área de texto para mostrar datos recuperados
    private ConexionMySQL gestorDB;

    public MainFrame() {
        setTitle("Sistema de Gestión - Clínica Veterinaria"); // Título de la ventana
        setSize(800, 600); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamiento al cerrar
        initUI(); // Inicialización de la interfaz de usuario
        setLocationRelativeTo(null); // Centrar ventana
        gestorDB = new ConexionMySQL();
    }

    private void initUI() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menu.add(menuItemSalir);
        menuBar.add(menu);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        getContentPane().add(panel, BorderLayout.CENTER); // Agregar al centro

        panel.add(new JLabel("Motivo:"));
        txtMotivo = new JTextField(15);
        panel.add(txtMotivo);
        
        panel.add(new JLabel("Fecha Cita (yyyy-mm-dd):"));
        txtFechaCita = new JTextField(15);
        panel.add(txtFechaCita);
        
        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField(15);
        panel.add(txtUsername);

        btnRegistrarMascota = new JButton("Registrar Mascota");
        btnRegistrarMascota.addActionListener(this::showRegistrarMascota);
        panel.add(btnRegistrarMascota);

        btnVerDatos = new JButton("Ver Datos");
        btnAgregarCita = new JButton("Agregar Cita");
        btnVerDatos.addActionListener(this::fetchData);
        btnAgregarCita.addActionListener(this::agregarCita);
        panel.add(btnVerDatos);
        panel.add(btnAgregarCita);

        textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.SOUTH); // Agregar scrollPane abajo
    }

    private void fetchData(ActionEvent e) {
        StringBuilder sb = new StringBuilder("Citas Registradas:\n");
        try (Connection conn = ConexionMySQL.connect();
             PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM citas");
             ResultSet rs1 = stmt1.executeQuery()) {
            while (rs1.next()) {
                sb.append("Fecha: ").append(rs1.getString("fecha_hora")).append(", Motivo: ").append(rs1.getString("motivo"))
                  .append(", Username: ").append(rs1.getString("username")).append("\n");
            }
            sb.append("\nMascotas Registradas:\n");
            PreparedStatement stmt2 = conn.prepareStatement("SELECT nombre FROM mascotas");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                sb.append("Nombre: ").append(rs2.getString("nombre")).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarCita(ActionEvent e) {
        String fechaHora = txtFechaCita.getText().trim();
        String motivo = txtMotivo.getText().trim();
        String username = txtUsername.getText().trim();
    
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormatter.setLenient(false);
            dateFormatter.parse(fechaHora); // Valida la fecha y hora.
    
            if (!ConexionMySQL.usuarioExiste(username)) {
                JOptionPane.showMessageDialog(this, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try (Connection conn = ConexionMySQL.connect()) {
                ConexionMySQL.agregarCita(fechaHora, motivo, username); // Supone que este método ahora acepta Connection como parámetro
                JOptionPane.showMessageDialog(this, "Cita agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Fecha no válida. Use el formato correcto (yyyy-MM-dd HH:mm).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegistrarMascota(ActionEvent e) {
        RegistroMascotas registroMascotas = new RegistroMascotas(gestorDB);
        registroMascotas.setVisible(true);
    }
    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

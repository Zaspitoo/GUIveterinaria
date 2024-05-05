// Paquete que organiza las clases relacionadas con la aplicación principal.
package com.example.MAIN;

import javax.swing.*;

import com.example.SERVICIOS.PropietarioService;
import com.example.UI.AdminLoginFrame;
import com.example.UI.BackgroundPanel;
import com.example.UI.LoginFrame;
import com.example.UI.RegistroFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.sql.SQLException;
import com.example.BASEDEDATOS.ConexionMySQL;

// Clase Main que controla la ejecución y transición entre ventanas en la aplicación.
public class Main {

    private static JFrame mainFrame;
    private static LoginFrame loginFrame;
    private static RegistroFrame registroFrame;
    private static ConexionMySQL gestorDB;
    private static PropietarioService propietarioService;

    // Método para mostrar la ventana de login.
    private static void showLoginWindow() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame(gestorDB);
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        loginFrame.setVisible(true);
    }

    // Método para mostrar la ventana de registro.
    private static void showRegistroWindow() {
        if (registroFrame == null) {
            registroFrame = new RegistroFrame(gestorDB, propietarioService);
            registroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        registroFrame.setVisible(true);
    }

    // Método para mostrar la ventana de administración.
    private static void showAdminWindow() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        if (loginFrame != null) {
            loginFrame.dispose();
        }
        if (registroFrame != null) {
            registroFrame.dispose();
        }
        try {
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
            adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adminLoginFrame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::prepareGUI);
    }

    // Método para preparar y mostrar la GUI inicial.
    public static void prepareGUI() {
        mainFrame = new JFrame("Inicio de aplicación");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(1280, 720);

        // Panel de fondo personalizado que carga una imagen.
        BackgroundPanel backgroundPanel = new BackgroundPanel("/com/example/gui2.jpg");
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Creación y configuración de botones con iconos.
        JButton btnLogin = new JButton(loadImageIcon("/com/example/login1.jpg"));
        JButton btnRegister = new JButton(loadImageIcon("/com/example/register1.jpg"));
        JButton btnAdmin = new JButton(loadImageIcon("/com/example/logo.jpg"));

        Dimension buttonSize = new Dimension(150, 150);
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnAdmin.setPreferredSize(buttonSize);

        // Agregar acciones a los botones para mostrar diferentes ventanas.
        btnLogin.addActionListener(e -> showLoginWindow());
        btnRegister.addActionListener(e -> showRegistroWindow());
        btnAdmin.addActionListener(e -> showAdminWindow());

        // Agregar los botones al panel de fondo.
        backgroundPanel.add(btnLogin);
        backgroundPanel.add(btnRegister);
        backgroundPanel.add(btnAdmin);

        mainFrame.add(backgroundPanel, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // Método para cargar iconos de imágenes de forma segura.
    private static ImageIcon loadImageIcon(String path) {
        URL url = Main.class.getResource(path);
        if (url == null) {
            System.err.println("El archivo no se encontró en el classpath: " + path);
            return null;
        }
        try {
            BufferedImage img = ImageIO.read(url);
            Image scaledImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + path);
            e.printStackTrace();
            return null;
        }
    }
}

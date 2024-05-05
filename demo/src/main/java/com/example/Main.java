package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static JFrame mainFrame;
    private static LoginFrame loginFrame;
    private static RegistroFrame registroFrame;
    private static ConexionMySQL gestorDB;
    private static PropietarioService propietarioService;

    private static void showLoginWindow() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame(gestorDB);
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio de operación de cierre
        }
        loginFrame.setVisible(true);
    }

    private static void showRegistroWindow() {
        if (registroFrame == null) {
            registroFrame = new RegistroFrame(gestorDB, propietarioService);
            registroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio de operación de cierre
        }
        registroFrame.setVisible(true);
    }

    private static void showAdminWindow() {
        if (mainFrame != null) {
            mainFrame.dispose(); // Cierra la ventana principal
        }
        if (loginFrame != null) {
            loginFrame.dispose();
        }
        if (registroFrame != null) {
            registroFrame.dispose();
        }
        try {
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
            adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio de operación de cierre
            adminLoginFrame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::prepareGUI);
    }

    public static void prepareGUI() {
        mainFrame = new JFrame("Inicio de aplicación");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio de operación de cierre
        mainFrame.setLayout(new BorderLayout()); // Usamos BorderLayout para que el BackgroundPanel se expanda
        mainFrame.setSize(1280, 720);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/com/example/gui2.jpg");
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnLogin = new JButton(loadImageIcon("/com/example/login1.jpg"));
        JButton btnRegister = new JButton(loadImageIcon("/com/example/register1.jpg"));
        JButton btnAdmin = new JButton(loadImageIcon("/com/example/logo.jpg"));

        Dimension buttonSize = new Dimension(150, 150); // Tamaño deseado para los botones
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnAdmin.setPreferredSize(buttonSize);

        btnLogin.addActionListener(e -> showLoginWindow());
        btnRegister.addActionListener(e -> showRegistroWindow());
        btnAdmin.addActionListener(e -> showAdminWindow());

        backgroundPanel.add(btnLogin);
        backgroundPanel.add(btnRegister);
        backgroundPanel.add(btnAdmin);

        mainFrame.add(backgroundPanel, BorderLayout.CENTER); // Añadimos el BackgroundPanel al centro

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static ImageIcon loadImageIcon(String path) {
        URL url = Main.class.getResource(path);
        if (url == null) {
            System.err.println("El archivo no se encontró en el classpath: " + path);
            return null;
        }
        try {
            BufferedImage img = ImageIO.read(url);
            Image scaledImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Tamaño deseado para las imágenes
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + path);
            e.printStackTrace();
            return null;
        }
    }
}

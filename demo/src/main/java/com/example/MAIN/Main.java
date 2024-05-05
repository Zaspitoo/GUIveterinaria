package com.example.MAIN;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import com.example.SERVICIOS.PropietarioService;
import com.example.UI.AdminLoginFrame;
import com.example.UI.BackgroundPanel;
import com.example.UI.LoginFrame;
import com.example.UI.RegistroFrame;
import com.example.BASEDEDATOS.ConexionMySQL;
import com.example.DAO.PropietarioDAO;

public class Main {

    private static JFrame mainFrame; // Marco principal para la aplicación
    private static LoginFrame loginFrame; // Marco para el login
    private static RegistroFrame registroFrame; // Marco para el registro
    private static ConexionMySQL gestorDB; // Gestor de la base de datos
    private static PropietarioService propietarioService; // Servicio para gestionar propietarios

    // Método para inicializar los servicios necesarios para la aplicación.
    private static void initializeServices() {
        gestorDB = new ConexionMySQL(); // Asumimos que este método retorna una instancia correctamente inicializada
        PropietarioDAO propietarioDao = new PropietarioDAO(ConexionMySQL.connect()); // DAO para propietarios
        propietarioService = new PropietarioService(propietarioDao); // Inicialización del servicio de propietarios
    }

    // Método para mostrar la ventana de login.
    private static void showLoginWindow() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame(gestorDB);
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Establece la operación de cierre
        }
        loginFrame.setVisible(true); // Hace visible el marco de login
    }

    // Método para mostrar la ventana de registro.
    private static void showRegistroWindow() {
        if (registroFrame == null) {
            registroFrame = new RegistroFrame(gestorDB, propietarioService);
            registroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Establece la operación de cierre
        }
        registroFrame.setVisible(true); // Hace visible el marco de registro
    }

    // Método para mostrar la ventana de administración.
    private static void showAdminWindow() {
        disposeAllFrames(); // Limpia todos los marcos abiertos
        try {
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
            adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adminLoginFrame.setVisible(true); // Muestra la ventana de administración
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para cerrar todos los marcos abiertos y liberar recursos.
    private static void disposeAllFrames() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        if (loginFrame != null) {
            loginFrame.dispose();
        }
        if (registroFrame != null) {
            registroFrame.dispose();
        }
    }

    // Método principal para ejecutar la aplicación.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            initializeServices(); // Inicializa los servicios
            prepareGUI(); // Prepara y muestra la interfaz gráfica
        });
    }

    // Método para preparar y mostrar la GUI inicial.
    public static void prepareGUI() {
        mainFrame = new JFrame("Inicio de aplicación"); // Título de la ventana principal
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(1280, 720); // Dimensiones de la ventana principal

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

        mainFrame.add(backgroundPanel, BorderLayout.CENTER); // Agrega el panel al marco
        mainFrame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        mainFrame.setVisible(true); // Hace visible el marco principal
    }

    // Método para cargar iconos de imágenes de forma segura.
    private static ImageIcon loadImageIcon(String path) {
        URL url = Main.class.getResource(path); // Obtiene la URL del recurso
        if (url == null) {
            System.err.println("El archivo no se encontró en el classpath: " + path);
            return null;
        }
        try {
            BufferedImage img = ImageIO.read(url); // Lee la imagen
            Image scaledImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Escala la imagen
            return new ImageIcon(scaledImage); // Retorna un icono de la imagen
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + path);
            e.printStackTrace();
            return null;
        }
    }
}

package com.example.UI;

import javax.swing.*;

import com.example.BASEDEDATOS.ConexionMySQL;
import com.example.DAO.CitaDAO;
import com.example.DAO.PropietarioDAO;
import com.example.SERVICIOS.CitaService;
import com.example.SERVICIOS.PropietarioService;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminLoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ConexionMySQL gestorDB; // Referencia al gestor de conexión de la base de datos

    // Constructor para la ventana de inicio de sesión del administrador
    public AdminLoginFrame() throws SQLException {
        gestorDB = new ConexionMySQL(); // Inicializar el gestor de conexión de la base de datos

        setTitle("Administración de la Clínica Veterinaria");
        setSize(300, 125);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    // Método para inicializar la interfaz de usuario
    private void initUI() {
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(this::adminLogin);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(btnLogin);

        add(panel);
    }

    // Método para manejar el evento de inicio de sesión del administrador
    private void adminLogin(ActionEvent e) {
        String adminUsername = "admin";
        String adminPassword = "test";

        String enteredUsername = txtUsername.getText();
        String enteredPassword = new String(txtPassword.getPassword());

        // Verificar las credenciales ingresadas
        if (adminUsername.equals(enteredUsername) && adminPassword.equals(enteredPassword)) {
            launchAdminMainFrame(gestorDB);
            this.dispose(); // Cerrar la ventana de inicio de sesión en caso de inicio de sesión exitoso.
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para iniciar la ventana principal del administrador
    private void launchAdminMainFrame(ConexionMySQL gestorDB) {
        // Este método inicializará AdminMainFrame con los servicios requeridos
        Connection dbConnection = ConexionMySQL.connect(); // Este método debe existir y devolver una conexión válida

        PropietarioDAO propietarioDao = new PropietarioDAO(dbConnection);
        CitaDAO citaDao = new CitaDAO(dbConnection);

        PropietarioService propietarioService = new PropietarioService((PropietarioDAO) propietarioDao);
        CitaService citaService = new CitaService(citaDao);

        AdminMainFrame adminMainFrame = new AdminMainFrame(gestorDB, propietarioService, citaService);
        adminMainFrame.setVisible(true);
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
                adminLoginFrame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}

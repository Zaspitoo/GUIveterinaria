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

public class AdminMainFrame extends JFrame {
    
    private AdminMainFrameUI ui;
    private AdminMainFrameBusinessLogic businessLogic;

    // Constructor para la clase principal de la interfaz de usuario del administrador
    public AdminMainFrame(AdminMainFrameBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        setTitle("Administración"); // Configura el título de la ventana.
        setSize(800, 600); // Establece el tamaño inicial de la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establece la operación de cierre.
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.

        ui = new AdminMainFrameUI(this, businessLogic.getGestorDB(), businessLogic.getPropietarioService(), businessLogic.getCitaService());
        initUI(); // Llama a initUI para configurar los componentes de la UI.
    }

    // Constructor alternativo para la clase principal de la interfaz de usuario del administrador
    public AdminMainFrame(ConexionMySQL gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this(new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService));
    }

    // Método para inicializar la interfaz de usuario
    private void initUI() {
        ui.initUI(); // Delega la configuración de la interfaz de usuario a la clase AdminMainFrameUI.
    }

    // Métodos para delegar acciones a la lógica de negocio
    public void agregarPropietario(ActionEvent e) {
        businessLogic.agregarPropietario(e);
    }

    public void editarPropietario(ActionEvent e) {
        try {
            businessLogic.editarPropietario(e);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarPropietario(ActionEvent e) {
        businessLogic.eliminarPropietario(e);
    }

    public void verPropietarios(ActionEvent e) {
        businessLogic.verPropietarios(e);
    }

    public void agregarCitas(ActionEvent e) {
        businessLogic.agregarCitas(e);
    }

    public void editarCita(ActionEvent e) {
        businessLogic.editarCita(e);
    }

    public void eliminarCita(ActionEvent e) {
        businessLogic.eliminarCita(e);
    }

    public void verCitas(ActionEvent e) {
        businessLogic.verCitas(e);
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionMySQL gestorDB = new ConexionMySQL();
            try (Connection dbConnection = ConexionMySQL.connect()) {
                PropietarioDAO propietarioDao = new PropietarioDAO(dbConnection);
                CitaDAO citaDao = new CitaDAO(dbConnection);
                PropietarioService propietarioService = new PropietarioService(propietarioDao);
                CitaService citaService = new CitaService(citaDao);
                AdminMainFrame adminMainFrame = new AdminMainFrame(gestorDB, propietarioService, citaService);
                adminMainFrame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}

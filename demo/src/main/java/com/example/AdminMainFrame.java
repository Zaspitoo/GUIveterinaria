package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminMainFrame extends JFrame {
    
    private AdminMainFrameUI ui;
    private AdminMainFrameBusinessLogic businessLogic;

    public AdminMainFrame(AdminMainFrameBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        ui = new AdminMainFrameUI(this, businessLogic.getGestorDB(), businessLogic.getPropietarioService(), businessLogic.getCitaService());
        initUI();
    }

    public AdminMainFrame(ConexionMySQL gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this(new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService));
    }

    private void initUI() {
        ui.initUI();
    }

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

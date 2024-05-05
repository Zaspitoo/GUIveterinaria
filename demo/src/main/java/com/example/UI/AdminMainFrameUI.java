package com.example.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import com.example.BASEDEDATOS.ConexionMySQL;
import com.example.MAIN.Main;
import com.example.SERVICIOS.CitaService;
import com.example.SERVICIOS.PropietarioService;

public class AdminMainFrameUI {
    private JFrame frame;
    private JButton backButton;
    private ConexionMySQL gestorDB;
    private JTextArea txtAreaDatos;
    private PropietarioService propietarioService;
    private CitaService citaService;

    public AdminMainFrameUI(JFrame frame, ConexionMySQL gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this.frame = frame;
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        this.citaService = citaService;
        initUI();
    }

    public void initUI() {
        frame.getContentPane().removeAll(); // Limpiar el frame para asegurar que no hay componentes previos

        BackgroundPanel backgroundPanel = new BackgroundPanel("/com/example/gui2.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundPanel);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20); // Ajustar los márgenes entre botones

        addButton(buttonsPanel, "Agregar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarPropietario(e)), gbc);
        addButton(buttonsPanel, "Editar Propietario", e -> addAction(() -> {
            try {
                new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarPropietario(e);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }), gbc);
        addButton(buttonsPanel, "Eliminar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarPropietario(e)), gbc);
        addButton(buttonsPanel, "Ver Propietarios", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verPropietarios(e)), gbc);
        addButton(buttonsPanel, "Agregar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarCitas(e)), gbc);
        addButton(buttonsPanel, "Editar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarCita(e)), gbc);
        addButton(buttonsPanel, "Eliminar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarCita(e)), gbc);
        addButton(buttonsPanel, "Ver Citas", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verCitas(e)), gbc);

        backButton = new JButton("Volver");
        backButton.addActionListener(e -> returnToMainMenu());
        buttonsPanel.add(backButton, gbc);

        backgroundPanel.add(buttonsPanel, BorderLayout.NORTH);

        txtAreaDatos = new JTextArea(15, 50);
        txtAreaDatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDatos);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void addButton(JPanel panel, String title, ActionListener actionListener, GridBagConstraints gbc) {
        JButton button = new JButton(title);
        button.addActionListener(actionListener);
        panel.add(button, gbc);
    }

    private void addAction(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToMainMenu() {
        frame.dispose();
        Main.prepareGUI();
    }
}

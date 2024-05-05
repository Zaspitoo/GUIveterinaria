package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminMainFrameUI {
    private JFrame frame;
    private JPanel panel;
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
        panel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Adjusted to accommodate the back button

        addButton(buttonsPanel, "Agregar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarPropietario(e)));
        addButton(buttonsPanel, "Editar Propietario", e -> addAction(() -> {
            try {
                new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarPropietario(e);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }));
        addButton(buttonsPanel, "Eliminar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarPropietario(e)));
        addButton(buttonsPanel, "Ver Propietarios", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verPropietarios(e)));
        addButton(buttonsPanel, "Agregar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarCitas(e)));
        addButton(buttonsPanel, "Editar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarCita(e)));
        addButton(buttonsPanel, "Eliminar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarCita(e)));
        addButton(buttonsPanel, "Ver Citas", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verCitas(e)));

        backButton = new JButton("Volver");
        backButton.addActionListener(e -> returnToMainMenu());
        buttonsPanel.add(backButton);

        txtAreaDatos = new JTextArea(15, 50);
        txtAreaDatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDatos);

        panel.add(buttonsPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Ensure all components fit within the frame
        frame.setVisible(true); // Display the frame
    }

    private void addButton(JPanel panel, String title, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.addActionListener(actionListener);
        panel.add(button);
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
        System.out.println("Returning to Main Menu...");
    }

    // Main method for testing the UI
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Main Frame");
        ConexionMySQL db = new ConexionMySQL(); // Ensure this constructor is properly defined
        PropietarioService propService = new PropietarioService(null); // Ensure this constructor is properly defined
        CitaService citaService = new CitaService(null); // Ensure this constructor is properly defined
        new AdminMainFrameUI(frame, db, propService, citaService);
    }
}

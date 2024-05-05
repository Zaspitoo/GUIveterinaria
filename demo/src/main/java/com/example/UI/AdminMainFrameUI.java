package com.example.UI;

import javax.swing.*;

import com.example.BASEDEDATOS.ConexionMySQL;
import com.example.MAIN.Main;
import com.example.SERVICIOS.CitaService;
import com.example.SERVICIOS.PropietarioService;
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

    // Constructor para la interfaz de usuario principal del administrador
    public AdminMainFrameUI(JFrame frame, ConexionMySQL gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this.frame = frame;
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        this.citaService = citaService;
        initUI(); // Inicializa la interfaz de usuario
    }

    // Método para inicializar la interfaz de usuario
    public void initUI() {
        panel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Ajustado para acomodar el botón de regreso

        // Agregar botones y sus correspondientes acciones
        addButton(buttonsPanel, "Agregar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarPropietario(e)));
        addButton(buttonsPanel, "Editar Propietario", e -> addAction(() -> {
            try {
                new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarPropietario(e);
            } catch (SQLException e1) {
                e1.printStackTrace(); // Imprime el rastreo de la pila si hay una excepción SQL
            }
        }));
        addButton(buttonsPanel, "Eliminar Propietario", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarPropietario(e)));
        addButton(buttonsPanel, "Ver Propietarios", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verPropietarios(e)));
        addButton(buttonsPanel, "Agregar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).agregarCitas(e)));
        addButton(buttonsPanel, "Editar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).editarCita(e)));
        addButton(buttonsPanel, "Eliminar Cita", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).eliminarCita(e)));
        addButton(buttonsPanel, "Ver Citas", e -> addAction(() -> new AdminMainFrameBusinessLogic(gestorDB, propietarioService, citaService).verCitas(e)));

        backButton = new JButton("Volver");
        backButton.addActionListener(e -> returnToMainMenu()); // Agrega la acción de volver al menú principal al botón
        buttonsPanel.add(backButton);

        txtAreaDatos = new JTextArea(15, 50);
        txtAreaDatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDatos);

        panel.add(buttonsPanel, BorderLayout.NORTH); // Agrega el panel de botones en la parte superior
        panel.add(scrollPane, BorderLayout.CENTER); // Agrega el área de texto en el centro

        frame.add(panel); // Agrega el panel principal al marco
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establece la operación de cierre
        frame.pack(); // Asegura que todos los componentes se ajusten dentro del marco
        frame.setVisible(true); // Hace visible el marco
    }

    // Método para agregar un botón con su acción correspondiente
    private void addButton(JPanel panel, String title, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.addActionListener(actionListener);
        panel.add(button);
    }

    // Método para ejecutar una acción, maneja las excepciones generales y muestra mensajes de error si es necesario
    private void addAction(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para volver al menú principal
    private void returnToMainMenu() {
        frame.dispose(); // Cierra el marco actual
        Main.prepareGUI(); // Vuelve al menú principal
        System.out.println("Returning to Main Menu...");
    }

    // Método principal para probar la interfaz de usuario
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Main Frame");
        ConexionMySQL db = new ConexionMySQL(); // Asegúrate de que este constructor esté definido correctamente
        PropietarioService propService = new PropietarioService(null); // Asegúrate de que este constructor esté definido correctamente
        CitaService citaService = new CitaService(null); // Asegúrate de que este constructor esté definido correctamente
        new AdminMainFrameUI(frame, db, propService, citaService); // Crea una instancia de la interfaz de usuario
    }
}

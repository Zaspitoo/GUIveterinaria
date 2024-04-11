package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicaVeterinariaGUI extends JFrame {
    private JTextField nombrePacienteTextField;
    private JButton guardarButton;
    private JTextArea informacionTextArea;

    public ClinicaVeterinariaGUI() {
        setTitle("Sistema de Gestión Veterinaria");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentesGUI();
    }

    private void inicializarComponentesGUI() {
        // Contenedor Principal
        setLayout(new FlowLayout());

        // Campo de Texto
        nombrePacienteTextField = new JTextField(20);
        add(nombrePacienteTextField);

        // Botón para Guardar
        guardarButton = new JButton("Guardar");
        add(guardarButton);

        // Área de Texto para Mostrar Información
        informacionTextArea = new JTextArea(10, 30);
        add(new JScrollPane(informacionTextArea));

        // Evento del Botón
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se manejaría la lógica para guardar la información y actualizar la vista.
                informacionTextArea.append(nombrePacienteTextField.getText() + "\n");
                nombrePacienteTextField.setText(""); // Limpiar el campo de texto después de guardar.
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClinicaVeterinariaGUI().setVisible(true);
            }
        });
    }
}

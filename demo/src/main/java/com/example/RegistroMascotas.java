package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegistroMascotas extends JFrame {
    private JTextField txtDniUsuario;  // Campo de texto para el DNI del usuario.
    private JTextField txtNombreMascota;  // Campo de texto para el nombre de la mascota.
    private JTextField txtSexoMascota;  // Campo de texto para el sexo de la mascota.
    private JButton btnRegistrarMascota;  // Botón para registrar a la mascota.
    private DBmanager gestorDB;  // Gestor de la base de datos.

    // Constructor del marco de registro de mascotas.
    public RegistroMascotas(DBmanager gestorDB) {
        this.gestorDB = gestorDB;
        setTitle("Registro de Mascota - Clínica Veterinaria");  // Título de la ventana.
        setSize(400, 200);  // Tamaño de la ventana.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Comportamiento al cerrar la ventana.
        initUI();  // Inicializa los componentes de la interfaz.
        setLocationRelativeTo(null);  // Centra la ventana.
    }

    // Inicializa la interfaz de usuario.
    private void initUI() {
        setLayout(new BorderLayout()); // Usa BorderLayout como gestor de diseño.

        JPanel panel = new JPanel(new GridLayout(0, 2)); // Panel con GridLayout.

        // Añade etiquetas y campos de texto al panel
        panel.add(new JLabel("DNI del Usuario:"));
        txtDniUsuario = new JTextField();
        panel.add(txtDniUsuario);

        panel.add(new JLabel("Nombre de la Mascota:"));
        txtNombreMascota = new JTextField();
        panel.add(txtNombreMascota);

        panel.add(new JLabel("Sexo de la Mascota (M/F):"));
        txtSexoMascota = new JTextField();
        panel.add(txtSexoMascota);

        // Añade el botón de registro al final del formulario.
        btnRegistrarMascota = new JButton("Registrar Mascota");
        btnRegistrarMascota.addActionListener(this::registrarMascota);

        add(panel, BorderLayout.CENTER); // Añade el panel de entrada al centro.
        add(btnRegistrarMascota, BorderLayout.SOUTH); // Añade el botón de registro en la parte inferior.

        pack(); // Ajusta el marco al contenido.
    }

    // Método que se llama al hacer clic en el botón de registro de mascota.
    private void registrarMascota(ActionEvent evento) {
        String dniUsuario = txtDniUsuario.getText().trim();
        String nombreMascota = txtNombreMascota.getText().trim();
        String sexoMascota = txtSexoMascota.getText().trim();
    
        try {
            if (gestorDB.usuarioExiste(dniUsuario, sexoMascota)) { // Asegúrate de que este método existe y está implementado correctamente.
                if (gestorDB.insertarMascota(dniUsuario, nombreMascota, sexoMascota)) {
                    JOptionPane.showMessageDialog(this, "Mascota registrada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "DNI del usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al añadir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    // Método main para probar el formulario. Se debe quitar en la aplicación final.
}

// Paquete que organiza las clases relacionadas con el registro de mascotas.
package com.example.SERVICIOS;

// Importación de librerías necesarias para el funcionamiento de la interfaz gráfica y la conexión con la base de datos.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import com.example.BASEDEDATOS.ConexionMySQL;

// Clase RegistroMascotas que hereda de JFrame para crear una ventana de aplicación.
public class RegistroMascotas extends JFrame {
    // Definición de componentes de la interfaz gráfica para capturar información del usuario y la mascota.
    private JTextField txtDniUsuario;
    private JTextField txtNombreMascota;
    private JTextField txtSexoMascota;
    private JButton btnRegistrarMascota;
    private ConexionMySQL gestorDB;

    // Constructor que recibe un gestor de la base de datos, inicializa la interfaz y establece configuraciones básicas de la ventana.
    public RegistroMascotas(ConexionMySQL gestorDB) {
        if (gestorDB == null) {
            throw new IllegalArgumentException("El gestor de la base de datos no puede ser nulo");
        }
        this.gestorDB = gestorDB;
        setTitle("Registro de Mascota - Clínica Veterinaria");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    // Método initUI que inicializa y organiza los componentes de la interfaz gráfica.
    private void initUI() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("DNI del Usuario:"));
        txtDniUsuario = new JTextField();
        panel.add(txtDniUsuario);

        panel.add(new JLabel("Nombre de la Mascota:"));
        txtNombreMascota = new JTextField();
        panel.add(txtNombreMascota);

        panel.add(new JLabel("Sexo de la Mascota (M/F):"));
        txtSexoMascota = new JTextField();
        panel.add(txtSexoMascota);

        btnRegistrarMascota = new JButton("Registrar Mascota");
        btnRegistrarMascota.addActionListener(this::registrarMascota);
        add(panel, BorderLayout.CENTER);
        add(btnRegistrarMascota, BorderLayout.SOUTH);

        pack(); // Ajusta el marco para que coincida con el contenido
    }

    // Método registrarMascota que maneja la acción de registrar una mascota en la base de datos.
    private void registrarMascota(ActionEvent evento) {
        String dniUsuario = txtDniUsuario.getText().trim();
        String nombreMascota = txtNombreMascota.getText().trim();
        String sexoMascota = txtSexoMascota.getText().trim();

        try {
            if (ConexionMySQL.usuarioExistes(dniUsuario)) {
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

    // Método main que crea una instancia de ConexionMySQL, inicializa la ventana RegistroMascotas y la hace visible.
    public static void main(String[] args) throws SQLException {
        ConexionMySQL dbManager = new ConexionMySQL();
        RegistroMascotas frame = new RegistroMascotas(dbManager);
        frame.setVisible(true);
    }
}

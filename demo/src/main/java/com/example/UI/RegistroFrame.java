// Paquete que organiza las clases relacionadas con la implementación.
package com.example.UI;

// Importación de las librerías necesarias para la interfaz gráfica, manejo de eventos y conexión con la base de datos.
import javax.swing.*;

import com.example.BASEDEDATOS.ConexionMySQL;
import com.example.DAO.PropietarioDAO;
import com.example.MODELOS.Propietario;
import com.example.SERVICIOS.PropietarioService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

// Clase RegistroFrame que hereda de JFrame para crear una ventana de aplicación.
public class RegistroFrame extends JFrame {
    // Componentes de la interfaz para captura de datos del usuario y propietario.
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JTextField txtDniUsuario;
    private JTextField txtNombrePropietario;
    private JTextField txtTelefonoPropietario;
    private JTextField txtDireccionPropietario;
    private JButton btnRegistrar;
    private ConexionMySQL gestorDB;
    private PropietarioService propietarioService;

    // Constructor que inicializa la conexión con la base de datos y el servicio de propietarios.
    public RegistroFrame(ConexionMySQL gestorDB, PropietarioService propietarioService) {
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        setTitle("Registro de Usuario - Clínica Veterinaria"); // Título de la ventana
        setSize(1000, 500); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamiento al cerrar
        initUI(); // Inicialización de la interfaz de usuario
        setLocationRelativeTo(null); // Centrar ventana
    }

    // Método para inicializar los componentes de la interfaz de usuario.
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10)); // Panel con diseño de rejilla

        // Agregar y configurar cada componente del formulario de registro.
        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        panel.add(new JLabel("DNI:"));
        txtDniUsuario = new JTextField(15);
        panel.add(txtDniUsuario);

        panel.add(new JLabel("Nombre Propietario:"));
        txtNombrePropietario = new JTextField(15);
        panel.add(txtNombrePropietario);

        panel.add(new JLabel("Teléfono Propietario:"));
        txtTelefonoPropietario = new JTextField(15);
        panel.add(txtTelefonoPropietario);

        panel.add(new JLabel("Dirección Propietario:"));
        txtDireccionPropietario = new JTextField(15);
        panel.add(txtDireccionPropietario);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this::registrarUsuario);
        panel.add(btnRegistrar);

        add(panel); // Añadir el panel al JFrame
    }

    // Método para manejar el evento de clic en el botón de registro.
    private void registrarUsuario(ActionEvent evento) {
        // Extracción y limpieza de los datos ingresados por el usuario.
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String dniUsuario = txtDniUsuario.getText().trim();
        String nombreProp = txtNombrePropietario.getText().trim();
        String telefonoProp = txtTelefonoPropietario.getText().trim();
        String direccionProp = txtDireccionPropietario.getText().trim();

        // Validación de campos completos.
        if (usuario.isEmpty() || contraseña.isEmpty() || dniUsuario.isEmpty() ||
            nombreProp.isEmpty() || telefonoProp.isEmpty() || direccionProp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Verificación de la existencia previa del usuario y el DNI.
            if (!ConexionMySQL.usuarioExistes(usuario)) {
                Propietario propietario = new Propietario(dniUsuario, nombreProp, telefonoProp, direccionProp);
                // Registro del propietario y del usuario.
                if (propietarioService.registrarPropietario(propietario)) {
                    if (ConexionMySQL.insertarUsuario(usuario, contraseña, dniUsuario)) {
                        JOptionPane.showMessageDialog(this, "Usuario y propietario registrados correctamente.");
                        this.dispose(); // Cerrar la ventana actual
                        new LoginFrame(gestorDB).setVisible(true); // Abrir ventana de inicio de sesión
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar propietario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de usuario o DNI ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método main para ejecutar la aplicación.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ConexionMySQL gestorDB = new ConexionMySQL(); // Ensure this method does not return null
                PropietarioDAO propietarioDao = new PropietarioDAO(ConexionMySQL.connect()); // Check that connect() does not return null
                PropietarioService propietarioService = new PropietarioService(propietarioDao);
                RegistroFrame registroFrame = new RegistroFrame(gestorDB, propietarioService);
                registroFrame.setVisible(true);
            } catch (Exception e) {
                System.out.println("Failed to initialize the application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
}

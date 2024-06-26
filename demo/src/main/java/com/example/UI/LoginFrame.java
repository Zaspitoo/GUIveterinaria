// Paquete que organiza las clases relacionadas con el marco de inicio de sesión.
package com.example.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import com.example.BASEDEDATOS.ConexionMySQL;

// Clase que crea una ventana para el inicio de sesión del usuario.
public class LoginFrame extends JFrame {
    private JTextField txtUsuario; // Campo de texto para el nombre de usuario.
    private JPasswordField txtContraseña; // Campo de contraseña para la contraseña.
    private JButton btnIniciarSesion, btnSalir; // Botones para iniciar sesión y salir.

    public LoginFrame(ConexionMySQL gestorDB) {
        setTitle("DAM Developer - Login"); // Título de la ventana.
        setSize(1000, 500); // Tamaño de la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Operación de cierre de la ventana.
        initUI(); // Inicialización de la interfaz de usuario.
        setLocationRelativeTo(null); // Centrado de la ventana.
    }

    private void initUI() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("/com/example/gui2.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Uso de GridBagLayout para un mejor control de la colocación de componentes.
        setContentPane(backgroundPanel); // Establece el backgroundPanel como el content pane

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 20, 10, 20); // Márgenes para los componentes.

        JLabel lblUsername = new JLabel("Enter username:"); // Etiqueta para el nombre de usuario.
        constraints.gridy = 0;
        backgroundPanel.add(lblUsername, constraints);

        txtUsuario = new JTextField(15); // Campo de texto para ingresar el nombre de usuario.
        constraints.gridy = 1;
        backgroundPanel.add(txtUsuario, constraints);

        JLabel lblPassword = new JLabel("Enter password:"); // Etiqueta para la contraseña.
        constraints.gridy = 2;
        backgroundPanel.add(lblPassword, constraints);

        txtContraseña = new JPasswordField(15); // Campo de contraseña para ingresar la contraseña.
        constraints.gridy = 3;
        backgroundPanel.add(txtContraseña, constraints);

        btnIniciarSesion = new JButton("Login"); // Botón para iniciar sesión.
        btnSalir = new JButton("Exit"); // Botón para salir de la aplicación.
        JPanel btnPanel = new JPanel(new FlowLayout()); // Panel para organizar los botones.
        btnPanel.add(btnIniciarSesion);
        btnPanel.add(btnSalir);
        constraints.gridy = 4;
        backgroundPanel.add(btnPanel, constraints);

        btnIniciarSesion.addActionListener(this::iniciarSesion); // Manejador de eventos para el botón de inicio de sesión.
        btnSalir.addActionListener(e -> System.exit(0)); // Manejador de eventos para el botón de salida.

        // Paneles adicionales para botones de redes sociales.
        JPanel socialPanel = new JPanel(new FlowLayout()); // Panel para iconos de redes sociales.
        socialPanel.add(new JLabel(new ImageIcon("facebook.png"))); // Icono de Facebook.
        socialPanel.add(new JLabel(new ImageIcon("/com/example/email.png"))); // Icono de correo electrónico.
        socialPanel.add(new JLabel(new ImageIcon("twitter.png"))); // Icono de Twitter.
        constraints.gridy = 5;
        backgroundPanel.add(socialPanel, constraints);
    }

    private void iniciarSesion(ActionEvent evento) {
        String usuario = txtUsuario.getText().trim(); // Obtención del texto del nombre de usuario.
        char[] contraseña = txtContraseña.getPassword(); // Obtención de la contraseña.

        try {
            if (ConexionMySQL.validarCredenciales(usuario, new String(contraseña))) { // Validación de las credenciales con la base de datos.
                JOptionPane.showMessageDialog(this, "Login successful!"); // Mensaje de éxito.
                MainFrame mainFrame = new MainFrame(); // Creación de la ventana principal.
                mainFrame.setVisible(true); // Mostrar la ventana principal.
                this.dispose(); // Cerrar la ventana de inicio de sesión.
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE); // Mensaje de error.
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Manejo de excepciones.
        } finally {
            Arrays.fill(contraseña, '0'); // Seguridad: limpieza del arreglo de contraseña.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionMySQL gestorDB = new ConexionMySQL(); // Creación de la conexión a la base de datos.
            LoginFrame loginFrame = new LoginFrame(gestorDB); // Creación del marco de inicio de sesión.
            loginFrame.setVisible(true); // Visualización del marco de inicio de sesión.
        });
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminMainFrame extends JFrame {
    private DBmanager gestorDB;
    private JTextArea txtAreaDatos;
    private PropietarioService propietarioService;
    private CitaService citaService;

    public AdminMainFrame(DBmanager gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        this.citaService = citaService;
        setTitle("Panel de Administración - Clínica Veterinaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        // Botones para Propietario
        JButton btnAgregarPropietario = new JButton("Agregar Propietario");
        btnAgregarPropietario.addActionListener(this::agregarPropietario);
        buttonsPanel.add(btnAgregarPropietario);

        JButton btnEditarPropietario = new JButton("Editar Propietario");
        btnEditarPropietario.addActionListener(a -> {
            try {
                editarPropietario(a);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        buttonsPanel.add(btnEditarPropietario);

        JButton btnEliminarPropietario = new JButton("Eliminar Propietario");
        btnEliminarPropietario.addActionListener(this::eliminarPropietario);
        buttonsPanel.add(btnEliminarPropietario);

        JButton btnVerPropietarios = new JButton("Ver Propietarios");
        btnVerPropietarios.addActionListener(this::verPropietarios);
        buttonsPanel.add(btnVerPropietarios);

        // Botones para Cita
        JButton btnAgregarCita = new JButton("Agregar Cita");
        btnAgregarCita.addActionListener(this::agregarCita);
        buttonsPanel.add(btnAgregarCita);

        JButton btnEditarCita = new JButton("Editar Cita");
        btnEditarCita.addActionListener(this::editarCita);
        buttonsPanel.add(btnEditarCita);

        JButton btnEliminarCita = new JButton("Eliminar Cita");
        btnEliminarCita.addActionListener(this::eliminarCita);
        buttonsPanel.add(btnEliminarCita);

        JButton btnVerCitas = new JButton("Ver Citas");
        btnVerCitas.addActionListener(this::verCitas);
        buttonsPanel.add(btnVerCitas);

        txtAreaDatos = new JTextArea(15, 50);
        txtAreaDatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDatos);

        panel.add(buttonsPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        this.add(panel);
    }

    private void agregarPropietario(ActionEvent e) {
        // Interactive dialogue to input new owner data and save it
        String nombre = JOptionPane.showInputDialog(this, "Nombre del propietario:");
        String telefono = JOptionPane.showInputDialog(this, "Teléfono del propietario:");
        String direccion = JOptionPane.showInputDialog(this, "Dirección del propietario:");
        Integer ID = Integer.parseInt(JOptionPane.showInputDialog(this, "ID DEL PROPIETARIO"));
        if (nombre != null && telefono != null && direccion != null ) {
            Propietario propietario = new Propietario(ID, nombre, telefono, direccion);
            boolean result = propietarioService.registrarPropietario(propietario);
            if (result) {
                JOptionPane.showMessageDialog(this, "Propietario agregado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar propietario.");
            }
        }
    }
    
    private void editarPropietario(ActionEvent e) throws SQLException {
        String idTexto = JOptionPane.showInputDialog(this, "ID del propietario a editar:");
        if (idTexto != null && !idTexto.trim().isEmpty()) {
            try {
                Integer ID = Integer.parseInt(idTexto.trim());
                Optional<Propietario> propietarioOptional = propietarioService.buscarPropietario(ID);
                if (propietarioOptional.isPresent()) {
                    Propietario propietario = propietarioOptional.get();
                    String newName = JOptionPane.showInputDialog(this, "Nuevo nombre del propietario:", propietario.getNombre());
                    if (newName != null && !newName.trim().isEmpty()) {
                        String newPhone = JOptionPane.showInputDialog(this, "Nuevo teléfono del propietario:", propietario.getTelefono());
                        if (newPhone != null && !newPhone.trim().isEmpty()) {
                            String newAddress = JOptionPane.showInputDialog(this, "Nueva dirección del propietario:", propietario.getDireccion());
                            if (newAddress != null && !newAddress.trim().isEmpty()) {
                                propietario.setNombre(newName.trim());
                                propietario.setTelefono(newPhone.trim());
                                propietario.setDireccion(newAddress.trim());
                                boolean result = propietarioService.actualizarPropietario(propietario);
                                if (result) {
                                    JOptionPane.showMessageDialog(this, "Propietario actualizado exitosamente.");
                                } else {
                                    JOptionPane.showMessageDialog(this, "Error al actualizar propietario.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "La actualización de la dirección no puede estar vacía.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "La actualización del teléfono no puede estar vacía.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "El nuevo nombre no puede estar vacío.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Propietario no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "La entrada del ID no puede estar vacía.");
        }
    }

    private void eliminarPropietario(ActionEvent e) {
        // Confirm and delete a owner record
        Integer ID = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del propietario a eliminar:"));
        boolean confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este propietario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        if (confirm) {
            boolean result = propietarioService.borrarPropietario(ID);
            if (result) {
                JOptionPane.showMessageDialog(this, "Propietario eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar propietario.");
            }
        }
    }

    private void verPropietarios(ActionEvent e) {
        // Fetch and display a list of all owners
        List<Propietario> propietarios = propietarioService.listarPropietarios();
        StringBuilder sb = new StringBuilder();
        for (Propietario propietario : propietarios) {
            sb.append(propietario.toString()).append("\n");
        }
        txtAreaDatos.setText(sb.toString());
    }

    private void agregarCita(ActionEvent e) {
        // Interactive dialogue to input new appointment data and save it
        int propietarioId = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del propietario para la cita:"));
        String fechaHora = JOptionPane.showInputDialog(this, "Fecha y hora de la cita (yyyy-MM-dd HH:mm):");
        String motivo = JOptionPane.showInputDialog(this, "Motivo de la cita:");
        if (fechaHora != null && motivo != null) {
            Cita cita = new Cita(0, propietarioId, fechaHora, motivo);
            boolean result = citaService.registrarCita(cita);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cita agregada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar cita.");
            }
        }
    }

    private void editarCita(ActionEvent e) {
        // Retrieve, edit, and update an existing appointment record
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID de la cita a editar:"));
        Cita cita = citaService.buscarCita(id);
        if (cita != null) {
            String newFechaHora = JOptionPane.showInputDialog(this, "Nueva fecha y hora de la cita (yyyy-MM-dd HH:mm):", cita.getFechaHora());
            String newMotivo = JOptionPane.showInputDialog(this, "Nuevo motivo de la cita:", cita.getMotivo());
            cita.setFechaHora(newFechaHora);
            cita.setMotivo(newMotivo);
            boolean result = citaService.actualizarCita(cita);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cita actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cita.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cita no encontrada.");
        }
    }

    private void eliminarCita(ActionEvent e) {
        // Confirm and delete an appointment record
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID de la cita a eliminar:"));
        boolean confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta cita?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        if (confirm) {
            boolean result = citaService.borrarCita(id);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cita eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cita.");
            }
        }
    }

    private void verCitas(ActionEvent e) {
        // Fetch and display a list of all appointments
        List<Cita> citas = citaService.listarCitas();
        StringBuilder sb = new StringBuilder();
        for (Cita cita : citas) {
            sb.append(cita.toString()).append("\n");
        }
        txtAreaDatos.setText(sb.toString());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DBmanager gestorDB = new DBmanager(); // Inicializa primero
                PropietarioDAO propietarioDAO = new PropietarioDAO(gestorDB.getConnection());
                CitaDAO citaDAO = new CitaDAO(gestorDB.getConnection());
                PropietarioService propietarioService = new PropietarioService(propietarioDAO);
                CitaService citaService = new CitaService(citaDAO);
                new AdminMainFrame(gestorDB, propietarioService, citaService).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}

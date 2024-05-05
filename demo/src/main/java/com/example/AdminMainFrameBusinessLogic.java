package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;
import java.sql.SQLException; // Agregado
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminMainFrameBusinessLogic {
    private ConexionMySQL gestorDB;
    private PropietarioService propietarioService;
    private CitaService citaService;

    public AdminMainFrameBusinessLogic(ConexionMySQL gestorDB, PropietarioService propietarioService, CitaService citaService) {
        this.gestorDB = gestorDB;
        this.propietarioService = propietarioService;
        this.citaService = citaService;
    }

    public ConexionMySQL getGestorDB() {
        return gestorDB;
    }

    public void setGestorDB(ConexionMySQL gestorDB) {
        this.gestorDB = gestorDB;
    }

    public PropietarioService getPropietarioService() {
        return propietarioService;
    }

    public CitaService getCitaService() {
        return citaService;
    }

    public void agregarPropietario(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog("Nombre del propietario:");
        String telefono = JOptionPane.showInputDialog("Teléfono del propietario:");
        String direccion = JOptionPane.showInputDialog("Dirección del propietario:");
        String idTexto = JOptionPane.showInputDialog("ID del propietario:");
        if (nombre != null && telefono != null && direccion != null && idTexto != null) {
            try {
                int id = Integer.parseInt(idTexto);
                Propietario propietario = new Propietario(id, nombre, telefono, direccion);
                boolean result = propietarioService.registrarPropietario(propietario);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Propietario agregado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar propietario.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
            }
        }
    }

    public void editarPropietario(ActionEvent e) throws SQLException {
        int id = getIntInput("ID del propietario a editar:");
        if (id != -1) {
            Optional<Propietario> propietario = propietarioService.obtenerPropietario(id);
            if (propietario.isPresent()) {
                String nombre = JOptionPane.showInputDialog("Nuevo nombre del propietario:", propietario.get().getNombre());
                String telefono = JOptionPane.showInputDialog("Nuevo teléfono del propietario:", propietario.get().getTelefono());
                String direccion = JOptionPane.showInputDialog("Nueva dirección del propietario:", propietario.get().getDireccion());
                if (nombre != null && telefono != null && direccion != null) {
                    Propietario updatedPropietario = new Propietario(id, nombre, telefono, direccion);
                    boolean result = propietarioService.actualizarPropietario(updatedPropietario);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Propietario actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar propietario.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Propietario no encontrado.");
            }
        }
    }

    public void eliminarPropietario(ActionEvent e) {
        int id = getIntInput("ID del propietario a eliminar:");
        if (id != -1) {
            boolean confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este propietario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (confirm) {
                boolean result = propietarioService.eliminarPropietario(id);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Propietario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar propietario.");
                }
            }
        }
    }

    public void verPropietarios(ActionEvent e) {
        List<Propietario> propietarios = propietarioService.listarPropietarios();
        StringBuilder sb = new StringBuilder();
        for (Propietario propietario : propietarios) {
            sb.append(propietario.toString()).append("\\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void agregarCitas(ActionEvent e) {
    String fechaHora = JOptionPane.showInputDialog("Ingrese la fecha y hora de la cita (formato YYYY-MM-DD HH:MM):");
    String motivo = JOptionPane.showInputDialog("Motivo de la cita:");
    String username = JOptionPane.showInputDialog("Ingrese usuario:");

    if (fechaHora != null && motivo != null && username != null) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setLenient(false);  // No permitir fechas no válidas
            sdf.parse(fechaHora);  // Validar el formato de fecha y hora

            Cita cita = new Cita(motivo, fechaHora, username);  // Asumir el orden correcto en el constructor
            boolean result = citaService.registrarCita(cita);  // Método de la clase de servicio para registrar la cita
            if (result) {
                JOptionPane.showMessageDialog(null, "Cita agregada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar cita.");
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha y hora incorrecto. Use el formato 'YYYY-MM-DD HH:MM'.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        }
    }
}

    

    public void editarCita(ActionEvent e) {
        String idCita = JOptionPane.showInputDialog("ID de la cita a editar:");
        if (idCita != null && !idCita.isEmpty()) {
            try {
                int id = Integer.parseInt(idCita);
                Optional<Cita> citaOpt = Optional.of(citaService.buscarCita(id));
                if (citaOpt.isPresent()) {
                    Cita cita = citaOpt.get();
                    String newFechaHora = JOptionPane.showInputDialog("Nueva fecha y hora de la cita:", cita.getFechaHora());
                    String newMotivo = JOptionPane.showInputDialog("Nuevo motivo de la cita:", cita.getMotivo());
                    if (newFechaHora != null && newMotivo != null) {
                        cita.setFechaHora(newFechaHora);
                        cita.setMotivo(newMotivo);
                        boolean result = citaService.actualizarCita(cita);
                        if (result) {
                            JOptionPane.showMessageDialog(null, "Cita actualizada exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al actualizar cita.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cita no encontrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
            }
        }
    }

    public void eliminarCita(ActionEvent e) {
        String idCita = JOptionPane.showInputDialog("ID de la cita a eliminar:");
        if (idCita != null && !idCita.isEmpty()) {
            try {
                int id = Integer.parseInt(idCita);
                boolean confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta cita?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                if (confirm) {
                    boolean result = citaService.borrarCita(id);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar cita.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
            }
        }
    }

    public void verCitas(ActionEvent e) {
        List<Cita> citas = citaService.listarCitas();
        StringBuilder sb = new StringBuilder();
        for (Cita cita : citas) {
            sb.append(cita.toString()).append("\\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private int getIntInput(String message) {
        String input = JOptionPane.showInputDialog(message);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
            return -1;  // Retorna -1 si hay un error de formato, indicando un error de entrada
        }
    }
}

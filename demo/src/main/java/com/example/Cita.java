package com.example;

/**
 * Cita class represents an appointment in the veterinary clinic system.
 */
public class Cita {
    private int id;
    private int propietarioId;
    private String fechaHora;
    private String motivo;

    /**
     * Constructor for Cita.
     * @param id The unique identifier for the appointment.
     * @param propietarioId The identifier for the owner associated with the appointment.
     * @param fechaHora The date and time of the appointment.
     * @param motivo The reason for the appointment.
     */
    public Cita(int id, int propietarioId, String fechaHora, String motivo) {
        this.id = id;
        this.propietarioId = propietarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", propietarioId=" + propietarioId +
                ", fechaHora='" + fechaHora + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}

package com.example;

/**
 * Cita class represents an appointment in the veterinary clinic system.
 */
public class Cita {
    private String id;
    private String propietarioId;
    private String fechaHora;
    private String motivo;

    /**
     * Constructor for Cita.
     * @param i The unique identifier for the appointment.
     * @param propietarioId The identifier for the owner associated with the appointment.
     * @param fechaHora The date and time of the appointment.
     */
    public Cita( String propietarioId, String fechaHora, String motivo) {
        
        this.propietarioId = propietarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

   

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(String propietarioId) {
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

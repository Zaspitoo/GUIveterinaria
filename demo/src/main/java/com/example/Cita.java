package com.example;



/**
 * Cita class represents an appointment in the veterinary clinic system.
 */
public class Cita {
    private int id;
    private int propietarioId;
    private String fechaHora;  // Cambiado a LocalDateTime para un manejo adecuado de fechas
    private String motivo;
    private String username;

    /**
     * Constructor for Cita.
     * @param propietarioId The identifier for the owner associated with the appointment.
     * @param fechaHora The date and time of the appointment.
     * @param motivo The reason for the appointment.
     */
    public Cita(int id,int propietarioId, String fechaHora, String motivo, String username) {
        this.id = id;  
        this.propietarioId = propietarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.username = username;

    }

    public Cita(int propietarioId, String fechaHora, String motivo) {
        this.propietarioId = propietarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public Cita(int id, String motivo, String fechaHora, String username) {
        this.id = id;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.username = username;

    }

    public Cita(String username, String motivo, String fechaHora) {
        this.username = username;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
    }

    public String getusername(){
        return username;
    }

    public void setusername(String username){

        this.username = username;
    }

    // Método para generar un ID único, podría integrar una lógica más compleja o base de datos
    public void setID(int id){
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return id;
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
                "id='" + id + '\'' +
                ", propietarioId='" + propietarioId + '\'' +
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}

package com.example;

public class HistorialMedico {
    private int id;
    private int mascotaId;
    private String fecha;
    private String detalles;

    public HistorialMedico(int id, int mascotaId, String fecha, String detalles) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.fecha = fecha;
        this.detalles = detalles;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "HistorialMedico{" +
               "id=" + id +
               ", mascotaId=" + mascotaId +
               ", fecha='" + fecha + '\'' +
               ", detalles='" + detalles + '\'' +
               '}';
    }
}

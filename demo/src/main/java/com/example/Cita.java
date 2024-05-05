package com.example;



/**
 * Cita class represents an appointment in the veterinary clinic system.
 */
public class Cita {
    private int id;
    private String fechaHora;  
    private String motivo;
    private String username;

    

  

    public Cita(int id, String motivo, String fechaHora, String username) {
        this.id = id;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.username = username;

    }

    public Cita(String motivo,String fechaHora,  String username) {
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.username = username;
        
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
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
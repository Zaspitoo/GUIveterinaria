package com.example;

/**
 * Propietario class represents an owner of pets in the veterinary clinic system.
 */
public class Propietario {
    
    private int ID;
    private String nombre;
    private String telefono;
    private String direccion;
    private String dniUsuario;
    

    /**
     * Constructor for Propietario.
     * @param ID The unique identifier for the owner (can include characters).
     * @param nombre The name of the owner.
     * @param telefono The phone number of the owner.
     * @param direccion The address of the owner.
     */
    public Propietario(int ID, String nombre, String telefono, String direccion) {
        this.ID = ID;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Propietario(String dniUsuario, String nombre, String telefono, String direccion) {
        this.dniUsuario = dniUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getdniUsuario(){
        return dniUsuario;
    }
    public void setdniUsuario(String dniUsuario){
        this.dniUsuario = dniUsuario;
    }

    @Override
    public String toString() {
        return "Propietario{" +
               "ID='" + ID + '\'' +
               ", nombre='" + nombre + '\'' +
               ", telefono='" + telefono + '\'' +
               ", direccion='" + direccion + '\'' +
               '}';
    }
}

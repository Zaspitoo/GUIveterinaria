package com.example;

/**
 * Propietario class represents an owner of pets in the veterinary clinic system.
 */
public class Propietario {
    
    private String nombre;
    private String telefono;
    private String direccion;
    private int ID;  // Cambio del tipo de ID a String
    private String dNI;

    /**
     * Constructor for Propietario.
     * @param iD The unique identifier for the owner.
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

    public Propietario(String dNI, String nombreProp, String telefonoProp, String direccionProp) {
        this.nombre = nombreProp;
        this.telefono = telefonoProp;
        this.direccion = direccionProp;
        this.dNI = dNI;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }
    public String getDNI() {
        return dNI;
    }
    public void setDNI(String dNI) {
        this.dNI = dNI;
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

    @Override
    public String toString() {
        return "Propietario{" +
               "id='" + ID + '\'' +
               ", nombre='" + nombre + '\'' +
               ", telefono='" + telefono + '\'' +
               ", direccion='" + direccion + '\'' +
               '}';
    }
}


package com.example;

/**
 * Propietario class represents an owner of pets in the veterinary clinic system.
 */
public class Propietario {
    
    private String nombre;
    private String telefono;
    private String direccion;
    private Integer ID;

    /**
     * Constructor for Propietario.
     * @param id The unique identifier for the owner.
     * @param nombre The name of the owner.
     * @param telefono The phone number of the owner.
     * @param direccion The address of the owner.
     * @param iD
     */
    public Propietario(Integer ID, String nombre, String telefono, String direccion ) {
        
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ID = ID;
        

    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }


    public void setID(Integer ID) {
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
                "id=" + ID +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

package com.example;

/**
 * Propietario class represents an owner of pets in the veterinary clinic system.
 */
public class Propietario {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;

    /**
     * Constructor for Propietario.
     * @param id The unique identifier for the owner.
     * @param nombre The name of the owner.
     * @param telefono The phone number of the owner.
     * @param direccion The address of the owner.
     */
    public Propietario(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

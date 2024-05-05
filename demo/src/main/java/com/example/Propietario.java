// Paquete que organiza las clases relacionadas con los propietarios.
package com.example;

/**
 * Clase Propietario que representa a un dueño de mascotas en el sistema de clínica veterinaria.
 */
public class Propietario {
    
    private int ID; // Identificador único del propietario.
    private String nombre; // Nombre del propietario.
    private String telefono; // Número de teléfono del propietario.
    private String direccion; // Dirección residencial del propietario.
    private String dniUsuario; // DNI del usuario, opcionalmente usado como identificador.
    
    /**
     * Constructor para crear un Propietario con identificador numérico.
     * @param ID El identificador único del propietario.
     * @param nombre El nombre del propietario.
     * @param telefono El número de teléfono del propietario.
     * @param direccion La dirección del propietario.
     */
    public Propietario(int ID, String nombre, String telefono, String direccion) {
        this.ID = ID;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Constructor para crear un Propietario usando el DNI como identificador alternativo.
     * @param dniUsuario El DNI del usuario.
     * @param nombre El nombre del propietario.
     * @param telefono El número de teléfono del propietario.
     * @param direccion La dirección del propietario.
     */
    public Propietario(String dniUsuario, String nombre, String telefono, String direccion) {
        this.dniUsuario = dniUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Métodos para obtener y establecer el ID del propietario.
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // Métodos para obtener y establecer el nombre del propietario.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos para obtener y establecer el número de teléfono del propietario.
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Métodos para obtener y establecer la dirección del propietario.
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Métodos para obtener y establecer el DNI del usuario.
    public String getdniUsuario(){
        return dniUsuario;
    }

    public void setdniUsuario(String dniUsuario){
        this.dniUsuario = dniUsuario;
    }

    /**
     * Representación en cadena de la clase Propietario.
     */
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

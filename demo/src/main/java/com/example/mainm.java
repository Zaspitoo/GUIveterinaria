package com.example;

import java.sql.SQLException;

public class mainm {
    public static void main(String[] args) {
        
        String usuario = "freedb_andav";
        String pass = "6q4x3^dFMS$@JAV";
        String bd = "freedb_veterinariaclinica";
        String host = "sql.freebd.tech";
    
        
        ConexionMySQL conexion = new ConexionMySQL(usuario, pass, bd, host);
    
        try {
            // Intentar conectar a la base de datos
            conexion.conectar();
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            // Manejar la excepción si la conexión falla
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    
      
    }
}

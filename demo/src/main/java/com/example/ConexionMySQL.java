package com.example;

import java.sql.*;  // Importa las clases necesarias para trabajar con SQL.

// Clase que gestiona la conexión a la base de datos MySQL.
public class ConexionMySQL {
    // Detalles de la conexión a la base de datos.
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_veterinariaclinica";
    private static final String USER = "freedb_andav";
    private static final String PASSWORD = "6q4X3*dFM$$@JAV";

    // Método estático que retorna una conexión a la base de datos.
    public static Connection getConexion() throws SQLException {
        // Retorna una conexión usando los datos de URL, usuario y contraseña.
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

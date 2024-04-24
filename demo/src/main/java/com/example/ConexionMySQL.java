package com.example;

import java.sql.*;

public class ConexionMySQL {
    // Utiliza las credenciales proporcionadas en la imagen para conectar con la base de datos
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_veterinariaclinica";
    private static final String USER = "freedb_andav";
    private static final String PASSWORD = "6q4X3*dFM$$@JAV";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

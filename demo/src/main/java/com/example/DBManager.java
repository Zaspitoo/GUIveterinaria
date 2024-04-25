package com.example;

import java.sql.*; // Importa todas las clases necesarias para manejar la base de datos.

public class DBmanager {
    private Connection conexion; // Variable para almacenar la conexión con la base de datos.

    // Constructor que intenta establecer una conexión con la base de datos.
    public DBmanager() {
        try {
            // Intenta obtener la conexión usando la clase ConexionMySQL.
            this.conexion = ConexionMySQL.getConexion();
        } catch (SQLException e) {
            // Si hay un error, imprime un mensaje.
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Método para cerrar la conexión con la base de datos.
    public void cerrarConexion() {
        try {
            if (this.conexion != null) this.conexion.close(); // Cierra la conexión si no es nula.
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Método para añadir un nuevo usuario a la base de datos.
    public boolean insertarUsuario(String username, String password, String dniUsuario) throws SQLException {
        String sql = "INSERT INTO users (username, password, DNI) VALUES (?, ?, ?)"; // Consulta SQL.
        return executeInsert(sql,username, password, dniUsuario); // Ejecuta la inserción.
    }

    
    // Método para verificar las credenciales de un usuario.
public boolean verificarCredenciales(String nombreUsuario, String contraseña) {
    String sql = "SELECT password FROM users WHERE username = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        pstmt = conexion.prepareStatement(sql);
        pstmt.setString(1, nombreUsuario);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            return contraseña.equals(rs.getString("password")); // Compara las contraseñas.
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar credenciales: " + e.getMessage());
    } finally {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { /* Manejo de error */ }
        }
        if (pstmt != null) {
            try { pstmt.close(); } catch (SQLException e) { /* Manejo de error */ }
        }
    }
    return false; // Devuelve falso si no hay coincidencias o si ocurre un error.
}


    public boolean usuarioExiste(String nombreUsuario) {
        String sql = "SELECT COUNT(*) AS cantidad FROM users WHERE username = ?";
        try {
            ResultSet rs = executeQuery(sql, nombreUsuario);
            if (rs.next()) {
                return rs.getInt("cantidad") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del usuario: " + e.getMessage());
        }
        return false;
    }

    // Método para añadir una nueva mascota a la base de datos.
public boolean insertarMascota(String dniUsuario, String nombreMascota, String sexoMascota) throws SQLException {
    // Asume que tienes una tabla llamada 'mascotas' con columnas 'usuario_dni', 'nombre' y 'sexo'.
    String sql = "INSERT INTO Mascotas (Usuario_DNI, nombre, Sexo) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        // Establece los valores de los parámetros basados en los argumentos del método.
        pstmt.setString(1, (dniUsuario)); // Convierte el DNI a entero, asumiendo que la columna es de tipo INT.
        pstmt.setString(2, nombreMascota);
        pstmt.setString(3, sexoMascota);

        // Ejecuta la inserción y verifica si se insertó alguna fila.
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0;
    } catch (NumberFormatException e) {
        // Esta excepción se lanza si el DNI no es un número válido.
        System.out.println("El DNI debe ser un número entero: " + e.getMessage());
        return false;
    }
    // No es necesario manejar SQLException aquí, ya que el método lo lanza y debería ser manejado por el método llamante.
}


   
   // Método para ejecutar consultas SQL.
public ResultSet executeQuery(String sql, String... parametros) throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet resultSet = null;
    try {
        pstmt = conexion.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            pstmt.setString(i + 1, parametros[i]);
        }
        resultSet = pstmt.executeQuery();
    } catch (SQLException e) {
        if (pstmt != null) pstmt.close(); // Asegúrate de cerrar el PreparedStatement si ocurre una excepción.
        throw e; // Relanza la excepción para manejarla en un nivel superior.
    }
    return resultSet; // Retorna el ResultSet para procesarlo externamente.
}


    // Método para ejecutar actualizaciones SQL (como inserciones).
    public boolean executeInsert(String sql, String... parametros) throws SQLException {
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setString(i + 1, parametros[i]);
            }
            int filasAfectadas = pstmt.executeUpdate(); // Ejecuta la actualización y obtiene el número de filas afectadas.
            return filasAfectadas > 0; // Devuelve verdadero si hay filas afectadas, indicando que la operación fue exitosa.
        }
    }
}

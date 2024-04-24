package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBmanager {
    private Connection connection;

    public DBmanager() {
        try {
            this.connection = ConexionMySQL.getConnection();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public boolean executeInsert(String sql) throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            int affectedRows = stmt.executeUpdate(sql);
            return affectedRows > 0;
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.executeQuery(sql);
        }
    }

    public boolean verifyCredentials(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return verifyPassword(password, storedPassword);
            }
            return false;
        }
    }

    public boolean userExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        }
    }

    public boolean addUser(String username, String password) throws SQLException {
        String hashedPassword = hashPassword(password);
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    private boolean verifyPassword(String password, String storedPassword) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(storedPassword);
    }
}

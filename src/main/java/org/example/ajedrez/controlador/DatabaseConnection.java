package org.example.ajedrez.controlador;


import java.sql.*;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/chess";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    public DatabaseConnection() {
        inicializarBaseDeDatos();
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    private static void inicializarBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS chess;");
            stmt.executeUpdate("USE chess;");


            // Crear tabla usuarios con el campo usuario
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                usuario VARCHAR(50) NOT NULL UNIQUE,
                correo VARCHAR(255) NOT NULL UNIQUE
            
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """);

            // Crear tabla partidas
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS partidas (
                id INT AUTO_INCREMENT PRIMARY KEY,
                jugador_blancas_id INT NOT NULL,
                jugador_negras_id INT NOT NULL,
                fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (jugador_blancas_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                FOREIGN KEY (jugador_negras_id) REFERENCES usuarios(id) ON DELETE CASCADE
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """);

            // Crear tabla movimientos
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS movimientos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                partida_id INT NOT NULL,
                movimiento VARCHAR(10) NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """);

            System.out.println("Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

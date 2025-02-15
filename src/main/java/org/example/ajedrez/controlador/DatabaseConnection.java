package org.example.ajedrez.controlador;


import java.sql.*;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/chess";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

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
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/chess", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

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
                turno_blancas BOOLEAN NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """);

            System.out.println("Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void guardarPartida(int jugadorBlancasId, int jugadorNegrasId, List<String> movimientos) {
        String insertPartida = "INSERT INTO partidas (jugador_blancas_id, jugador_negras_id) VALUES (?, ?)";
        String insertMovimiento = "INSERT INTO movimientos (partida_id, movimiento, turno_blancas) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmtPartida = conn.prepareStatement(insertPartida, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtMovimiento = conn.prepareStatement(insertMovimiento)) {

            // 1. Guardar partida y obtener su ID
            stmtPartida.setInt(1, jugadorBlancasId);
            stmtPartida.setInt(2, jugadorNegrasId);
            stmtPartida.executeUpdate();

            ResultSet generatedKeys = stmtPartida.getGeneratedKeys();
            int partidaId = -1;
            if (generatedKeys.next()) {
                partidaId = generatedKeys.getInt(1);
            }

            // 2. Guardar movimientos
            boolean turnoBlancas = true;  // Asumimos que el primer movimiento es de blancas
            for (String movimiento : movimientos) {
                stmtMovimiento.setInt(1, partidaId);
                stmtMovimiento.setString(2, movimiento);
                stmtMovimiento.setBoolean(3, turnoBlancas);
                stmtMovimiento.executeUpdate();
                turnoBlancas = !turnoBlancas;
            }

            System.out.println("Partida guardada en la base de datos con ID: " + partidaId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

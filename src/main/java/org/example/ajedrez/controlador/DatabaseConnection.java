package org.example.ajedrez.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String USER = "root"; // Usuario de la BD.
    private static final String PASSWORD = ""; // Contraseña del usuario especificado arriba.
    private static final String URL = "jdbc:mariadb://localhost:3306/chess"; // URL de MariaDB.
    private static Connection conexion = null; // Objeto conexión para la BD.

    /**
     * Constructor privado que se conecta al SGBD usando los datos proporcionados.
     */
    private DatabaseConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ha ocurrido un error accediendo a la base de datos: " + e.getMessage());
            // Se puede reintentar la conexión en el método static getConnection()
        }
    }

    /**
     * Genera una conexión al SGDB.
     *
     * @return Connection - Objeto que permite la conexión a la base de datos.
     */
    public static synchronized Connection getConnection() {
        if (conexion == null) {
            inicializarBaseDeDatos();
            new DatabaseConnection(); // Se llama al constructor para iniciar la conexión
        }
        return conexion;
    }

    // ...

    /**
     * Método que cierra la conexión al SGBD.
     *
     * @throws SQLException Excepción lanzada en caso de que no se pueda cerrar la
     *                      conexión.
     */
    public static void close() throws SQLException {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
            conexion = null; // Se establece como nula para evitar problemas en el siguiente llamado a
                             // getConnection()
        }
    }

    /**
     * Método que inicializa la base de datos.
     * 
     * @throws SQLException Excepción lanzada en caso de que no se pueda inicializar
     *                      la base de datos.
     */
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
                            correo VARCHAR(255) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL UNIQUE

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
                            movimiento VARCHAR(16300) NOT NULL,
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

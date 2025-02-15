package org.example.ajedrez.controlador;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DAO {
    public void insertarUsuario(Usuario usuario, String password) throws SQLException, SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));  // Hashea la contraseña

        String sql = "INSERT INTO usuarios (usuario, correo, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, hashedPassword);  // Guardamos el hash, no la contraseña en texto plano
            stmt.executeUpdate();
        }
    }public int insertarPartida(int idBlancas, int idNegras) {
        int idGenerado = -1;
        String query = "INSERT INTO partidas (jugador_blancas_id, jugador_negras_id) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idBlancas);
            stmt.setInt(2, idNegras);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    public static void insertarMovimiento(int partidaId, String movimiento) {
        String query = "INSERT INTO movimientos (partida_id, movimiento) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, partidaId);
            stmt.setString(2, movimiento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

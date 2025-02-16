package org.example.ajedrez.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase DAO (Data Access Object) que gestiona las operaciones de acceso a la
 * base de datos
 * para la aplicación de ajedrez. Proporciona métodos para insertar, actualizar
 * y consultar
 * datos relacionados con usuarios, partidas y movimientos.
 */
public class DAO {
    private static Connection conn;

    /**
     * Cierra la conexión a la base de datos si está abierta.
     *
     * @throws SQLException Si ocurre un error al cerrar la conexión.
     * @since 1.0
     */
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario El objeto Usuario que contiene los datos del usuario a
     *                insertar.
     * @since 1.0
     */
    public void insertarUsuario(Usuario usuario) {
        conn = DatabaseConnection.getConnection();
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(12)); // Hashea la contraseña

        String sql = "INSERT INTO usuarios (usuario, correo, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, hashedPassword); // Guardamos el hash, no la contraseña en texto plano
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserta una nueva partida en la base de datos.
     *
     * @param idBlancas El ID del jugador que juega con las piezas blancas.
     * @param idNegras  El ID del jugador que juega con las piezas negras.
     * @return El ID generado para la partida, o -1 si ocurre un error.
     * @since 1.0
     */
    public int insertarPartida(int idBlancas, int idNegras) {
        conn = DatabaseConnection.getConnection();
        int idGenerado = -1;
        String query = "INSERT INTO partidas (jugador_blancas_id, jugador_negras_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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

    /**
     * Busca el ID de una partida basándose en los IDs de los jugadores.
     *
     * @param idBlancas El ID del jugador que juega con las piezas blancas.
     * @param idNegras  El ID del jugador que juega con las piezas negras.
     * @return El ID de la partida si existe, o -1 si no se encuentra o hay un
     *         error.
     * @since 1.0
     */
    public int buscarPartidaPorJugadores(int idBlancas, int idNegras) {
        conn = DatabaseConnection.getConnection();
        int idPartida = -1;
        String query = "SELECT id FROM partidas WHERE jugador_blancas_id = ? AND jugador_negras_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idBlancas); // Establecer el ID del jugador con piezas blancas
            stmt.setInt(2, idNegras); // Establecer el ID del jugador con piezas negras

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idPartida = rs.getInt("id"); // Obtener el ID de la partida
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPartida; // Retornar el ID de la partida o -1 si no se encuentra
    }

    /**
     * Valida las credenciales de un usuario (nombre de usuario, correo y
     * contraseña).
     *
     * @param nombreUsuario El nombre de usuario a validar.
     * @param correo        El correo electrónico a validar.
     * @param contraseña    La contraseña a validar.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @since 1.0
     */
    public boolean validarUsuario(String nombreUsuario, String correo, String contraseña) {
        conn = DatabaseConnection.getConnection();
        String query = "SELECT password FROM usuarios WHERE usuario = ? AND correo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashAlmacenado = rs.getString("password");
                    return BCrypt.checkpw(contraseña, hashAlmacenado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza la contraseña de un usuario en la base de datos.
     *
     * @param nombreUsuario    El nombre de usuario del usuario cuya contraseña se
     *                         actualizará.
     * @param nuevaContrasenha La nueva contraseña a establecer.
     * @return true si la contraseña se actualizó correctamente, false en caso
     *         contrario.
     * @since 1.0
     */
    public boolean actualizarContrasenhaUsuario(String nombreUsuario, String nuevaContrasenha) {
        // Hashear la nueva contraseña
        String hashedNuevaContrasenha = BCrypt.hashpw(nuevaContrasenha, BCrypt.gensalt(12));

        // Query para actualizar la contraseña
        String query = "UPDATE usuarios SET password = ? WHERE usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, hashedNuevaContrasenha);
            stmt.setString(2, nombreUsuario);

            int filasAfectadas = stmt.executeUpdate();

            // Si se actualizó al menos una fila, la operación fue exitosa
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el ID de un usuario basándose en su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del usuario.
     * @return El ID del usuario si existe, o -1 si no se encuentra o hay un error.
     * @since 1.0
     */
    public int obtenerIdUsuarioPorUsername(String nombreUsuario) {
        conn = DatabaseConnection.getConnection();
        String query = "SELECT id FROM usuarios WHERE usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario); // Buscar por nombre de usuario

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Devolver el ID del usuario
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retornar -1 si no se encuentra el usuario o hay un error
    }

    /**
     * Inserta un movimiento en la base de datos asociado a una partida.
     *
     * @param partidaId  El ID de la partida a la que pertenece el movimiento.
     * @param movimiento El movimiento a insertar.
     * @return true si el movimiento se insertó correctamente, false en caso
     * contrario.
     * @since 1.0
     */
    public void insertarMovimiento(int partidaId, String movimiento) {
        conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO movimientos (partida_id, movimiento) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, partidaId);
            stmt.setString(2, movimiento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
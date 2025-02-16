package org.example.ajedrez.controlador;

/**
 * Esta clase representa el contexto de la aplicación, almacenando el usuario actual,
 * el último panel cargado y el idioma seleccionado.
 *
 * @author Lucas V. (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ContextoApp {
    private static String idioma; // Guarda el idioma seleccionado.
    private static boolean jugador2; // Indica si hay un segundo jugador.
    private static int idUsuario1; // ID del primer usuario.
    private static int idUsuario2; // ID del segundo usuario.

    /**
     * Obtiene el idioma seleccionado actualmente.
     *
     * @return El idioma seleccionado.
     * @since 1.0
     */
    public static String getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma seleccionado.
     *
     * @param idioma El idioma a establecer.
     * @since 1.0
     */
    public static void setIdioma(String idioma) {
        ContextoApp.idioma = idioma;
    }

    /**
     * Obtiene el ID del primer usuario.
     *
     * @return El ID del primer usuario.
     * @since 1.0
     */
    public static int getIdUsuario1() {
        return idUsuario1;
    }

    /**
     * Establece el ID del primer usuario.
     *
     * @param idUsuario1 El ID del primer usuario a establecer.
     * @since 1.0
     */
    public static void setIdUsuario1(int idUsuario1) {
        ContextoApp.idUsuario1 = idUsuario1;
    }

    /**
     * Obtiene el ID del segundo usuario.
     *
     * @return El ID del segundo usuario.
     * @since 1.0
     */
    public static int getIdUsuario2() {
        return idUsuario2;
    }

    /**
     * Establece el ID del segundo usuario.
     *
     * @param idUsuario2 El ID del segundo usuario a establecer.
     * @since 1.0
     */
    public static void setIdUsuario2(int idUsuario2) {
        ContextoApp.idUsuario2 = idUsuario2;
    }

    /**
     * Verifica si hay un segundo jugador en la partida.
     *
     * @return `true` si hay un segundo jugador, `false` en caso contrario.
     * @since 1.0
     */
    public static boolean isJugador2() {
        return jugador2;
    }

    /**
     * Establece si hay un segundo jugador en la partida.
     *
     * @param jugador2Val `true` si hay un segundo jugador, `false` en caso contrario.
     * @since 1.0
     */
    public static void setJugador2(boolean jugador2Val) {
        jugador2 = jugador2Val;
    }
}
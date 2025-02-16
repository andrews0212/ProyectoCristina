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
    private static boolean jugador2;
    private static int idUsuario1;
    private static int idUsuario2;

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

    public static int getIdUsuario1() {
        return idUsuario1;
    }

    public static void setIdUsuario1(int idUsuario1) {
        ContextoApp.idUsuario1 = idUsuario1;
    }

    public static int getIdUsuario2() {
        return idUsuario2;
    }

    public static void setIdUsuario2(int idUsuario2) {
        ContextoApp.idUsuario2 = idUsuario2;
    }

    public static boolean isJugador2() {
        return jugador2;
    }

    public static void setJugador2(boolean jugador2Val) {
        jugador2 = jugador2Val;
    }
}
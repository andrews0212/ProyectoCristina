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
}
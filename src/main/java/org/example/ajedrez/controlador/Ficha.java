package org.example.ajedrez.controlador;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Representa una ficha de ajedrez en el tablero con su imagen, nombre, color y posición.
 * Esta clase también permite obtener la posición en notación ajedrecística y crear fichas
 * a partir de un panel de la interfaz de usuario.
 *
 * @author Andrews Dos Ramos
 */
public class Ficha {

    private ImageView imagen;
    private String nombre;
    private String color;
    private int fila;
    private int columna;
    private String posicionAjedrez;

    /**
     * Crea una nueva ficha de ajedrez.
     *
     * @param imagen   La imagen de la ficha representada por un ImageView.
     * @param nombre   El nombre de la ficha (e.g., "Reina", "Caballo").
     * @param color    El color de la ficha (e.g., "blanco" o "negro").
     * @param fila     La fila de la ficha en el tablero.
     * @param columna  La columna de la ficha en el tablero.
     */
    public Ficha(ImageView imagen, String nombre, String color, int fila, int columna) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.color = color;
        this.fila = fila;
        this.columna = columna;
        this.posicionAjedrez = convertirAjedrez(fila, columna); // Se inicializa la posición en ajedrez
    }

    // Getters y Setters

    /**
     * Obtiene la imagen de la ficha.
     *
     * @return La imagen de la ficha.
     */
    public ImageView getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen de la ficha.
     *
     * @param imagen La nueva imagen de la ficha.
     */
    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el nombre de la ficha.
     *
     * @return El nombre de la ficha.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la ficha.
     *
     * @param nombre El nuevo nombre de la ficha.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el color de la ficha.
     *
     * @return El color de la ficha.
     */
    public String getColor() {
        return color;
    }

    /**
     * Establece el color de la ficha.
     *
     * @param color El nuevo color de la ficha.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Obtiene la fila de la ficha en el tablero.
     *
     * @return La fila de la ficha.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Establece la fila de la ficha en el tablero.
     *
     * @param fila La nueva fila de la ficha.
     */
    public void setFila(int fila) {
        this.fila = fila;
        this.posicionAjedrez = convertirAjedrez(fila, columna); // Actualiza la posición en ajedrez
    }

    /**
     * Obtiene la columna de la ficha en el tablero.
     *
     * @return La columna de la ficha.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Establece la columna de la ficha en el tablero.
     *
     * @param columna La nueva columna de la ficha.
     */
    public void setColumna(int columna) {
        this.columna = columna;
        this.posicionAjedrez = convertirAjedrez(fila, columna); // Actualiza la posición en ajedrez
    }

    /**
     * Obtiene la posición de la ficha en notación ajedrecística.
     *
     * @return La posición de la ficha en notación ajedrecística (e.g., "a1", "e4").
     */
    public String getPosicionAjedrez() {
        return posicionAjedrez;
    }

    /**
     * Establece la posición de la ficha directamente en notación ajedrecística.
     *
     * @param posicionAjedrez La nueva posición en notación ajedrecística.
     */
    public void setPosicionAjedrez(String posicionAjedrez) {
        this.posicionAjedrez = posicionAjedrez;
        if (posicionAjedrez != null && posicionAjedrez.length() >= 2) {
            char columnaLetra = posicionAjedrez.charAt(0);
            int filaNumero = Character.getNumericValue(posicionAjedrez.charAt(1));
            this.columna = columnaLetra - 'a';
            this.fila = 8 - filaNumero; // La fila se invierte para el sistema de coordenadas del tablero
        }
    }

    /**
     * Convierte las coordenadas internas (fila, columna) a notación ajedrecística.
     *
     * @param fila    La fila de la ficha.
     * @param columna La columna de la ficha.
     * @return La posición en notación ajedrecística.
     */
    private String convertirAjedrez(int fila, int columna) {
        char columnaLetra = (char) ('a' + columna);
        int filaNumero = 8 - fila;
        return "" + columnaLetra + filaNumero;
    }

    /**
     * Crea un objeto Ficha a partir de una celda (Pane) que contenga la pieza.
     * Se busca el ImageView dentro del Pane y se deduce el color y el nombre a partir
     * de la URL de la imagen y/o de la clase de estilo del Pane.
     *
     * @param casilla El Pane que contiene la pieza de ajedrez.
     * @return Una nueva Ficha creada desde la casilla, o null si no hay pieza en la casilla.
     */
    public static Ficha crearFichaDesdePanel(Pane casilla) {
        ImageView imagen = null;

        // Buscar el ImageView entre los hijos del Pane
        for (var node : casilla.getChildren()) {
            if (node instanceof ImageView) {
                imagen = (ImageView) node;
                break;
            }
        }
        if (imagen == null) {
            return null; // No hay pieza en esta casilla
        }

        // Determinar el color a partir de la clase de estilo del Pane
        String paneColor = "desconocido";
        if (casilla.getStyleClass().contains("white")) {
            paneColor = "blanco";
        } else if (casilla.getStyleClass().contains("black")) {
            paneColor = "negro";
        }

        // Determinar el nombre de la pieza a partir de la URL de la imagen
        String nombreFicha = "Ficha Desconocida";
        Image img = imagen.getImage();
        if (img != null && img.getUrl() != null && !img.getUrl().isEmpty()) {
            String url = img.getUrl();
            int lastSlash = url.lastIndexOf("/");
            String fileName = lastSlash >= 0 ? url.substring(lastSlash + 1) : url;

            if (fileName.startsWith("w")) {
                paneColor = "blanco";
            } else if (fileName.startsWith("b")) {
                paneColor = "negro";
            }

            if (fileName.length() >= 2) {
                char pieceChar = fileName.charAt(1);
                switch(pieceChar) {
                    case 'R':
                        nombreFicha = "Torre";
                        break;
                    case 'B':
                        nombreFicha = "Alfil";
                        break;
                    case 'N':
                        nombreFicha = "Caballo";
                        break;
                    case 'Q':
                        nombreFicha = "Reina";
                        break;
                    case 'K':
                        nombreFicha = "Rey";
                        break;
                    case 'P':
                        nombreFicha = "Peón";
                        break;
                    default:
                        nombreFicha = "Ficha Desconocida";
                        break;
                }
            }
        }

        // Obtener los índices de fila y columna con valores predeterminados si son null
        Integer fila = GridPane.getRowIndex(casilla);
        Integer columna = GridPane.getColumnIndex(casilla);

        if (fila == null) {
            fila = 0; // Valor predeterminado
        }
        if (columna == null) {
            columna = 0; // Valor predeterminado
        }

        return new Ficha(imagen, nombreFicha, paneColor, fila, columna);
    }

    @Override
    public String toString() {
        return "Ficha{" +
                "nombre='" + nombre + '\'' +
                ", color='" + color + '\'' +
                ", posicion='" + posicionAjedrez + '\'' +
                '}';
    }
}

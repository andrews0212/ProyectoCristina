package org.example.ajedrez;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Ficha {

    private ImageView imagen;
    private String nombre;
    private String color;
    private int fila;
    private int columna;
    private String posicionAjedrez;

    public Ficha(ImageView imagen, String nombre, String color, int fila, int columna) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.color = color;
        this.fila = fila;
        this.columna = columna;
        this.posicionAjedrez = convertirAjedrez(fila, columna); // Se inicializa la posición en ajedrez
    }

    // Getters y Setters
    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
        // Actualizar la posición de la ficha cada vez que se cambia la fila
        this.posicionAjedrez = convertirAjedrez(fila, columna);
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
        // Actualizar la posición de la ficha cada vez que se cambia la columna
        this.posicionAjedrez = convertirAjedrez(fila, columna);
    }

    public String getPosicionAjedrez() {
        return posicionAjedrez;
    }

    // Método para actualizar la posición en ajedrez directamente
    public void setPosicionAjedrez(String posicionAjedrez) {
        this.posicionAjedrez = posicionAjedrez;
        // Extraer la fila y columna de la notación ajedrecística si es necesario
        // Esto puede ser útil si prefieres establecer la posición ajedrecística en lugar de las coordenadas
        if (posicionAjedrez != null && posicionAjedrez.length() >= 2) {
            char columnaLetra = posicionAjedrez.charAt(0);
            int filaNumero = Character.getNumericValue(posicionAjedrez.charAt(1));

            // Convertir la notación a coordenadas de fila y columna
            this.columna = columnaLetra - 'a';
            this.fila = 8 - filaNumero; // La fila se invierte para el sistema de coordenadas del tablero
        }
    }

    // Método para convertir coordenadas (fila, columna) a notación ajedrecística
    private String convertirAjedrez(int fila, int columna) {
        char columnaLetra = (char) ('a' + columna);
        int filaNumero = 8 - fila;
        return "" + columnaLetra + filaNumero;
    }

    /**
     * Crea un objeto Ficha a partir de una celda (Pane) que contenga la pieza.
     * Se busca el ImageView dentro del Pane y se deduce el color y el nombre a partir
     * de la URL de la imagen y/o de la clase de estilo del Pane.
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
            // Extraer el nombre del archivo (por ejemplo "wR.png" o "bP.png")
            int lastSlash = url.lastIndexOf("/");
            String fileName = lastSlash >= 0 ? url.substring(lastSlash + 1) : url;

            // Actualiza el color según el prefijo del nombre del archivo
            if (fileName.startsWith("w")) {
                paneColor = "blanco";
            } else if (fileName.startsWith("b")) {
                paneColor = "negro";
            }

            // La segunda letra indica el tipo de pieza
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

        // Crear una nueva ficha con la información de la casilla
        return new Ficha(imagen, nombreFicha, paneColor, GridPane.getRowIndex(casilla), GridPane.getColumnIndex(casilla));
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

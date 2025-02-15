package org.example.ajedrez.controlador;

import org.example.ajedrez.modelo.Casilla;
import org.example.ajedrez.modelo.Tablero;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ControladorTablero {

    @FXML
    private GridPane gridPanel;

    private Tablero tablero;
    private ImageView piezaSeleccionada;
    private Ficha fichaSeleccionada;
    private boolean turnoBlancas = true; // true: blancas, false: negras

    @FXML
    private Label labelMovimiento;
    @FXML
    private GridPane cementerioNegras;
    @FXML
    private GridPane cementerioBlancas;

    public void initialize() {
        asignarEventosAPiezas();
        asignarEventosACasillas();
        tablero = new Tablero();
    }

    private void asignarEventosAPiezas() {
        for (Node nodo : gridPanel.getChildren()) {
            if (nodo instanceof Pane) {
                Pane casilla = (Pane) nodo;
                for (Node child : casilla.getChildren()) {
                    if (child instanceof ImageView) {
                        ImageView pieza = (ImageView) child;
                        pieza.setOnMouseClicked(event -> {
                            // Validar turno
                            boolean esBlanca = pieza.getImage().getUrl().contains("w");
                            if (esBlanca != turnoBlancas) {
                                System.out.println("No es tu turno.");
                                return;
                            }

                            if (piezaSeleccionada != null) {
                                piezaSeleccionada.setStyle(null);
                            }

                            piezaSeleccionada = pieza;
                            fichaSeleccionada = Ficha.crearFichaDesdePanel((Pane) pieza.getParent());
                            System.out.println("Ficha seleccionada: " + fichaSeleccionada);
                            piezaSeleccionada.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0.5, 0, 0);");
                            event.consume();
                        });
                    }
                }
            }
        }
    }

    private void asignarEventosACasillas() {
        for (Node nodo : gridPanel.getChildren()) {
            if (nodo instanceof Pane) {
                Pane casilla = (Pane) nodo;
                casilla.setOnMouseClicked(event -> {
                    if (piezaSeleccionada != null && fichaSeleccionada != null) {
                        Integer filaDestino = GridPane.getRowIndex(casilla);
                        Integer columnaDestino = GridPane.getColumnIndex(casilla);
                        if (filaDestino == null) filaDestino = 0;
                        if (columnaDestino == null) columnaDestino = 0;

                        if (!tablero.mover(
                                new Casilla(fichaSeleccionada.getColumna() + 1, 9 - fichaSeleccionada.getFila()),
                                new Casilla(columnaDestino + 1, 9 - filaDestino))) {
                            System.out.println("Movimiento inválido para " + fichaSeleccionada.getNombre());
                            return;
                        }

                        if (!casilla.getChildren().isEmpty()) {
                            Node piezaEnCasilla = casilla.getChildren().get(0);
                            if (piezaEnCasilla instanceof ImageView) {
                                moverFichaCementerio((ImageView) piezaEnCasilla);
                                casilla.getChildren().remove(piezaEnCasilla);
                            }
                        }

                        String movimiento = fichaSeleccionada.getNombre() + ": "
                                + fichaSeleccionada.getPosicionAjedrez() + " a "
                                + convertirAjedrez(filaDestino, columnaDestino);
                        System.out.println(movimiento);
                        if (labelMovimiento != null) {
                            labelMovimiento.setText(movimiento);
                        }

                        Pane origen = (Pane) piezaSeleccionada.getParent();
                        if (origen != null) {
                            origen.getChildren().remove(piezaSeleccionada);
                        }
                        casilla.getChildren().add(piezaSeleccionada);

                        fichaSeleccionada.setFila(filaDestino);
                        fichaSeleccionada.setColumna(columnaDestino);
                        fichaSeleccionada.setPosicionAjedrez(convertirAjedrez(filaDestino, columnaDestino));

                        piezaSeleccionada.setStyle(null);
                        piezaSeleccionada = null;

                        turnoBlancas = !turnoBlancas; // Cambiar turno
                    }
                });
            }
        }
    }

    private void moverFichaCementerio(ImageView piezaEnCasilla) {
        String url = piezaEnCasilla.getImage().getUrl();
        boolean esBlanca = url.contains("w");
        GridPane cementerio = esBlanca ? cementerioBlancas : cementerioNegras;

        for (Node nodo : cementerio.getChildren()) {
            if (nodo instanceof Pane) {
                Pane casilla = (Pane) nodo;
                if (casilla.getChildren().isEmpty()) {
                    casilla.getChildren().add(piezaEnCasilla);
                    break;
                }
            }
        }
    }

    private String convertirAjedrez(int fila, int columna) {
        char columnaLetra = (char) ('a' + columna);
        int filaNumero = 8 - fila;
        return "" + columnaLetra + filaNumero;
    }
}
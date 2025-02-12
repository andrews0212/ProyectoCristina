package org.example.ajedrez.controlador;

import org.example.ajedrez.modelo.Casilla;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.ajedrez.modelo.Tablero;


public class ControladorTablero {

    @FXML
    private GridPane gridPanel;

    private Tablero tablero;
    private ImageView piezaSeleccionada;
    private Ficha fichaSeleccionada;

    @FXML
    private Label labelMovimiento;

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
                            // Quitar el efecto de la pieza previamente seleccionada
                            if (piezaSeleccionada != null) {
                                piezaSeleccionada.setStyle(null);
                            }

                            // Asignar la nueva pieza seleccionada
                            piezaSeleccionada = pieza;
                            fichaSeleccionada = Ficha.crearFichaDesdePanel((Pane) pieza.getParent());
                            System.out.println("Ficha seleccionada: " + fichaSeleccionada);

                            // Aplicar efecto visual solo a la nueva pieza
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

                        // Si los valores son null, significa que están en la posición 0
                        if (filaDestino == null) filaDestino = 0;
                        if (columnaDestino == null) columnaDestino = 0;

                        // Validar movimiento con lógica del tablero
                        if (!tablero.mover(new Casilla(fichaSeleccionada.getColumna() + 1, 9 - fichaSeleccionada.getFila()),
                                new Casilla(columnaDestino + 1, 9 - filaDestino))) {
                            System.out.println("Movimiento inválido para " + fichaSeleccionada.getNombre());
                            return;
                        }

                        // Comprobar si hay una pieza en la casilla de destino (comer pieza)
                        if (!casilla.getChildren().isEmpty()) {
                            Node piezaEnCasilla = casilla.getChildren().get(0);
                            if (piezaEnCasilla instanceof ImageView) {
                                casilla.getChildren().remove(piezaEnCasilla); // Comer la pieza
                            }
                        }

                        // Crear un String del movimiento
                        String movimiento = fichaSeleccionada.getNombre() + ": "
                                + fichaSeleccionada.getPosicionAjedrez() + " a "
                                + convertirAjedrez(filaDestino, columnaDestino);

                        // Imprimir y actualizar el Label
                        System.out.println(movimiento);
                        if (labelMovimiento != null) {
                            labelMovimiento.setText(movimiento);
                        }

                        // Mover la pieza en el GridPane
                        Pane origen = (Pane) piezaSeleccionada.getParent();
                        if (origen != null) {
                            origen.getChildren().remove(piezaSeleccionada);
                        }
                        casilla.getChildren().add(piezaSeleccionada);

                        // Actualizar la posición de la pieza
                        fichaSeleccionada.setFila(filaDestino);
                        fichaSeleccionada.setColumna(columnaDestino);
                        fichaSeleccionada.setPosicionAjedrez(convertirAjedrez(filaDestino, columnaDestino));

                        // Resetear la selección
                        piezaSeleccionada.setStyle(null);
                        piezaSeleccionada = null;
                    }
                });
            }
        }
    }


    private String convertirAjedrez(int fila, int columna) {
        char columnaLetra = (char) ('a' + columna);
        int filaNumero = 8 - fila;
        return "" + columnaLetra + filaNumero;
    }
}



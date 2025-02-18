package org.example.ajedrez.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.ajedrez.App;
import org.example.ajedrez.modelo.Casilla;
import org.example.ajedrez.modelo.Movimiento;
import org.example.ajedrez.modelo.Rey;
import org.example.ajedrez.modelo.Tablero;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Controlador para la interfaz gráfica del tablero de ajedrez.
 * Maneja la interacción con las piezas y las casillas, permitiendo realizar
 * movimientos de piezas en el tablero.
 * Además, gestiona el turno de las piezas, mostrando las jugadas realizadas y
 * moviendo las piezas al cementerio cuando se capturan.
 *
 * @author Andrews Dos ramos
 */
public class ControladorTablero {

    /**
     * El panel de grid que representa el tablero de ajedrez.
     */
    @FXML
    private GridPane gridPanel;

    /**
     * El tablero de ajedrez que contiene la lógica del juego.
     */
    private Tablero tablero;

    /**
     * La pieza actualmente seleccionada.
     */
    private ImageView piezaSeleccionada;

    /**
     * La ficha actualmente seleccionada, que contiene la información de la pieza en
     * el tablero.
     */
    private Ficha fichaSeleccionada;

    /**
     * El estado del turno, true para las blancas y false para las negras.
     */
    private boolean turnoBlancas = true;

    /**
     * Etiqueta para mostrar el movimiento realizado.
     */
    @FXML
    private Label labelMovimiento;

    /**
     * Panel que representa el cementerio de las piezas negras.
     */
    @FXML
    private GridPane cementerioNegras;

    /**
     * Panel que representa el cementerio de las piezas blancas.
     */
    @FXML
    private GridPane cementerioBlancas;
    private static boolean bot = true;
    private List<String> movimientos = new ArrayList<>();

    /**
     * DAO para la persistencia de las partidas.
     */
    private DAO dao;

    /**
     * Método de inicialización del controlador. Se encarga de asignar los eventos
     * de interacción con las piezas y casillas.
     */
    public static int id_userBlanco;
    public static int id_userNegro;

    public void initialize() {
        id_userBlanco = ContextoApp.getIdUsuario1();
        id_userNegro = ContextoApp.getIdUsuario2();
        asignarEventosAPiezas();
        asignarEventosACasillas();
        tablero = new Tablero();
        dao = new DAO();
        if (bot == true){
            Usuario usuario;
            if ((usuario = dao.buscar("bot")) == null){
                dao.insertarUsuario(new Usuario("bot", "12345" ,"bot@gmail.com"));
                usuario = dao.buscar("bot");
                id_userNegro = usuario.getIdUsuario();
            }else{
                id_userNegro = usuario.getIdUsuario();
            }

        }
        dao.insertarPartida(id_userBlanco, id_userNegro);
    }

    /**
     * Asigna los eventos de clic a las piezas en el tablero.
     * Al hacer clic en una pieza, se selecciona y resalta, permitiendo que el
     * jugador realice un movimiento.
     */
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

    /**
     * Asigna los eventos de clic a las casillas del tablero.
     * Al hacer clic en una casilla, se intenta mover la pieza seleccionada a esa
     * casilla, si el movimiento es válido.
     */
    private void asignarEventosACasillas() {
        for (Node nodo : gridPanel.getChildren()) {
            if (nodo instanceof Pane) {
                Pane casilla = (Pane) nodo;
                casilla.setOnMouseClicked(event -> {
                    if (piezaSeleccionada != null && fichaSeleccionada != null) {
                        Integer filaDestino = GridPane.getRowIndex(casilla);
                        Integer columnaDestino = GridPane.getColumnIndex(casilla);
                        if (filaDestino == null)
                            filaDestino = 0;
                        if (columnaDestino == null)
                            columnaDestino = 0;

                        // Validar y realizar el movimiento
                        if (!tablero.mover(
                                new Casilla(fichaSeleccionada.getColumna() + 1, 9 - fichaSeleccionada.getFila()),
                                new Casilla(columnaDestino + 1, 9 - filaDestino))) {
                            System.out.println("Movimiento inválido para " + fichaSeleccionada.getNombre());
                            return;
                        }

                        // Captura de pieza: moverla al cementerio
                        if (!casilla.getChildren().isEmpty()) {
                            Node piezaEnCasilla = casilla.getChildren().get(0);
                            if (piezaEnCasilla instanceof ImageView) {
                                moverFichaCementerio((ImageView) piezaEnCasilla);
                                casilla.getChildren().remove(piezaEnCasilla);
                            }
                        }

                        // Mostrar movimiento realizado
                        String movimiento = fichaSeleccionada.getNombre() + ": "
                                + fichaSeleccionada.getPosicionAjedrez() + " a "
                                + convertirAjedrez(filaDestino, columnaDestino);
                        System.out.println(movimiento);
                        movimientos.add(convertirAjedrez(filaDestino, columnaDestino));
                        if (labelMovimiento != null) {
                            labelMovimiento.setText(movimiento);
                        }

                        // Mover la pieza seleccionada al destino
                        Pane origen = (Pane) piezaSeleccionada.getParent();
                        if (origen != null) {
                            origen.getChildren().remove(piezaSeleccionada);
                        }
                        casilla.getChildren().add(piezaSeleccionada);

                        // Actualizar las coordenadas de la ficha
                        fichaSeleccionada.setFila(filaDestino);
                        fichaSeleccionada.setColumna(columnaDestino);
                        fichaSeleccionada.setPosicionAjedrez(convertirAjedrez(filaDestino, columnaDestino));

                        // Deseleccionar la pieza
                        piezaSeleccionada.setStyle(null);
                        piezaSeleccionada = null;
                        // Verificar jaque/jaque mate
                        // Suponiendo que puedes determinar el color de la pieza movida (por ejemplo, a
                        // partir del URL de la imagen):

                        try {
                            verificarJaqueYJaqueMate(fichaSeleccionada.getColor() == "blanco" ? false : true);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // Cambiar de turno
                        turnoBlancas = !turnoBlancas;
                        if (bot) {
                            try {
                                turnoBot();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        }
    }

    private void turnoBot() throws IOException {
        Movimiento movimientoBot = tablero.bot(true);
        Pane fin = null;
        Pane inicio = null;
        for (Node node : gridPanel.getChildren()) {
            //No se porque pero cuando getColumnIndex es 0 lo tranforma en null
            Integer col = (GridPane.getColumnIndex(node) != null) ? GridPane.getColumnIndex(node) : 0;
            if (col != null && GridPane.getRowIndex(node) != null
                    && col == movimientoBot.getInicio().x
                    && GridPane.getRowIndex(node) == movimientoBot.getInicio().y) {
                inicio = (Pane) node;
                System.out.println(movimientoBot.getInicio() + " " + movimientoBot.getObjetivo());
                piezaSeleccionada = (ImageView) inicio.getChildren().get(0);
                fichaSeleccionada = Ficha.crearFichaDesdePanel((Pane) piezaSeleccionada.getParent());
            }
            System.out.println(GridPane.getColumnIndex(node)+""+GridPane.getRowIndex(node)+" "+movimientoBot.getObjetivo().x+""+movimientoBot.getObjetivo().y);
            if (col != null && GridPane.getRowIndex(node) != null
                    && col == movimientoBot.getObjetivo().x
                    && GridPane.getRowIndex(node) == movimientoBot.getObjetivo().y) {
                fin = (Pane) node;
            }
        }
        // Captura de pieza: moverla al cementerio
        if (!fin.getChildren().isEmpty()) {
            Node piezaEnCasilla = fin.getChildren().get(0);
            if (piezaEnCasilla instanceof ImageView) {
                moverFichaCementerio((ImageView) piezaEnCasilla);
                fin.getChildren().remove(piezaEnCasilla);
            }
        }
        // Mostrar movimiento realizado
        String movimiento = fichaSeleccionada.getNombre() + ": "
                + fichaSeleccionada.getPosicionAjedrez() + " a "
                + convertirAjedrez(movimientoBot.getObjetivo().y, movimientoBot.getObjetivo().x);
        System.out.println(movimiento);
        movimientos.add(convertirAjedrez(movimientoBot.getObjetivo().y, movimientoBot.getObjetivo().x));
        imprimirMovimientos();
        if (labelMovimiento != null) {
            labelMovimiento.setText(movimiento);
        }

        // Mover la pieza seleccionada al destino
        inicio.getChildren().remove(piezaSeleccionada);
        fin.getChildren().add(piezaSeleccionada);

        // Verificar jaque/jaque mate: el bot juega con negras (colorMovimiento == true)
        verificarJaqueYJaqueMate(true);

        // Cambiar de turno
        turnoBlancas = !turnoBlancas;
    }

    /**
     * Mueve una pieza capturada al cementerio correspondiente.
     *
     * @param piezaEnCasilla La pieza que fue capturada.
     */
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

    /**
     * Convierte las coordenadas del tablero en notación de ajedrez (por ejemplo,
     * "e4").
     *
     * @param fila    La fila en el tablero (0-7).
     * @param columna La columna en el tablero (0-7).
     * @return La notación de ajedrez correspondiente (por ejemplo, "e4").
     */
    private String convertirAjedrez(int fila, int columna) {
        char columnaLetra = (char) ('a' + columna);
        int filaNumero = 8 - fila;
        return "" + columnaLetra + filaNumero;
    }

    private void imprimirMovimientos() {
        System.out.println("\n--- Historial de movimientos ---");
        for (String mov : movimientos) {
            System.out.print(mov + " "); // Se imprime en una sola línea
        }
    }

    private void guardarMovimientosEnBD() {
        String movimientosUnidos = String.join(" ", movimientos);
        dao.insertarMovimiento(dao.buscarPartidaPorJugadoresUltima(id_userBlanco,id_userNegro), movimientosUnidos);
        System.out.println("Movimientos guardados en la base de datos.");
    }

    private void verificarJaqueYJaqueMate(boolean colorMovimiento) throws IOException {
        // colorMovimiento: true si la pieza que se movió es negra, false si es blanca.
        Rey reyEnemigo = tablero.obtenerRey(!colorMovimiento);
        if (tablero.jaque(reyEnemigo)) {
            String colorReyEnemigo = (colorMovimiento) ? "blanco" : "negro";
            if (tablero.jaqueMate(reyEnemigo)) {

                mostrarAlerta("Jaque Mate", "El rey " + colorReyEnemigo + " está en Jaque Mate");
                guardarMovimientosEnBD();


                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/finPartida.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                ControladorFinPartida controladorTablero = fxmlLoader.getController();
                controladorTablero.idBlancas = id_userBlanco;
                controladorTablero.idNegras = id_userNegro;
                // Cerrar la ventana actual del tablero
                Stage ventanaTablero = (Stage) gridPanel.getScene().getWindow();
                ventanaTablero.close();
                Stage stage = new Stage();
                stage.setTitle("Ajedrez"); // Título de la ventana
                stage.getIcons().add(new Image(App.class.getResource("images/icono.png").toExternalForm()));
                stage.setScene(scene); // Establece la escena en el escenario
                stage.show(); // Muestra la ventana
            } else {
                mostrarAlerta("Jaque", "El rey " + colorReyEnemigo + " está en Jaque");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();

    }

    public static boolean isBot() {
        return bot;
    }

    public static void setBot(boolean botVal) {
        bot = botVal;
    }
}

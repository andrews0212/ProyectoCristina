package org.example.ajedrez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Clase principal de la aplicación de Ajedrez.
 * Esta clase extiende de {@link javafx.application.Application} y se encarga de iniciar la
 * interfaz gráfica de usuario cargando la escena inicial, asociando el archivo FXML y el
 * archivo de estilo CSS.
 * Además, contiene el método para establecer la raíz de la aplicación.
 *
 * @author Lucas Villa (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */
public class App extends Application {

    /**
     * Método estático para cambiar la raíz de la escena de la aplicación.
     * Actualmente no implementado.
     *
     * @param s Nombre del archivo FXML para cargar como nueva raíz de la escena.
     * @since 1.0
     */
    public static void setRoot(String s) {
        // Implementación pendiente para cambiar la raíz de la escena
    }

    /**
     * Método principal de la aplicación. Carga la interfaz inicial, que está definida en
     * el archivo FXML "tablero.fxml". También se asocia el archivo CSS "style_tablero.css"
     * para la personalización visual de la escena.
     *
     * @param stage El escenario (ventana) principal de la aplicación.
     * @throws IOException Si no se puede cargar el archivo FXML o el archivo CSS.
     * @since 1.0
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/tablero.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        scene.getStylesheets().add(getClass().getResource("css/style_tablero.css").toExternalForm());
        stage.setTitle("Hello!"); // Título de la ventana
        stage.setScene(scene); // Establece la escena en el escenario
        stage.show(); // Muestra la ventana
    }

    /**
     * Método principal que lanza la aplicación.
     * Este método invoca {@link #start(Stage)} para iniciar la interfaz gráfica.
     *
     * @param args Argumentos de la línea de comandos.
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(); // Lanza la aplicación
    }

}
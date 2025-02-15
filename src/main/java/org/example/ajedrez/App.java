package org.example.ajedrez;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.controlador.ContextoApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación de Ajedrez.
 * Esta clase extiende de {@link javafx.application.Application} y se encarga de
 * iniciar la
 * interfaz gráfica de usuario cargando la escena inicial, asociando el archivo
 * FXML y el
 * archivo de estilo CSS.
 * Además, contiene el método para establecer la raíz de la aplicación.
 *
 * @author Lucas Villa (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */
public class App extends Application {
    private static Scene scene;

    /**
     * Método estático para cambiar la raíz de la escena de la aplicación.
     *
     * @param fxml Nombre del archivo FXML para cargar como nueva raíz de la escena.
     * @throws IOException Si no se puede cargar el archivo FXML.
     * @since 1.0
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Método principal de la aplicación. Carga la interfaz inicial, que está
     * definida en
     * el archivo FXML "inicioSesionJ1.fxml". También se asocia el archivo CSS
     * "style_tablero.css"
     * para la personalización visual de la escena.
     *
     * @param stage El escenario (ventana) principal de la aplicación.
     * @throws IOException Si no se puede cargar el archivo FXML o el archivo CSS.
     * @since 1.0
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/inicioSesionJ1.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("css/style_tablero.css").toExternalForm());
        stage.setTitle("Ajedrez"); // Título de la ventana
        stage.setScene(scene); // Establece la escena en el escenario
        stage.show(); // Muestra la ventana
    }

    /**
     * Carga el archivo FXML usando FXMLLoader y el ResourceBundle para la localización.
     *
     * @param fxml El nombre del archivo FXML a cargar.
     * @return El objeto Parent cargado desde el archivo FXML.
     * @throws IOException Lanza una IOException si no se puede cargar el FXML.
     * @since 1.0
     */
    private static Parent loadFXML(String fxml) throws IOException {
        Locale localeDefecto = ContextoApp.getIdioma() == null ? Locale.getDefault()
                : Locale.of(ContextoApp.getIdioma());
        ResourceBundle bundle = ResourceBundle.getBundle("org.example.ajedrez.i18n.LB", localeDefecto);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
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
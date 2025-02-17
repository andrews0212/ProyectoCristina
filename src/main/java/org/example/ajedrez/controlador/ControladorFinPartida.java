package org.example.ajedrez.controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador para la vista de fin de partida. Gestiona la
 * interacción de la
 * interfaz de usuario, incluyendo la selección del idioma ylos eventos de reinicio de partida y de generación de reporte. Además, maneja la gestión
 * de los textos
 * localizados para los distintos idiomas disponibles en la aplicación.
 *
 * @author Lucas Villa
 * @version 1.0
 * @since 1.0
 */
public class ControladorFinPartida {

    // FXML y componentes de la interfaz de usuario
    @FXML
    private ComboBox<String> idioma;
    @FXML
    private Label lblTitulo, lblInfo, lblAyuda;
    @FXML
    private Button btnGenerarReporte, btnReiniciarPartida;
    @FXML
    private Tooltip ttComboBox, ttAyuda, ttGenerarReporte, ttReiniciarPartida;
    @FXML
    private VBox rootVBox;
    @FXML
    private BorderPane finPartida1, selJug2;

    private ResourceBundle bundle;
    private DAO dao;
    private GeneratorReportes generadorReportes;
    private Scene scene;

    public static int idBlancas;
    public static int idNegras;

    /**
     * Método llamado al iniciar la aplicación. Inicializa los elementos de la
     * interfaz de usuario,
     * configura el combobox de idiomas, establece el idioma por defecto y asigna
     * los atajos de teclado.
     *
     * @since 1.0
     */
    @FXML
    private void initialize() {
        generadorReportes = new GeneratorReportes();
        dao = new DAO();
        initComboBox();
        seleccionarIdioma();
        setTooltips();
    }

    /**
     * Inicializa el combobox de idiomas con las opciones disponibles y selecciona
     * el idioma por defecto
     * según la configuración regional del sistema.
     *
     * @since 1.0
     */
    public void initComboBox() {
        idioma.getItems().addAll("Español", "English");
        idioma.setValue(idioma.getValue() == null ? primeraLetraMayuscula(Locale.getDefault().getDisplayLanguage())
                : "Español");
    }

    /**
     * Cambia el idioma de la aplicación según el idioma seleccionado en el
     * combobox.
     * Carga un nuevo ResourceBundle y actualiza los textos de la interfaz.
     *
     * @param localeStr El código del idioma a cambiar (por ejemplo, "es" para
     *                  español, "en" para inglés).
     * @since 1.0
     */
    private void setLocale(String localeStr) {
        Locale locale = Locale.forLanguageTag(localeStr);
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("org.example.ajedrez.i18n.LB", locale);
        actualizarLabels();
        actualizarTooltips();
    }

    /**
     * Actualiza los textos de los labels y los campos de la interfaz de usuario
     * según el idioma seleccionado.
     *
     * @since 1.0
     */
    private void actualizarLabels() {
        lblTitulo.setText(bundle.getString("finPartida.lbl.titulo"));
        lblInfo.setText(bundle.getString("finPartida.lbl.info"));
        btnGenerarReporte.setText(bundle.getString("finPartida.btn.generarReporte"));
        btnReiniciarPartida.setText(bundle.getString("finPartida.btn.reiniciarPartida"));
        lblAyuda.setText(bundle.getString("ayuda"));
    }

    /**
     * Actualiza los tooltips de la interfaz de usuario según el idioma
     * seleccionado.
     *
     * @since 1.0
     */
    private void actualizarTooltips() {
        ttComboBox.setText(bundle.getString("tt.comboBox"));
        ttGenerarReporte.setText(bundle.getString("tt.btn.generarReporte"));
        ttReiniciarPartida.setText(bundle.getString("tt.btn.reiniciarPartida"));
        ttAyuda.setText(bundle.getString("tt.ayuda"));
    }

    /**
     * Asigna los tooltips a los componentes de la interfaz de usuario.
     *
     * @since 1.0
     */
    private void setTooltips() {
        idioma.setTooltip(ttComboBox);
        btnGenerarReporte.setTooltip(ttGenerarReporte);
        btnReiniciarPartida.setTooltip(ttReiniciarPartida);
        lblAyuda.setTooltip(ttAyuda);
    }

    /**
     * Método que maneja la selección de un idioma en el ComboBox.
     * Al seleccionar un idioma, se cambia el idioma de la interfaz de usuario.
     *
     * @since 1.0
     */
    @FXML
    private void seleccionarIdioma() {
        String idiomaSeleccionado = idioma.getValue();
        String localeStr;

        localeStr = switch (idiomaSeleccionado) {
            case "Español" -> "es";
            case "English" -> "en";
            default -> "es";
        };

        ContextoApp.setIdioma(localeStr);
        setLocale(localeStr);
    }

    /**
     * Muestra el manual de usuario.
     * 
     * @throws IOException Si ocurre un error al intentar cargar el manual.
     */
    public void showDocs() throws IOException {
        try {
            // Obtiene la ruta del archivo manualUsuario.html dentro de resources
            File file = new File("src/main/resources/org/example/ajedrez/html/manualUsuario.html");

            // Verifica si el archivo existe
            if (file.exists()) {
                // Abre el archivo en el navegador predeterminado
                Desktop.getDesktop().browse(file.toURI());
            } else {
                System.err.println("El archivo manualUsuario.html no fue encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un reporte de la partida.
     * 
     * @since 1.0
     */
    @FXML
    private void generarReporte() {
        generadorReportes.generarReporte(
                dao.buscarPartidaPorJugadores(ContextoApp.getIdUsuario1(), ContextoApp.getIdUsuario2()));
    }

    /**
     * Reinicia la partida.
     * 
     * @throws IOException Excepción que se lanza si ocurre un error al cargar la
     *                     vista.
     */
    @FXML
    private void reiniciarPartida() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/tablero.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 703);
        
        // Obtener el stage actual
        Stage stage = (Stage) btnGenerarReporte.getScene().getWindow();
        
        // Configurar el nuevo tamaño
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(703);
        stage.centerOnScreen();
    }

    /**
     * Convierte la primera letra de una cadena de texto a mayúscula, dejando el
     * resto en minúsculas.
     *
     * @param input El texto a convertir.
     * @return El texto con la primera letra en mayúscula y el resto en minúsculas.
     * @since 1.0
     */
    public static String primeraLetraMayuscula(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}

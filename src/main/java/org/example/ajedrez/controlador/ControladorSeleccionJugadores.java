package org.example.ajedrez.controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.App;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controlador para la vista de inicio de sesión del jugador 1. Gestiona la interacción de la
 * interfaz de usuario, incluyendo la selección del idioma, los eventos de inicio de sesión,
 * la visualización de los formularios de registro y recuperación de contraseña, y la configuración
 * de atajos de teclado. Además, maneja la validación del usuario y la gestión de los textos
 * localizados para los distintos idiomas disponibles en la aplicación.
 *
 * @author Lucas Villa
 * @version 1.0
 * @since 1.0
 */
public class ControladorSeleccionJugadores {

    // FXML y componentes de la interfaz de usuario
    @FXML private ComboBox<String> idioma;
    @FXML private Label lblTitulo, lblInfo, lbl1Jugador, lbl2Jugadores, lblAyuda;
    @FXML private Tooltip ttComboBox, ttAyuda;
    @FXML private VBox rootVBox;
    @FXML private Tooltip ttLbl1Jugador, ttLbl2Jugadores;
    @FXML private BorderPane selJug1, selJug2;

    private ResourceBundle bundle;

    /**
     * Método llamado al iniciar la aplicación. Inicializa los elementos de la interfaz de usuario,
     * configura el combobox de idiomas, establece el idioma por defecto y asigna los atajos de teclado.
     *
     * @since 1.0
     */
    @FXML
    private void initialize() {
        initComboBox();
        seleccionarIdioma();
        setAtajos();
        setTooltips();
    }

    /**
     * Inicializa el combobox de idiomas con las opciones disponibles y selecciona el idioma por defecto
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
     * Cambia el idioma de la aplicación según el idioma seleccionado en el combobox.
     * Carga un nuevo ResourceBundle y actualiza los textos de la interfaz.
     *
     * @param localeStr El código del idioma a cambiar (por ejemplo, "es" para español, "en" para inglés).
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
     * Actualiza los textos de los labels y los campos de la interfaz de usuario según el idioma seleccionado.
     *
     * @since 1.0
     */
    private void actualizarLabels() {
        lblTitulo.setText(bundle.getString("selJug.lbl.titulo"));
        lblInfo.setText(bundle.getString("selJug.lbl.info"));
        lbl1Jugador.setText(bundle.getString("selJug.lbl.1Jugador"));
        lbl2Jugadores.setText(bundle.getString("selJug.lbl.2Jugadores"));
        lblAyuda.setText(bundle.getString("ayuda"));
    }

    /**
     * Actualiza los tooltips de la interfaz de usuario según el idioma seleccionado.
     *
     * @since 1.0
     */
    private void actualizarTooltips() {
        ttComboBox.setText(bundle.getString("tt.comboBox"));
        ttLbl1Jugador.setText(bundle.getString("tt.selJug.lbl.1Jugador"));
        ttLbl2Jugadores.setText(bundle.getString("tt.selJug.lbl.2Jugadores"));
        ttAyuda.setText(bundle.getString("tt.ayuda"));
    }

    /**
     * Asigna los tooltips a los componentes de la interfaz de usuario.
     *
     * @since 1.0
     */
    private void setTooltips() {
        idioma.setTooltip(ttComboBox);
        lblTitulo.setTooltip(ttLbl1Jugador);
        lbl1Jugador.setTooltip(ttLbl1Jugador);
        lbl2Jugadores.setTooltip(ttLbl2Jugadores);
        lblInfo.setTooltip(ttAyuda);
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
     * Realiza el inicio de sesión como el jugador 1. Si la autenticación es exitosa, se redirige
     * al jugador 2. Si la validación falla, se muestra un mensaje de error.
     *
     * @throws IOException Si ocurre un error al intentar cargar la siguiente vista.
     * @since 1.0
     */
    @FXML
    private void loginJugador1() throws IOException {
        System.out.println("Se ha seleccionado 1 jugador");
        ContextoApp.setJugador2(false);
        ControladorTablero.setBot(true);
        App.setRoot("fxml/inicioSesionJ1");
     
    }
    /**
     * Realiza el inicio de sesión como el jugador 1. Si la autenticación es exitosa, se redirige
     * al jugador 2. Si la validación falla, se muestra un mensaje de error.
     *
     * @throws IOException Si ocurre un error al intentar cargar la siguiente vista.
     * @since 1.0
     */
    @FXML
    private void loginJugador2() throws IOException {
        System.out.println("Se han seleccionado 2 jugadores");
        ContextoApp.setJugador2(true);
        ControladorTablero.setBot(false);
        App.setRoot("fxml/inicioSesionJ1");
    }

    /**
     * Muestra el manual de usuario.
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
     * Convierte la primera letra de una cadena de texto a mayúscula, dejando el resto en minúsculas.
     *
     * @param input El texto a convertir.
     * @return El texto con la primera letra en mayúscula y el resto en minúsculas.
     * @since 1.0
     */
    public static String primeraLetraMayuscula(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Configura los atajos de teclado para acciones rápidas como iniciar sesión, registrarse y
     * recuperar la contraseña.
     *
     * @since 1.0
     */
    public void setAtajos() {

        KeyCombination kc1Jugador = new KeyCodeCombination(KeyCode.DIGIT1);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kc1Jugador.match(event)) {
                try {
                    loginJugador1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        KeyCombination kc2Jugadores = new KeyCodeCombination(KeyCode.DIGIT2);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kc2Jugadores.match(event)) {
                try {
                    loginJugador2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

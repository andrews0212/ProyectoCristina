package org.example.ajedrez.controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
public class ControladorInicioSesionJ1 {

    // FXML y componentes de la interfaz de usuario
    @FXML private ComboBox<String> idioma;
    @FXML private Label lblTitulo, lblValidacionUsuario, lblAyuda;
    @FXML private Button btnIniciarSesion;
    @FXML private Hyperlink hlRecuperarContrasenha, hlRegistro, hlMenuPrincipal;
    @FXML private TextField txtUsuario, txtEmail;
    @FXML private PasswordField txtContrasenha;
    @FXML private Tooltip ttComboBox, ttBtnIniciarSesion, ttHlRecuperarContrasenha, ttHlRegistro, ttTxtContrasenha, ttTxtUsuario, ttTxtEmail, ttHlMenuPrincipal, ttAyuda;
    @FXML HBox hbValidacionUsuario;
    @FXML private VBox rootVBox;

    private ResourceBundle bundle;
    private DAO dao;

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
        dao = new DAO();
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
        lblTitulo.setText(bundle.getString("loginJ1.lbl.titulo"));
        txtUsuario.setPromptText(bundle.getString("login.username"));
        txtEmail.setPromptText(bundle.getString("login.email"));
        txtContrasenha.setPromptText(bundle.getString("login.password"));
        hlRegistro.setText(bundle.getString("login.hl.registro"));
        btnIniciarSesion.setText(bundle.getString("login.btn.iniciarSesion"));
        hlRecuperarContrasenha.setText(bundle.getString("login.hl.recuperarContrasenha"));
        lblValidacionUsuario.setText(bundle.getString("login.usuarioInvalido"));
        hlMenuPrincipal.setText(bundle.getString("login.hl.menuPrincipal"));
        lblAyuda.setText(bundle.getString("ayuda"));
    }

    /**
     * Actualiza los tooltips de la interfaz de usuario según el idioma seleccionado.
     *
     * @since 1.0
     */
    private void actualizarTooltips() {
        ttComboBox.setText(bundle.getString("tt.comboBox"));
        ttTxtUsuario.setText(bundle.getString("tt.login.txt.usuario"));
        ttTxtEmail.setText(bundle.getString("tt.login.txt.email"));
        ttTxtContrasenha.setText(bundle.getString("tt.login.txt.contrasenha"));
        ttBtnIniciarSesion.setText(bundle.getString("tt.login.btn.iniciarSesion"));
        ttHlRegistro.setText(bundle.getString("tt.login.hl.registro"));
        ttHlRecuperarContrasenha.setText(bundle.getString("tt.login.hl.recuperarContrasenha"));
        ttHlMenuPrincipal.setText(bundle.getString("tt.login.hl.menuPrincipal"));
        ttAyuda.setText(bundle.getString("tt.ayuda"));
    }

    /**
     * Asigna los tooltips a los componentes de la interfaz de usuario.
     *
     * @since 1.0
     */
    private void setTooltips() {
        idioma.setTooltip(ttComboBox);
        txtUsuario.setTooltip(ttTxtUsuario);
        txtContrasenha.setTooltip(ttTxtContrasenha);
        btnIniciarSesion.setTooltip(ttBtnIniciarSesion);
        hlRegistro.setTooltip(ttHlRegistro);
        hlRecuperarContrasenha.setTooltip(ttHlRecuperarContrasenha);
        hlMenuPrincipal.setTooltip(ttHlMenuPrincipal);
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
     * Muestra el formulario de registro de usuario. Este formulario se carga en la vista
     * correspondiente para la creación de una nueva cuenta de usuario.
     *
     * @throws IOException Si ocurre un error al intentar cargar la vista.
     * @since 1.0
     */
    @FXML
    private void registro() throws IOException {
        App.setRoot("fxml/registroUsuario");
    }

    /**
     * Realiza el inicio de sesión como el jugador 1. Si la autenticación es exitosa, se redirige
     * al jugador 2. Si la validación falla, se muestra un mensaje de error.
     *
     * @throws IOException Si ocurre un error al intentar cargar la siguiente vista.
     * @throws SQLException 
     * @since 1.0
     */
    @FXML
    private void login() throws IOException, SQLException {
        if (dao.validarUsuario(txtUsuario.getText(), txtEmail.getText(), txtContrasenha.getText())) {
            ContextoApp.setIdUsuario1(dao.obtenerIdUsuarioPorUsername(txtUsuario.getText()));
            if (ContextoApp.isJugador2() == (false)) {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/tablero.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 703);
            
            // Obtener el stage actual
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            
            // Configurar el nuevo tamaño
            stage.setScene(scene);
            stage.setWidth(1200);
            stage.setHeight(703);
            stage.centerOnScreen();
            } else {
                App.setRoot("fxml/inicioSesionJ2");
            }
        } else {
            hbValidacionUsuario.setVisible(true);
        }
}

    /**
     * Muestra el formulario de recuperación de contraseña. Este formulario se carga para permitir
     * al usuario recuperar su contraseña olvidada.
     *
     * @throws IOException Si ocurre un error al intentar cargar la vista.
     * @since 1.0
     */
    @FXML
    private void recuperarContrasenha() throws IOException {
        App.setRoot("fxml/recuperacionContrasenha");
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
     * Vuelve al menú principal de la aplicación.
     *
     * @throws IOException Si ocurre un error al intentar cargar la vista del menú principal.
     * @since 1.0
     */
    @FXML
    public void volverMenuPrincipal() throws IOException {
        App.setRoot("fxml/seleccionJugadores");
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
     * Configura los atajos de teclado para acciones rápidas como iniciar sesión, registrarse y
     * recuperar la contraseña.
     *
     * @since 1.0
     */
    public void setAtajos() {
        btnIniciarSesion.setDefaultButton(true);

        KeyCombination kcRegistro = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcRegistro.match(event)) {
                try {
                    registro();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        KeyCombination kcRecuperacionContrasenha = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcRecuperacionContrasenha.match(event)) {
                try {
                    recuperarContrasenha();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        KeyCombination kcVolverMenuPrincipal = new KeyCodeCombination(KeyCode.ESCAPE);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcVolverMenuPrincipal.match(event)) {
                try {
                    volverMenuPrincipal();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

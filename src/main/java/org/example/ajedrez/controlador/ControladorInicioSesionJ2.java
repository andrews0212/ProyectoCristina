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
 * Controlador para la pantalla de inicio de sesión del jugador 2.
 * <p>
 * Esta clase maneja los eventos y acciones del formulario de inicio de sesión para el jugador 2.
 * Proporciona funcionalidad para verificar las credenciales del jugador 2, manejar la recuperación de contraseña
 * y otros eventos relacionados con la autenticación.
 * </p>
 *
 * @author: Lucas Villa (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ControladorInicioSesionJ2 {
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
     * Método llamado al iniciar la aplicación.
     *
     * @since 1.0
     */
    @FXML
    private void initialize() {
        // Inicializar el combobox y establecer el idioma predeterminado.
        initComboBox();
        seleccionarIdioma();
        setAtajos();
        setTooltips();
        dao = new DAO();
    }

    /**
     * Inicializa el combobox con los idiomas disponibles.
     *
     * @since 1.0
     */
    public void initComboBox() {
        idioma.getItems().addAll("Español", "English");
        idioma.setValue(idioma.getValue() == null ? primeraLetraMayuscula(Locale.getDefault().getDisplayLanguage())
                : "Español");
    }

    /**
     * Cambia el idioma de la aplicación.
     *
     * @param localeStr String con el idioma a cambiar.
     * @since 1.0
     */
    private void setLocale(String localeStr) {
        // Cambiar el idioma de la aplicación.
        Locale locale = Locale.forLanguageTag(localeStr);
        Locale.setDefault(locale);
        // // Cargar un nuevo bundle de recursos.
        bundle = ResourceBundle.getBundle("org.example.ajedrez.i18n.LB", locale);
        // Actualizar los elementos de la interfaz con el texto localizado.
        actualizarLabels();
        actualizarTooltips();
    }

    /**
     * Actualiza los textos de los elementos de la interfaz en función del idioma
     * seleccionado.
     *
     * @since 1.0
     */
    private void actualizarLabels() {
        lblTitulo.setText(bundle.getString("loginJ2.lbl.titulo"));
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
     * Actualiza los tooltips de los botones y campos de texto en función del idioma
     * seleccionado.
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
     * Añade tooltips a los botones y campos de texto.
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
        lblAyuda.setTooltip(ttAyuda);
    }

    /**
     * Selecciona el idioma en base al contenido del combobox.
     *
     * @since 1.0
     */
    @FXML
    private void seleccionarIdioma() {
        // Determinar el idioma seleccionado.
        String idiomaSeleccionado = idioma.getValue();
        String localeStr;

        // Devolver el idioma seleccionado.
        localeStr = switch (idiomaSeleccionado) {
            case "Español" -> "es";
            case "English" -> "en";
            default -> "es";
        };

        // Establecer el nuevo idioma.
        ContextoApp.setIdioma(localeStr);
        setLocale(localeStr); 
    }

    /**
     * Muestra el formulario de registro de usuario.
     *
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void registro() throws IOException {
        App.setRoot("fxml/registroUsuario");
    }

    /**
     * Inicia sesión como el jugador 2, y después, lanza el stage del tablero.
     *
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @throws SQLException 
     * @since 1.0
     */
        @FXML
    private void login() throws IOException, SQLException {
        if (dao.validarUsuario(txtUsuario.getText(), txtEmail.getText(), txtContrasenha.getText())) {
        ContextoApp.setIdUsuario2(dao.obtenerIdUsuarioPorUsername(txtUsuario.getText()));
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
            hbValidacionUsuario.setVisible(true);
        }
    }
    /**
     * Muestra el formulario de recuperación de contraseña.
     *
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void recuperarContrasenha() throws IOException {
        App.setRoot("fxml/recuperacionContrasenha");
    }

    /**
     * Devuelve la primera letra en mayúscula de un string.
     *
     * @param input String a convertir.
     * @return String con la primera letra en mayúscula.
     * @since 1.0
     */
    public static String primeraLetraMayuscula(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }


    /**
     * Vuelve al menú anterior.
     * 
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    public void volverMenuPrincipal() throws IOException {
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
     * Añade los atajos de teclado.
     *
     * @since 1.0
     */
    public void setAtajos() {
        // Añadir evento de teclado para iniciar sesión.
        btnIniciarSesion.setDefaultButton(true);

        // Añadir evento de teclado para registrarse.
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

        // Añadir evento de teclado para recuperar la contraseña.
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

        // Añadir evento de teclado para volver al menú principal.
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



package org.example.ajedrez.controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.App;

import javafx.fxml.FXML;
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

/**
 * Controlador para el formulario de registro de usuario.
 * <p>
 * Esta clase maneja la lógica de la pantalla de registro, permitiendo a los usuarios
 * registrarse con un nombre de usuario, correo electrónico y contraseña. También
 * incluye funciones para validar los campos de entrada, cambiar el idioma de la aplicación,
 * y configurar atajos de teclado.
 * </p>
 *
 * @author: Lucas Villa (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ControladorRecuperacionContrasenha {
    @FXML private ComboBox<String> idioma;
    @FXML private Label lblTitulo, lblValidacionContrasenha, lblContrasenhaCambiada, lblContrasenhaNoIdentica;
    @FXML private Button btnRecuperarContrasenha;
    @FXML private Hyperlink hlLogin, hlMenuPrincipal;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenha, txtContrasenhaRep;
    @FXML private Tooltip ttComboBox, ttBtnRecuperarContrasenha, ttHlLogin, ttTxtContrasenha, ttTxtUsuario, ttTxtContrasenhaRep;
    @FXML HBox hbContrasenhaInvalida, hbContrasenhaNoIdentica, hbContrasenhaCambiada;

    @FXML private VBox rootVBox;

    private ResourceBundle bundle;

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
        lblTitulo.setText(bundle.getString("recuperacionContrasenha.lbl.titulo"));
        txtUsuario.setPromptText(bundle.getString("login.username"));
        txtContrasenha.setPromptText(bundle.getString("login.password"));
        txtContrasenhaRep.setPromptText(bundle.getString("login.password"));
        hlLogin.setText(bundle.getString("login.btn.iniciarSesion"));
        btnRecuperarContrasenha.setText(bundle.getString("recuperacionContrasenha.btn.recuperarContrasenha"));
        lblValidacionContrasenha.setText(bundle.getString("recuperacionContrasenha.contrasenhaInvalida"));
        lblContrasenhaNoIdentica.setText(bundle.getString("recuperacionContrasenha.contrasenhaNoIdentica"));
        lblContrasenhaCambiada.setText(bundle.getString("recuperacionContrasenha.contrasenhaCambiada"));
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
        ttTxtContrasenha.setText(bundle.getString("tt.recuperacionContrasenha.txt.contrasenha"));
        ttTxtContrasenhaRep.setText(bundle.getString("tt.recuperacionContrasenha.txt.contrasenhaRep"));
        ttBtnRecuperarContrasenha.setText(bundle.getString("tt.recuperacionContrasenha.btn.recuperarContrasenha"));
        ttHlLogin.setText(bundle.getString("tt.recuperacionContrasenha.hl.login"));
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
        btnRecuperarContrasenha.setTooltip(ttBtnRecuperarContrasenha);
        hlLogin.setTooltip(ttHlLogin);
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
     * @throws IOException Exception lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void registro() throws IOException {
        // App.setRoot("fxml/registroUsuario");
    }

    /**
     * Inicia sesión como el jugador 1.
     *
     * @throws IOException Exception lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void login() throws IOException {
        App.setRoot("fxml/iniciarSesionJ1");
    }

    /**
     * Muestra el formulario de recuperación de contraseña.
     *
     * @throws IOException Exception lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void cambiarContrasenha() throws IOException {
        if (txtContrasenha.getText().matches("[A-z0-9]{6,}")) {
            if (txtContrasenha.getText().equals(txtContrasenhaRep.getText())) {
                hbContrasenhaCambiada.setVisible(true);
            } else {
                hbContrasenhaNoIdentica.setVisible(true);
            }
        } else {
                hbContrasenhaInvalida.setVisible(true);
        }
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
     * Añade los atajos de teclado.
     *
     * @since 1.0
     */
    public void setAtajos() {
        // Añadir evento de teclado para cambiar la contraseña.
        btnRecuperarContrasenha.setDefaultButton(true);

        // Añadir evento de teclado para iniciar sesión.
        KeyCombination kcLogin = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        rootVBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcLogin.match(event)) {
                try {
                    login();
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



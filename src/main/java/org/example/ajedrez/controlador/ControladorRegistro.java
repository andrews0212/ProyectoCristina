/*
 * @author: Lucas Villa (k4ts0v@protonmail.com)
 * @version 1.0
 * @since 1.0
 */

package org.example.ajedrez.controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.example.ajedrez.App;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class ControladorRegistro {
    @FXML private ComboBox<String> idioma;
    @FXML private Label lblTitulo, lblValidacionUsuario, lblValidacionEmail, lblValidacionContrasenha, lblRegistroExitoso;
    @FXML private Button btnRegistro;
    @FXML private Hyperlink hlLogin, hlMenuPrincipal;
    @FXML private TextField txtUsuario, txtEmail;
    @FXML private PasswordField txtContrasenha;
    @FXML private Tooltip ttComboBox, ttBtnIniciarSesion, ttHlRecuperarContrasenha, ttHlLogin, ttTxtContrasenha, ttTxtUsuario, ttTxtEmail, ttHlMenuPrincipal;
    @FXML HBox hbValidacionUsuario, hbValidacionEmail, hbValidacionContrasenha, hbRegistroExitoso;

    @FXML private VBox rootVBox;

    private ResourceBundle bundle;

    /**
     * Método llamado al iniciar la aplicación.
     *
     * @since 1.0
     */
    @FXML
    private void initialize() {
        initComboBox();
        seleccionarIdioma();
        setAtajos();
        setTooltips();
        validacionesPeriodicas();
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
        lblTitulo.setText(bundle.getString("login.hl.registro"));
        txtUsuario.setPromptText(bundle.getString("login.username"));
        txtEmail.setPromptText(bundle.getString("login.email"));
        txtContrasenha.setPromptText(bundle.getString("login.password"));
        hlLogin.setText(bundle.getString("login.btn.iniciarSesion"));
        btnRegistro.setText(bundle.getString("login.hl.registro"));
        lblValidacionUsuario.setText(bundle.getString("registro.validacionUsuario"));
        lblValidacionEmail.setText(bundle.getString("registro.validacionEmail"));
        lblValidacionContrasenha.setText(bundle.getString("registro.validacionContrasenha"));
        hlMenuPrincipal.setText(bundle.getString("login.hl.menuPrincipal"));
        lblRegistroExitoso.setText(bundle.getString("registro.lbl.registroExitoso"));
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
        ttHlLogin.setText(bundle.getString("tt.recuperacionContrasenha.hl.login"));
        ttHlMenuPrincipal.setText(bundle.getString("tt.login.hl.menuPrincipal"));
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
        btnRegistro.setTooltip(ttBtnIniciarSesion);
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
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void registro() throws IOException {
        if(!hbValidacionUsuario.isVisible() && !hbValidacionEmail.isVisible() && !hbValidacionContrasenha.isVisible()) {
            //gestionDB.anhadirUsuario(txtUsuario.getText(), txtContrasenha.getText());
            hbRegistroExitoso.setVisible(true);
        }
    }

    /**
     * Valida el nombre del usuario.
     * 
     * @param txt TextField con el nombre del usuario.
     * @since 1.0
     */
    public void validarNombre(TextField txt) {
        txt.setOnKeyReleased(evento -> {
            String contenidoActual = txt.getText();
            if (contenidoActual.matches("[a-zA-Z0-9]{6,}")) {
                hbValidacionUsuario.setVisible(false);
            } else {
                hbValidacionUsuario.setVisible(true);
            }
        });
    }

    /**
     * Valida el email del usuario.
     * 
     * @param txt TextField con el email del usuario.
     * @since 1.0
     */
    public void validarEmail(TextField txt) {
        txt.setOnKeyReleased(evento -> {
            String contenidoActual = txt.getText();
            if (contenidoActual.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                hbValidacionEmail.setVisible(false);
            } else {
                hbValidacionEmail.setVisible(true);
            }
        });
    }

    /**
     * Valida la contraseña del usuario.
     * 
     * @param pwd PasswordField con la contraseña del usuario.
     * @since 1.0
     */
    public void validarContrasenha(PasswordField pwd) {
        pwd.setOnKeyReleased(evento -> {
            String contenidoActual = pwd.getText();
            if (contenidoActual.matches("[a-zA-Z0-9]{6,}")) {
                hbValidacionContrasenha.setVisible(false);
            } else {
                hbValidacionContrasenha.setVisible(true);
            }
        });
    }

    /**
     * Validación periódica de los campos de texto.
     * Se realiza una validación periódica de los campos de texto para asegurarse de
     * que los campos estén completos y validos.
     * Se realiza mediante el uso de un Timeline que se ejecuta indefinidamente.
     * Cada campo de texto tiene un KeyFrame que se ejecuta cada dos segundos para
     * validar el contenido del campo.
     * Por último, se ejecuta el timeline para que se haga la validación periódica.
     *
     * @since 1.0
     */
    public void validacionesPeriodicas() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), event -> {
            validarNombre(txtUsuario);
            validarEmail(txtEmail);
            validarContrasenha(txtContrasenha);
        }));
        timeline.play();
    }

    /**
     * Inicia sesión como el jugador 1.
     *
     * @throws IOException Excepción lanzada si no se puede mostrar el formulario.
     * @since 1.0
     */
    @FXML
    private void login() throws IOException {
        App.setRoot("fxml/inicioSesionJ1");
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
     * Añade los atajos de teclado.
     *
     * @since 1.0
     */
    public void setAtajos() {
        // Añadir evento de teclado para registrarse.
        btnRegistro.setDefaultButton(true);

        // Añadir evento de teclado para registrarse.
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



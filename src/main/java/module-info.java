module org.example.ajedrez {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires javafx.graphics;

    opens org.example.ajedrez.controlador to javafx.fxml;
    exports org.example.ajedrez;
}
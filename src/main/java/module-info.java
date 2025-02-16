module org.example.ajedrez {


    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires java.sql;
    requires jbcrypt;

    requires java.desktop;
    requires net.sf.jasperreports.core;

    opens org.example.ajedrez.controlador to javafx.fxml;
    exports org.example.ajedrez;
}
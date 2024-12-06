module com.example.projectfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jasperreports;

    opens com.example.projectfx to javafx.fxml;
    exports com.example.projectfx;
}
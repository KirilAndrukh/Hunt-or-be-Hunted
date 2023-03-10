module com.example.javaprojectwithjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens com.example.javaprojectwithjfx to javafx.fxml;
    exports com.example.javaprojectwithjfx;
}
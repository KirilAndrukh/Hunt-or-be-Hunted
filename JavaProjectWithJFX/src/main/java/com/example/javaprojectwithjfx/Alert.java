package com.example.javaprojectwithjfx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    /**
     * Constructor of class Alert which create a window which name is set via title parameter to show a message using a text parameter
     * @param title - sets the title of the window
     * @param text - sets the texts that is shown in the window
     */
    public Alert(String title, String text) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(420);
        stage.setMinHeight(230);

        Label label = new Label();
        label.setText(text);

        Button button = new Button("OK");
        button.setOnAction(e -> stage.close());

        VBox layout = new VBox(8);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}

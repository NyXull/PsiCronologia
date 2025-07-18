package model.entities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TrocarCena {
    private static final String DEFAULT_CSS = "/css/fonts.css";

    public static void trocarCena(String fxmlPath, String cssPath, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(TrocarCena.class.getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(TrocarCena.class.getResource(DEFAULT_CSS)).toExternalForm());

        if (cssPath != null && !cssPath.isEmpty()) {
            scene.getStylesheets().add(Objects.requireNonNull(TrocarCena.class.getResource(cssPath)).toExternalForm());
        }

        stage.setScene(scene);
        stage.show();
    }
}

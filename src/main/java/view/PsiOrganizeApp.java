package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PsiOrganizeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login.fxml")));

            Scene sceneCadastro = new Scene(root, 1150, 600, javafx.scene.paint.Color.rgb(24, 32, 82));
            sceneCadastro.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/login.css")).toExternalForm());
            sceneCadastro.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/fonts.css")).toExternalForm());

            stage.setResizable(false);
            stage.setTitle("PsiOrganize");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/icon.png")).toExternalForm()));

            stage.setScene(sceneCadastro);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

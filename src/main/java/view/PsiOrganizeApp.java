package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class PsiOrganizeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro.fxml"));
        Scene scene = new Scene(loader.load(), 1150, 600, javafx.scene.paint.Color.rgb(24, 32, 82));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/cadastro.css")).toExternalForm());

        stage.setResizable(false);
        stage.setTitle("PsiOrganize");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/icon.png")).toExternalForm()));

        stage.setScene(scene);
        stage.show();
    }
}

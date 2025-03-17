package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.PURPLE);
        Image icon = new Image("C:\\Projetos-Intellij\\TCC\\PsiOrganize\\src\\main\\resources\\img\\java_logo_256-256.png");

        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("ESC para sair do modo \"tela cheia\"");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("esc"));
        stage.setTitle("PsiOrganize");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }
}
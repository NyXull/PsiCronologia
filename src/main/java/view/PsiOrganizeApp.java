package view;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PsiOrganizeApp extends Application {

    private static Scene mainScene;

    @Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
			ScrollPane scrollPane = loader.load();			
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("PsiOrganize");
			primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/icon_cerebro.png")).toExternalForm()));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static Scene getMainScene() {
    	return mainScene;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
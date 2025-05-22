package util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		// Aplica o CSS personalizado
        alert.getDialogPane().getStylesheets().add(
            Alerts.class.getResource("/css/alerts.css").toExternalForm()
        );

        // Define o ícone da janela
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(Alerts.class.getResourceAsStream("/img/icon_exclamacao.png"));
        stage.getIcons().add(icon);

        // Efeito de vibração suave
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),     e -> alert.getDialogPane().setTranslateX(0)),
            new KeyFrame(Duration.millis(50),    e -> alert.getDialogPane().setTranslateX(4)),
            new KeyFrame(Duration.millis(100),   e -> alert.getDialogPane().setTranslateX(-4)),
            new KeyFrame(Duration.millis(150),   e -> alert.getDialogPane().setTranslateX(2)),
            new KeyFrame(Duration.millis(200),   e -> alert.getDialogPane().setTranslateX(-2)),
            new KeyFrame(Duration.millis(250),   e -> alert.getDialogPane().setTranslateX(1)),
            new KeyFrame(Duration.millis(300),   e -> alert.getDialogPane().setTranslateX(0))
        );
        timeline.play();
        
		alert.show();
	}
}
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    public Pane rootPane;
    public Rectangle retanguloFundo;
    public Text cadastro;
    public Text nome;
    public Line linha1;
    public Text email;
    public Line linha2;
    public Text senha;
    public Line linha3;
    public ImageView imageView;
    public ImageView imageViewFundo;
    public Text psiOrganize;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void trocarParaSceneCadastro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/cadastro.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/cadastro.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void trocarParaSceneLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/login.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.Paciente;
import model.TrocarCena;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Paciente> listViewPacientes;

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        double radius = 75;
        Circle clip = new Circle(radius);
        clip.setCenterX(radius);
        clip.setCenterY(radius);

        imageView.setPreserveRatio(false);
        imageView.setFitWidth(radius * 2);
        imageView.setFitHeight(radius * 2);
        imageView.setClip(clip);
        imageView.setSmooth(true);

        ObservableList<Paciente> pacientes = FXCollections.observableArrayList();

        for (int i = 1; i <= 100; i++) {
            pacientes.add(new Paciente("Paciente " + i));
        }

        listViewPacientes.setItems(pacientes);
    }

}

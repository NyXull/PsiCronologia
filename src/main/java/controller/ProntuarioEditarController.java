package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.entities.Paciente;
import model.entities.TrocarCena;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProntuarioEditarController implements Initializable {

    @FXML
    private ListView<Paciente> listViewDatas;

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Paciente> pacientes = FXCollections.observableArrayList();

        for (int i = 1; i <= 50; i++) {
            pacientes.add(new Paciente("01/" + "01/" + "01"));
        }

        listViewDatas.setItems(pacientes);
    }
}

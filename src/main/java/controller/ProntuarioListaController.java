package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.entities.Paciente_EXCLUIR;
import model.entities.TrocarCena;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProntuarioListaController implements Initializable {

    @FXML
    private ListView<Paciente_EXCLUIR> listViewDatas;

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaProntuarioEditar(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/prontuario-editar.fxml", "/css/prontuario-editar.css", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Paciente_EXCLUIR> pacientes = FXCollections.observableArrayList();

        for (int i = 1; i <= 50; i++) {
            pacientes.add(new Paciente_EXCLUIR("01/" + "01/" + "01"));
        }

        listViewDatas.setItems(pacientes);
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.TrocarCena;

import java.io.IOException;

public class CadastroPacienteFinalizadoController {

    @FXML
    private void navegarParaCadastroPaciente(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/cadastro-paciente.fxml", "/css/cadastro-paciente.css", event);
    }

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaHomePaciente(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home-paciente.fxml", "/css/home-paciente.css", event);
    }

}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.TrocarCena;

import java.io.IOException;

public class CadastroPacienteController {

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaCadastroFinalizado(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/cadastro-paciente-finalizado.fxml", "/css/cadastro-paciente-finalizado.css",
                event);
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.TrocarCena;

import java.io.IOException;

public class CadastroController {

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }
}

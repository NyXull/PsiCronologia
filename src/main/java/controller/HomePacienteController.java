package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.TrocarCena;

import java.io.IOException;

public class HomePacienteController {

    @FXML
    private void navegarParaProntuarioLista(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css", event);
    }

    @FXML
    private void navegarParaFinanceiroPagamento(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css", event);
    }
}

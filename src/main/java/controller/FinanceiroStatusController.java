package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.TrocarCena;

import java.io.IOException;

public class FinanceiroStatusController {

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @FXML
    private void navegarParaFinanceiroPagamento(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css", event);
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.entities.TrocarCena;

import java.io.IOException;

public class FinanceiroPagamentoController {

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @FXML
    private void navegarParaFinanceiroStatus(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/financeiro-status.fxml", "/css/financeiro-status.css", event);
    }

    @FXML
    private void navegarParaRelatorio(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/relatorio.fxml", "/css/relatorio.css", event);
    }

    @FXML
    private void navegarParaBiblioteca(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/biblioteca.fxml", "/css/biblioteca.css", event);
    }
}

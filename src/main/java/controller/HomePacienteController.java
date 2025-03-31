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

    @FXML
    private void navegarParaRelatorio(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/relatorio.fxml", "/css/relatorio.css", event);
    }

    @FXML
    private void navegarParaAgenda(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/agenda-editar.fxml", "/css/agenda-editar.css", event);
    }

    @FXML
    private void navegarParaBiblioteca(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/biblioteca.fxml", "/css/biblioteca.css", event);
    }

}

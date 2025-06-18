package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import util.Alerts;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    @FXML
    public TextField textFieldNomeDocumento;

    @FXML
    public ComboBox<String> comboBoxModelo;

    @FXML
    private VBox vBox1Relatorios;

    @FXML
    private VBox vBox2Relatorios;

    @FXML
    private HBox hBoxPaiRelatorios;

    @FXML
    private Button btNomeDoPaciente;

    @FXML
    private Button btProntuario;

    @FXML
    private Button btAgenda;

    @FXML
    private Button btFinanceiro;

    private ObservableList<String> modelos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox1Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.25));
        vBox2Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.75));

        exibirNomePaciente();

        comboBoxModelo.setItems(modelos);
    }

    @FXML
    private void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    private void onBtNomeDoPacienteAction() {
        ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
    }

    @FXML
    private void onBtProntuarioAction() {
        ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
    }

    @FXML
    private void onBtAgendaAction() {
        ViewLoader.loadView("/fxml/agenda-editar.fxml", "/css/agenda-editar.css");
    }

    @FXML
    private void onBtFinanceiroAction() {
        ViewLoader.loadView("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css");
    }

    @FXML
    public void onBtSalvarAction() {
        gerarNomeModelo();
    }

    @FXML
    public void onBtBaixarPdfAction() {
        System.out.println("onBtBaixarPdfAction");
    }

    @FXML
    public void onBtExcluirModeloAction() {
        System.out.println("onBtExcluirModeloAction");
    }


    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPaciente.setText(nomeFormatado.toString());
    }

    public void gerarNomeModelo() {
        String nomeModelo = textFieldNomeDocumento.getText();

        if (nomeModelo.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha o nome do documento.",
                    Alert.AlertType.ERROR);
            return;
        }

        if (!modelos.contains(nomeModelo)) {
            modelos.add(nomeModelo);
        }
    }
}

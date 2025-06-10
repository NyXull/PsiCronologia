package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.entities.Paciente;
import util.Constraints;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;
import util.enums.TipoStatusPagamento;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinanceiroPagamentoController implements Initializable {

    @FXML
    private VBox vBox1FinanceiroPagamento;

    @FXML
    private VBox vBox2FinanceiroPagamento;

    @FXML
    private HBox hBoxPaiFinanceiroPagamento;

    @FXML
    private Button btNomeDoPacienteAqui;

    @FXML
    private Button btProntuario;

    @FXML
    private Button btAgenda;

    @FXML
    private Button btRelatorios;

    @FXML
    private TextField textFieldValorPorSessao;

    @FXML
    private TextField textFieldVencimento;

    @FXML
    private TextField textFieldQuantidadePorMes;

    @FXML
    private Label lblTotal;

    @FXML
    private ComboBox<TipoStatusPagamento> comboBoxStatus;

    @FXML
    private ComboBox<Month> comboBoxMes;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btStatusMensal;

    private boolean atualizando = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
        vBox1FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.25));
        vBox2FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.75));

        exibirNomePaciente();

        Constraints.setTextFieldInteger(textFieldQuantidadePorMes);
        Constraints.setTextFieldMaxLength(textFieldQuantidadePorMes, 2);

        Constraints.setTextFieldDouble(textFieldValorPorSessao);
        Constraints.setTextFieldMaxLength(textFieldValorPorSessao, 11);

        textFieldQuantidadePorMes.textProperty().addListener((obs, oldVal, newVal) -> atualizarTotal());
        textFieldValorPorSessao.textProperty().addListener((obs, oldVal, newVal) -> atualizarTotal());

        atualizarTotal();

        carregarOpcoesStatus();

        carregarOpcoesMeses();
    }

    private void atualizarTotal() {
        if (atualizando) return;
        atualizando = true;

        try {
            int quantidade = 0;
            double valor = 0.0;

            try {
                String textoQuantidade = textFieldQuantidadePorMes.getText();
                if (textoQuantidade != null && !textoQuantidade.isEmpty()) {
                    int parseInt = Integer.parseInt(textoQuantidade);

                    if (parseInt >= 0 && parseInt <= 99) {
                        quantidade = parseInt;
                    } else {
                        quantidade = Math.min(Math.max(parseInt, 0), 99);
                        String novaQuantidade = String.valueOf(quantidade);

                        if (!novaQuantidade.equals(textoQuantidade)) {
                            textFieldQuantidadePorMes.setText(novaQuantidade);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            try {
                String textoValor = textFieldValorPorSessao.getText();
                if (textoValor != null && !textoValor.isEmpty()) {
                    double parseDouble = Double.parseDouble(textoValor);

                    if (parseDouble >= 0 && parseDouble <= 99999999.99) {
                        valor = parseDouble;
                    } else {
                        valor = Math.min(Math.max(parseDouble, 0), 99999999.99);
                        String novoValor = String.format("%.2f", valor);

                        if (!novoValor.equals(textoValor)) {
                            textFieldValorPorSessao.setText(novoValor);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            double total = quantidade * valor;

            lblTotal.setText(String.format("R$ %.2f", total));
        } finally {
            atualizando = false;
        }
    }

    @FXML
    private void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    private void onBtNomeDoPacienteAquiAction() {
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
    private void onBtRelatoriosAction() {
        System.out.println("onBtRelatoriosAction");
    }

    @FXML
    private void onBtSalvarAction() {
        System.out.println("onBtSalvarAction");
    }

    @FXML
    private void onBtStatusMensalAction() {
        ViewLoader.loadView("/fxml/financeiro-status.fxml", "/css/financeiro-status.css");
    }

    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPacienteAqui.setText(nomeFormatado.toString());
    }

    private void carregarOpcoesStatus() {
        comboBoxStatus.getItems().clear();
        comboBoxStatus.getItems().addAll(TipoStatusPagamento.ABERTO, TipoStatusPagamento.PAGO);
        comboBoxStatus.setValue(TipoStatusPagamento.ABERTO);
    }

    public void carregarOpcoesMeses() {
        Locale localePtBr = Locale.forLanguageTag("pt-BR");

        ObservableList<Month> meses = FXCollections.observableArrayList(Month.values());

        comboBoxMes.setItems(meses);

        comboBoxMes.setValue(Month.of(java.time.LocalDate.now().getMonthValue()));

        comboBoxMes.setConverter(new StringConverter<Month>() {
            @Override
            public String toString(Month month) {
                if (month == null) return "";
                String nomeMes = month.getDisplayName(TextStyle.FULL, localePtBr);
                return nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
            }

            @Override
            public Month fromString(String string) {
                return null;
            }
        });
    }
}

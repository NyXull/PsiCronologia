package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Financeiro;
import model.entities.Paciente;
import model.services.FinanceiroService;
import util.*;
import util.enums.TipoStatusPagamento;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinanceiroPagamentoController implements Initializable {

    private final SimpleDateFormat sdf;

    public FinanceiroPagamentoController() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
    }

    @FXML
    public Label lblSucessoSalvar;

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
    private Button btSalvar;

    @FXML
    private Button btStatusMensal;

    private boolean atualizando = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	padraoLarguraVBox();
        exibirNomePaciente();

        Constraints.setTextFieldInteger(textFieldQuantidadePorMes);
        Constraints.setTextFieldMaxLength(textFieldQuantidadePorMes, 2);

        Constraints.setTextFieldDouble(textFieldValorPorSessao);
        Constraints.setTextFieldMaxLength(textFieldValorPorSessao, 11);

        textFieldQuantidadePorMes.textProperty().addListener((obs, oldVal, newVal) -> atualizarTotal());
        textFieldValorPorSessao.textProperty().addListener((obs, oldVal, newVal) -> atualizarTotal());

        if (informacaoPagamentoJaExiste()) {
            try {
                carregarInformacoesPagamento();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            atualizarTotal();
        }
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
    	ViewLoader.loadView("/fxml/relatorio.fxml", "/css/relatorio.css");
    }

    @FXML
    private void onBtSalvarAction() {
        Financeiro financeiro = validacaoEInstaciacao();

        if (financeiro != null) {
            FinanceiroService financeiroService = new FinanceiroService();

            try {
                financeiroService.salvarInformacoesPagamento(financeiro);
                feedbackVisual(true);
            } catch (Exception e) {
                Alerts.showAlert("Erro de Validação", "Erro inesperado", "Ocorreu um erro ao salvar informações",
                        Alert.AlertType.ERROR);
            }
        }
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

    public Financeiro validacaoEInstaciacao() {
        String valorSessaoString = textFieldValorPorSessao.getText();
        String dataVencimentoString = textFieldVencimento.getText();
        String quantidadeSessaoString = textFieldQuantidadePorMes.getText();

        if (valorSessaoString.isEmpty() || dataVencimentoString.isEmpty() || quantidadeSessaoString.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.",
                    Alert.AlertType.ERROR);
            feedbackVisual(false);
            return null;
        }

        try {
            Date dataVencimento = sdf.parse(dataVencimentoString);

            if (!dataValida(dataVencimento)) {
                Alerts.showAlert("Erro de Validação", "Data inválida!", "Data deve ser entre hoje e 30 dias.",
                        Alert.AlertType.ERROR);
                feedbackVisual(false);
                return null;
            }

            Paciente paciente = SessaoPaciente.getPaciente();
            if (paciente == null) {
                feedbackVisual(false);
                return null;
            }

            BigDecimal valorSessao = new BigDecimal(valorSessaoString);
            Integer quantidadeSessao = Integer.valueOf(quantidadeSessaoString);
            String statusPagamento = String.valueOf(TipoStatusPagamento.ABERTO);

            Instant instant = dataVencimento.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            Month mesStatusPagamentoMonth = localDate.getMonth();

            Locale localePtBr = Locale.forLanguageTag("pt-BR");

            String mesStatusPagamento = mesStatusPagamentoMonth.getDisplayName(TextStyle.FULL, localePtBr);
            mesStatusPagamento = mesStatusPagamento.substring(0, 1).toUpperCase() + mesStatusPagamento.substring(1);

            Financeiro financeiro = new Financeiro();

            financeiro.setIdPaciente(paciente.getIdPaciente());
            financeiro.setValorSessao(valorSessao);
            financeiro.setDataVencimento(dataVencimento);
            financeiro.setQuantidadeSessao(quantidadeSessao);
            financeiro.setStatusPagamento(statusPagamento);
            financeiro.setMesStatusPagamento(mesStatusPagamento);

            return financeiro;
        } catch (Exception e) {
            Alerts.showAlert("Erro de Validação", "Data inválida", "Use um formato válido. Exemplo: 08/06/2024.",
                    Alert.AlertType.ERROR);
            feedbackVisual(false);
            return null;
        }
    }

    public boolean dataValida(Date data) {
        if (data == null) {
            return false;
        }

        LocalDate dataLocal = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(30);

        return !dataLocal.isBefore(hoje) && !dataLocal.isAfter(limite);
    }

    public void carregarInformacoesPagamento() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return;
        }

        FinanceiroService financeiroService = new FinanceiroService();
        Financeiro financeiro = financeiroService.carregarInformacoesPagamento(paciente.getIdPaciente());

        if (financeiro == null) {
            return;
        }

        String valorSessao = String.valueOf(financeiro.getValorSessao());
        String dataVencimento = sdf.format(financeiro.getDataVencimento());
        String quantidadeSessao = String.valueOf(financeiro.getQuantidadeSessao());

        textFieldValorPorSessao.setText(valorSessao);
        textFieldVencimento.setText(dataVencimento);
        textFieldQuantidadePorMes.setText(quantidadeSessao);
    }

    public boolean informacaoPagamentoJaExiste() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return false;
        }

        FinanceiroService financeiroService = new FinanceiroService();
        return financeiroService.informacaoPagamentoJaExiste(paciente.getIdPaciente());
    }

    public void feedbackVisual(boolean sucessoSalvar) {
        if (sucessoSalvar) {
            lblSucessoSalvar.setText("Dados de pagamento salvos!");
        } else {
            lblSucessoSalvar.setText("");
        }
    }
    
    private void padraoLarguraVBox() {
    	vBox1FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.25));
        vBox2FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.75));
    }
}

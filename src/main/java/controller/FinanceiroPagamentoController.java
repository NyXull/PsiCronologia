package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.entities.Financeiro;
import model.entities.Paciente;
import model.services.FinanceiroService;
import util.*;
import util.enums.TipoStatusPagamento;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
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

        try {
            carregarInformacoesPagamento();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
        Financeiro financeiro = validacaoEInstaciacao();

        if (financeiro != null) {
            FinanceiroService financeiroService = new FinanceiroService();

            try {
                financeiroService.salvarInformacoesPagamento(financeiro);
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

    private void carregarOpcoesStatus() {
        comboBoxStatus.getItems().clear();
        comboBoxStatus.getItems().addAll(TipoStatusPagamento.ABERTO, TipoStatusPagamento.PAGO);
        comboBoxStatus.setValue(TipoStatusPagamento.ABERTO);
    }

    public void carregarOpcoesMeses() {
        Locale localePtBr = Locale.forLanguageTag("pt-BR");

        int mesAtual = java.time.LocalDate.now().getMonthValue();

        ObservableList<Month> meses = FXCollections.observableArrayList();

        for (int m = mesAtual; m <= 12; m++) {
            meses.add(Month.of(m));
        }

        comboBoxMes.setItems(meses);

        comboBoxMes.setValue(Month.of(mesAtual));

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

    public Financeiro validacaoEInstaciacao() {
        String valorSessaoString = textFieldValorPorSessao.getText();
        String dataVencimentoString = textFieldVencimento.getText();
        String quantidadeSessaoString = textFieldQuantidadePorMes.getText();
        TipoStatusPagamento statusPagamentoEnum = comboBoxStatus.getValue();
        Month mesStatusPagamentoMonth = comboBoxMes.getValue();

        if (valorSessaoString.isEmpty() || dataVencimentoString.isEmpty() || quantidadeSessaoString.isEmpty() || statusPagamentoEnum == null || mesStatusPagamentoMonth == null) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.",
                    Alert.AlertType.ERROR);
            return null;
        }

        try {
            Date dataVencimento = sdf.parse(dataVencimentoString);

            if (!dataValida(dataVencimento)) {
                Alerts.showAlert("Erro de Validação", "Data inválida!", "Data deve ser hoje ou posterior.", Alert.AlertType.ERROR);
                return null;
            }

            Paciente paciente = SessaoPaciente.getPaciente();
            if (paciente == null) {
                return null;
            }

            BigDecimal valorSessao = new BigDecimal(valorSessaoString);
            Integer quantidadeSessao = Integer.valueOf(quantidadeSessaoString);
            String statusPagamento = String.valueOf(statusPagamentoEnum);

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
            return null;
        }
    }

    public boolean dataValida(Date data) {
        if (data == null) {
            return false;
        }

        LocalDate dataLocal = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now();

        return !dataLocal.isBefore(hoje);
    }

    private Month converterMesPortuguesParaMonth(String mesPortugues) {
        return switch (mesPortugues.toLowerCase()) {
            case "janeiro" -> Month.JANUARY;
            case "fevereiro" -> Month.FEBRUARY;
            case "março" -> Month.MARCH;
            case "abril" -> Month.APRIL;
            case "maio" -> Month.MAY;
            case "junho" -> Month.JUNE;
            case "julho" -> Month.JULY;
            case "agosto" -> Month.AUGUST;
            case "setembro" -> Month.SEPTEMBER;
            case "outubro" -> Month.OCTOBER;
            case "novembro" -> Month.NOVEMBER;
            case "dezembro" -> Month.DECEMBER;
            default -> throw new IllegalArgumentException("Mês inválido: " + mesPortugues);
        };
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
        TipoStatusPagamento statusPagamento = TipoStatusPagamento.valueOf(financeiro.getStatusPagamento());
        Month mesStatusPagamentoMonth = converterMesPortuguesParaMonth(financeiro.getMesStatusPagamento());

        textFieldValorPorSessao.setText(valorSessao);
        textFieldVencimento.setText(dataVencimento);
        textFieldQuantidadePorMes.setText(quantidadeSessao);
        comboBoxStatus.setValue(statusPagamento);
        comboBoxMes.setValue(mesStatusPagamentoMonth);
    }
}

package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
import java.util.*;
import java.util.stream.Collectors;

public class FinanceiroStatusController implements Initializable {

    private final SimpleDateFormat sdf;

    public FinanceiroStatusController() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
    }

    @FXML
    public Text txtDataDeVencimentoAqui;

    @FXML
    private HBox hBoxPaiFinanceiroStatus;

    @FXML
    private VBox vBox1FinanceiroStatus;

    @FXML
    private VBox vBox2FinanceiroStatus;

    @FXML
    private Button btHome;

    @FXML
    private Button btNomeDoPaciente;

    @FXML
    private Button btProntuario;

    @FXML
    private Button btAgenda;

    @FXML
    private Button btRelatorios;

    @FXML
    private ComboBox<Month> comboBoxMesParaAlterar;

    @FXML
    private ComboBox<Month> comboBoxMesParaExcluir;

    @FXML
    private ComboBox<TipoStatusPagamento> comboBoxStatus;

    @FXML
    private Button btExcluir;

    @FXML
    private HBox hboxMesJaneiro;

    @FXML
    private HBox hboxMesFevereiro;

    @FXML
    private HBox hboxMesMarco;

    @FXML
    private HBox hboxMesAbril;

    @FXML
    private HBox hboxMesMaio;

    @FXML
    private HBox hboxMesJunho;

    @FXML
    private HBox hboxMesJulho;

    @FXML
    private HBox hboxMesAgosto;

    @FXML
    private HBox hboxMesSetembro;

    @FXML
    private HBox hboxMesOutubro;

    @FXML
    private HBox hboxMesNovembro;

    @FXML
    private HBox hboxMesDezembro;

    private Map<Month, HBox> mapMesParaHBox;

    private boolean existeDados = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox1FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.25));
        vBox2FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.75));

        exibirNomePaciente();

        mapMesParaHBox = Map.ofEntries(
                Map.entry(Month.JANUARY, hboxMesJaneiro),
                Map.entry(Month.FEBRUARY, hboxMesFevereiro),
                Map.entry(Month.MARCH, hboxMesMarco),
                Map.entry(Month.APRIL, hboxMesAbril),
                Map.entry(Month.MAY, hboxMesMaio),
                Map.entry(Month.JUNE, hboxMesJunho),
                Map.entry(Month.JULY, hboxMesJulho),
                Map.entry(Month.AUGUST, hboxMesAgosto),
                Map.entry(Month.SEPTEMBER, hboxMesSetembro),
                Map.entry(Month.OCTOBER, hboxMesOutubro),
                Map.entry(Month.NOVEMBER, hboxMesNovembro),
                Map.entry(Month.DECEMBER, hboxMesDezembro)
        );

        if (informacaoPagamentoJaExiste()) {
            try {
                carregarInformacoesPagamentoPorAno();
            } catch (Exception e) {
                e.printStackTrace();
                existeDados = false;
                throw new RuntimeException(e);
            }
        }
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
    private void onBtRelatoriosAction() {
        ViewLoader.loadView("/fxml/relatorio.fxml", "/css/relatorio.css");
    }

    @FXML
    public void onBtAlterarAction() {
        Financeiro financeiro = validacaoEInstanciao("alterar");

        if (financeiro != null) {
            FinanceiroService financeiroService = new FinanceiroService();

            int ano = ExtrairDadosData.getAno(financeiro.getDataVencimento());

            financeiroService.atualizarStatusPagamentoMesStatusAno(financeiro, ano);
        }
    }

    @FXML
    public void onBtExcluirAction() {
        Financeiro financeiro = validacaoEInstanciao("excluir");

        if (financeiro != null) {
            FinanceiroService financeiroService = new FinanceiroService();

            int ano = ExtrairDadosData.getAno(financeiro.getDataVencimento());

            financeiroService.atualizarStatusPagamentoMesStatusAno(financeiro, ano);
        }
    }

    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPaciente.setText(nomeFormatado.toString());
    }

    public boolean informacaoPagamentoJaExiste() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return false;
        }

        FinanceiroService financeiroService = new FinanceiroService();
        return financeiroService.informacaoPagamentoJaExiste(paciente.getIdPaciente());
    }

    public void carregarInformacoesPagamentoPorAno() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return;
        }

        FinanceiroService financeiroService = new FinanceiroService();

        // Obter o Financeiro mais recente (ou algum) para pegar a data de vencimento
        Financeiro financeiroRecente = financeiroService.carregarInformacoesPagamento(paciente.getIdPaciente());

        if (financeiroRecente == null || financeiroRecente.getDataVencimento() == null) {
            return;
        }

        int ano = ExtrairDadosData.getAno(financeiroRecente.getDataVencimento());

        List<Financeiro> listaFinanceiro =
                financeiroService.carregarInformacoesPagamentoPorAno(paciente.getIdPaciente(), ano);

        if (listaFinanceiro == null || listaFinanceiro.isEmpty()) {
            return;
        }

        carregarOpcoesStatus();
        carregarOpcoesMeses(listaFinanceiro);

        String dataVencimentoFormatada = sdf.format(financeiroRecente.getDataVencimento());

        txtDataDeVencimentoAqui.setText(dataVencimentoFormatada);

        existeDados = true;
    }

    private void carregarOpcoesStatus() {
        comboBoxStatus.getItems().clear();
        comboBoxStatus.getItems().addAll(TipoStatusPagamento.ABERTO, TipoStatusPagamento.PAGO);
        comboBoxStatus.setValue(TipoStatusPagamento.ABERTO);
    }

    public void carregarOpcoesMeses(List<Financeiro> listaFinanceiro) {
        Locale localePtBr = Locale.forLanguageTag("pt-BR");

        Set<Month> mesesUnicos = listaFinanceiro.stream()
                .map(Financeiro::getMesStatusPagamento)
                .filter(Objects::nonNull)
                .map(mesStr -> {
                    try {
                        int mesNumero = Integer.parseInt(mesStr);
                        return Month.of(mesNumero);
                    } catch (NumberFormatException e) {
                        try {
                            return converterMesPortuguesParaMonth(mesStr);
                        } catch (IllegalArgumentException ex) {
                            // Ignorar mês inválido
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));

        ObservableList<Month> meses = FXCollections.observableArrayList(mesesUnicos);

        comboBoxMesParaAlterar.setItems(meses);
        comboBoxMesParaExcluir.setItems(meses);

        if (!meses.isEmpty()) {
            comboBoxMesParaAlterar.setValue(meses.getFirst());
            comboBoxMesParaExcluir.setValue(meses.getFirst());
        }

        // Configurar conversor para ambos
        comboBoxMesParaAlterar.setConverter(converterMes(localePtBr));
        comboBoxMesParaExcluir.setConverter(converterMes(localePtBr));

        atualizarIconesMeses();
    }

    private StringConverter<Month> converterMes(Locale localePtBr) {
        return new StringConverter<>() {
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
        };
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

    private void atualizarIconeMes(HBox hboxMes, String statusPagamento) {
        hboxMes.getStyleClass().removeAll("hbox-pago", "hbox-aberto", "hbox-excluido");

        if ("PAGO".equalsIgnoreCase(statusPagamento)) {
            hboxMes.getStyleClass().add("hbox-pago");
        } else if ("ABERTO".equalsIgnoreCase(statusPagamento)) {
            hboxMes.getStyleClass().add("hbox-aberto");
        } else if ("EXCLUIDO".equalsIgnoreCase(statusPagamento)) {
            hboxMes.getStyleClass().add("hbox-excluido");
        }
    }

    public void atualizarIconesMeses() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return;
        }

        FinanceiroService financeiroService = new FinanceiroService();

        // Obter o Financeiro mais recente (ou algum) para pegar a data de vencimento
        Financeiro financeiroRecente = financeiroService.carregarInformacoesPagamento(paciente.getIdPaciente());

        if (financeiroRecente == null || financeiroRecente.getDataVencimento() == null) {
            return;
        }

        int ano = ExtrairDadosData.getAno(financeiroRecente.getDataVencimento());

        List<Financeiro> listaFinanceiro =
                financeiroService.carregarInformacoesPagamentoPorAno(paciente.getIdPaciente(), ano);

        if (listaFinanceiro == null || listaFinanceiro.isEmpty()) {
            return;
        }

        for (Financeiro financeiro : listaFinanceiro) {
            String mesStr = financeiro.getMesStatusPagamento();
            if (mesStr == null) continue;

            Month mes;
            try {
                int mesNumero = Integer.parseInt(mesStr);
                mes = Month.of(mesNumero);
            } catch (NumberFormatException e) {
                try {
                    mes = converterMesPortuguesParaMonth(mesStr);
                } catch (IllegalArgumentException ex) {
                    continue;
                }
            }

            HBox hboxMes = mapMesParaHBox.get(mes);
            if (hboxMes != null) {
                atualizarIconeMes(hboxMes, financeiro.getStatusPagamento());
            }
        }
    }

    public Financeiro validacaoEInstanciao(String acao) {
        String statusPagamentoString = String.valueOf(comboBoxStatus.getValue());
        Month mesParaExcluirMonth = comboBoxMesParaExcluir.getValue();
        Month mesParaAlterarMonth = comboBoxMesParaAlterar.getValue();

        if (!existeDados) {
            Alerts.showAlert("Erro de Validação", "Sem informações!", "Não é possível alterar nada.",
                    Alert.AlertType.ERROR);
            return null;
        }

        if ("excluir".equalsIgnoreCase(acao)) {
            if (mesParaExcluirMonth == null) {
                Alerts.showAlert("Erro de Validação", "Campo obrigatório!", "Preencha o mês para excluir.",
                        Alert.AlertType.ERROR);
                return null;
            }
        } else if ("alterar".equalsIgnoreCase(acao)) {
            if (mesParaAlterarMonth == null) {
                Alerts.showAlert("Erro de Validação", "Campo obrigatório!", "Preencha o mês para alterar.",
                        Alert.AlertType.ERROR);
                return null;
            }
        } else {
            Alerts.showAlert("Erro de Validação", "Ação desconhecida!", "Ação deve ser alterar ou excluir.",
                    Alert.AlertType.ERROR);
            return null;
        }

        try {
            Paciente paciente = SessaoPaciente.getPaciente();
            if (paciente == null) {
                return null;
            }

            Financeiro financeiroRecente = carregarInformacoesPagamento();

            BigDecimal valorSessao = financeiroRecente.getValorSessao();

            java.sql.Date sqlDate = (java.sql.Date) financeiroRecente.getDataVencimento();

            LocalDate localDate = sqlDate.toLocalDate();

            Date dataVencimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Integer quantidadeSessao = financeiroRecente.getQuantidadeSessao();
            String statusPagamento = financeiroRecente.getStatusPagamento();
            String mesStatusPagamento = financeiroRecente.getMesStatusPagamento();

            Locale localePtBr = Locale.forLanguageTag("pt-BR");

            if ("excluir".equalsIgnoreCase(acao)) {
                String mesParaExcluirString = mesParaExcluirMonth.getDisplayName(TextStyle.FULL, localePtBr);
                mesParaExcluirString =
                        mesParaExcluirString.substring(0, 1).toUpperCase() + mesParaExcluirString.substring(1);

                mesStatusPagamento = mesParaExcluirString;

                statusPagamento = String.valueOf(TipoStatusPagamento.EXCLUIDO);
            } else if ("alterar".equalsIgnoreCase(acao)) {
                statusPagamento = statusPagamentoString;

                String mesParaAlterarMonthString = mesParaAlterarMonth.getDisplayName(TextStyle.FULL, localePtBr);
                mesParaAlterarMonthString =
                        mesParaAlterarMonthString.substring(0, 1).toUpperCase() + mesParaAlterarMonthString.substring(1);

                mesStatusPagamento = mesParaAlterarMonthString;
            }

            Financeiro financeiro = new Financeiro();

            financeiro.setIdPaciente(paciente.getIdPaciente());
            financeiro.setValorSessao(valorSessao);
            financeiro.setDataVencimento(dataVencimento);
            financeiro.setQuantidadeSessao(quantidadeSessao);
            financeiro.setStatusPagamento(statusPagamento);
            financeiro.setMesStatusPagamento(mesStatusPagamento);

            Platform.runLater(this::atualizarIconesMeses);

            return financeiro;
        } catch (Exception e) {
            Alerts.showAlert("Erro de Validação", "Erro inesperado", "Ocorreu um erro ao salvar informações",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    public Financeiro carregarInformacoesPagamento() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return null;
        }

        FinanceiroService financeiroService = new FinanceiroService();

        return financeiroService.carregarInformacoesPagamento(paciente.getIdPaciente());
    }
}

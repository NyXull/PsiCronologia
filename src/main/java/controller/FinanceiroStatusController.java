package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.entities.Financeiro;
import model.entities.Paciente;
import model.services.FinanceiroService;
import util.ExibirNomeDoPaciente;
import util.ExtrairDadosData;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class FinanceiroStatusController implements Initializable {

    @FXML
    public ComboBox comboBoxMesParaAlterar1;

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
    private ComboBox comboBoxMesParaAlterar;

    @FXML
    private ComboBox comboBoxMesParaExcluir;

    @FXML
    private ComboBox comboBoxStatus;

    @FXML
    private Button btExcluir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox1FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.25));
        vBox2FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.75));

        exibirNomePaciente();

        if (informacaoPagamentoJaExiste()) {
            try {
                carregarInformacoesPagamentoPorAno();
            } catch (Exception e) {
                e.printStackTrace();
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
    private void onBtSalvarAction() {
        System.out.println("onBtSalvarAction");
    }

    @FXML
    public void onBtAlterarAction() {
        System.out.println("onBtAlterarAction");
    }

    @FXML
    public void onBtExcluirAction() {
        System.out.println("onBtExcluirAction");
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

        carregarOpcoesStatus(listaFinanceiro);
        carregarOpcoesMeses(listaFinanceiro);
    }

    private void carregarOpcoesStatus(List<Financeiro> listaFinanceiro) {
        comboBoxStatus.getItems().clear();

        // Extrair status únicos da lista
        Set<String> statusUnicos = listaFinanceiro.stream()
                .map(Financeiro::getStatusPagamento)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Se quiser usar enum TipoStatusPagamento, pode fazer um mapeamento aqui
        // Por simplicidade, adiciono direto as Strings
        comboBoxStatus.getItems().addAll(statusUnicos);

        // Seleciona o primeiro status, se existir
        if (!statusUnicos.isEmpty()) {
            comboBoxStatus.setValue(statusUnicos.iterator().next());
        }
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
        comboBoxMesParaAlterar1.setItems(meses);

        if (!meses.isEmpty()) {
            comboBoxMesParaAlterar.setValue(meses.get(0));
            comboBoxMesParaAlterar1.setValue(meses.get(0));
        }

        // Configurar conversor para ambos
        comboBoxMesParaAlterar.setConverter(converterMes(localePtBr));
        comboBoxMesParaAlterar1.setConverter(converterMes(localePtBr));
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
}
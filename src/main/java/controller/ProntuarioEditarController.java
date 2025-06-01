package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Paciente;
import model.entities.Prontuario;
import model.services.ProntuarioService;
import util.Alerts;
import util.Constraints;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ProntuarioEditarController implements Initializable {

    private final SimpleDateFormat sdf;

    public ProntuarioEditarController() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
    }

    @FXML
    public HBox hBoxPaiProntuarioLista;

    @FXML
    public VBox vBox1ProntuarioLista;

    @FXML
    public Button btHome;

    @FXML
    public Text txtPaciente;

    @FXML
    public VBox vboxPesquisa;

    @FXML
    public TextField txtPesquisar;

    @FXML
    public ListView listView;

    @FXML
    public VBox vBox2ProntuarioLista;

    @FXML
    public Button btEsquerda;

    @FXML
    public Button btCentralizado;

    @FXML
    public Button btDireita;

    @FXML
    public Button btJustificado;

    @FXML
    public Button btNumerado;

    @FXML
    public Button btOrdenado;

    @FXML
    public ComboBox cbFonte;

    @FXML
    public ComboBox cbTamanho;

    @FXML
    public Button btNegrito;

    @FXML
    public Button btItalico;

    @FXML
    public TextArea txtAreaProntuario;

    @FXML
    public Text txtProntuario;

    @FXML
    public Text txtData;

    @FXML
    public Text txtDataDoProntuarioAqui;

    @FXML
    public Text txtDetalhesDoProntuario;

    @FXML
    public Button btSalvar;

    @FXML
    public Button btNomeDoPacienteAqui;

    @FXML
    public Text txtSessaoDoProntuarioAqui;

    @FXML
    public void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    public void onBtSalvar() {
        Prontuario prontuario = validacaoEInstaciacao();

        if (prontuario != null) {
            ProntuarioService prontuarioService = new ProntuarioService();

            try {
                prontuarioService.salvarProntuario(prontuario);
                atualizarSessao(prontuarioService);
            } catch (IllegalArgumentException e) {
                Alerts.showAlert("Erro de Validação", "Sessão já existe", e.getMessage(), Alert.AlertType.ERROR);
            } catch (Exception e) {
                Alerts.showAlert("Erro de Validação", "Erro inesperado", "Ocorreu um erro ao salvar o prontuário",
                        Alert.AlertType.ERROR);
            }
        }
    }

    public void onBtNomeDoPacienteAquiAction() {
        ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
        vBox1ProntuarioLista.prefWidthProperty().bind(hBoxPaiProntuarioLista.widthProperty().multiply(0.25));
        vBox2ProntuarioLista.prefWidthProperty().bind(hBoxPaiProntuarioLista.widthProperty().multiply(0.75));

        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente != null) {
            btNomeDoPacienteAqui.setText(paciente.getNomePaciente());
        }

        LocalDate dataAtual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);

        txtDataDoProntuarioAqui.setText(dataFormatada);

        Constraints.setTextAreaMaxLength(txtAreaProntuario, 16777215);

        ProntuarioService prontuarioService = new ProntuarioService();
        atualizarSessao(prontuarioService);
    }

    public Prontuario validacaoEInstaciacao() {
        String dataAtendimentoString = txtDataDoProntuarioAqui.getText();
        String descricao = txtAreaProntuario.getText();
        String caminho_arquivo = "path";

        if (dataAtendimentoString.isEmpty() || descricao.isEmpty() || caminho_arquivo.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.",
                    Alert.AlertType.ERROR);
            return null;
        }

        try {
            Date dataAtendimento = sdf.parse(dataAtendimentoString);

            Paciente paciente = SessaoPaciente.getPaciente();
            if (paciente == null) {
                return null;
            }

            Prontuario prontuario = new Prontuario();

            prontuario.setIdPaciente(paciente.getIdPaciente());
            prontuario.setDataAtendimento(dataAtendimento);
            prontuario.setDescricao(descricao);
            prontuario.setCaminhoArquivo(caminho_arquivo);
            prontuario.setIdSessao(paciente.getIdPaciente());

            return prontuario;
        } catch (Exception e) {
            Alerts.showAlert("Erro de Validação", "Data inválida", "Use um formato válido. Exemplo: 08/06/2024.",
                    Alert.AlertType.ERROR);
            return null;
        }
    }

    public void atualizarSessao(ProntuarioService prontuarioService) {
        int idSessao = SessaoPaciente.getPaciente().getIdPaciente();
        int proximoIdOrdem = prontuarioService.getProximoIdOrdem(idSessao);

        txtSessaoDoProntuarioAqui.setText(String.valueOf(proximoIdOrdem));
    }
}

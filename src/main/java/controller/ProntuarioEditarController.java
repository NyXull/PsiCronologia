package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import model.entities.Paciente;
import model.entities.Prontuario;
import model.services.ProntuarioService;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import util.Alerts;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;
import util.interfaces.ParametroRecebivel;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProntuarioEditarController implements Initializable, ParametroRecebivel<Prontuario> {

    private final SimpleDateFormat sdf;

    @FXML
    public HTMLEditor htmlEditor;

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
    public ListView<Prontuario> listView;

    @FXML
    public VBox vBox2ProntuarioLista;

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

    private ObservableList<Prontuario> obsProntuarios;
    private FilteredList<Prontuario> prontuariosFiltrados;

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
                carregarListaProntuarios();
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

        atualizarSessao();

        dataAtualFormatada();

        carregarListaProntuarios();

        exibirNomePaciente();
    }

    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPacienteAqui.setText(nomeFormatado.toString());
    }

    public void dataAtualFormatada() {
        LocalDate dataAtual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);

        txtDataDoProntuarioAqui.setText(dataFormatada);
    }

    public Prontuario validacaoEInstaciacao() {
        String dataAtendimentoString = txtDataDoProntuarioAqui.getText();
        String descricaoHtml = htmlEditor.getHtmlText();
        Integer idOrdem = Integer.valueOf(txtSessaoDoProntuarioAqui.getText());
        int maxLength = 16777215;
        String descricaoTexto = Jsoup.parse(descricaoHtml).text().trim();

        if (dataAtendimentoString.isEmpty() || descricaoTexto.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.",
                    Alert.AlertType.ERROR);
            return null;
        }

        if (descricaoHtml.length() > maxLength) {
            Alerts.showAlert("Erro de Validação", "Texto muito longo!",
                    "O texto não pode ultrapassar " + maxLength + " caracteres (incluindo HTML).",
                    Alert.AlertType.ERROR);
            return null;
        }

        try {
            Date dataAtendimento = sdf.parse(dataAtendimentoString);

            Paciente paciente = SessaoPaciente.getPaciente();
            if (paciente == null) {
                return null;
            }

            String nomeArquivo = gerarNomeArquivo(paciente.getNomePaciente(), idOrdem);

            String userHome = System.getProperty("user.home");
            String documentosPath = userHome + File.separator + "Documents";

            File arquivoDocx = new File(documentosPath, nomeArquivo);

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);

            wordMLPackage.getMainDocumentPart().getContent().addAll(
                    xhtmlImporter.convert(descricaoHtml, null));

            wordMLPackage.save(arquivoDocx);

            Prontuario prontuario = new Prontuario();

            prontuario.setIdPaciente(paciente.getIdPaciente());
            prontuario.setDataAtendimento(dataAtendimento);
            prontuario.setDescricao(descricaoHtml);
            prontuario.setCaminhoArquivo(arquivoDocx.getAbsolutePath());
            prontuario.setIdSessao(paciente.getIdPaciente());
            prontuario.setIdOrdem(idOrdem);

            return prontuario;
        } catch (Exception e) {
            Alerts.showAlert("Erro de Validação", "Data inválida", "Use um formato válido. Exemplo: 08/06/2024.",
                    Alert.AlertType.ERROR);
            return null;
        }
    }

    public void atualizarSessao() {
        ProntuarioService prontuarioService = new ProntuarioService();
        int idSessao = SessaoPaciente.getPaciente().getIdPaciente();
        int proximoIdOrdem = prontuarioService.getProximoIdOrdem(idSessao);

        txtSessaoDoProntuarioAqui.setText(String.valueOf(proximoIdOrdem));
    }

    private void carregarListaProntuarios() {
        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente == null) {
            return;
        }

        ProntuarioService prontuarioService = new ProntuarioService();
        List<Prontuario> prontuarios = prontuarioService.listarPorPaciente(paciente.getIdPaciente());

        // Ordena por data de atendimento (mais recente primeiro)
        prontuarios.sort(Comparator.comparing(Prontuario::getDataAtendimento).reversed());

        obsProntuarios = FXCollections.observableArrayList(prontuarios);

        prontuariosFiltrados = new FilteredList<>(obsProntuarios, p -> true);

        // Listener para o campo de pesquisa (filtra pela data formatada)
        txtPesquisar.textProperty().addListener((obs, oldValue, newValue) -> {
            prontuariosFiltrados.setPredicate(prontuario -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String dataFormatada = sdf.format(prontuario.getDataAtendimento());
                return dataFormatada.contains(newValue.trim());
            });
        });

        // Placeholder para quando não houver resultados
        prontuariosFiltrados.addListener((ListChangeListener<Prontuario>) change -> {
            if (prontuariosFiltrados.isEmpty()) {
                Text placeholderText = new Text("Nenhum prontuário encontrado!");
                placeholderText.setStyle("-fx-font-family: 'Nunito'; -fx-font-size: 13pt; -fx-fill: #5F3AFC; " +
                        "-fx-font-style: italic;");
                listView.setPlaceholder(placeholderText);
            } else {
                listView.setPlaceholder(null);
            }
        });

        listView.setItems(prontuariosFiltrados);

        // Exibe somente a data formatada na ListView
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Prontuario prontuario, boolean empty) {
                super.updateItem(prontuario, empty);
                if (empty || prontuario == null) {
                    setText(null);
                } else {
                    setText(sdf.format(prontuario.getDataAtendimento()));
                }
            }
        });

        // Evento de clique na ListView
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Prontuario prontuarioSelecionado = listView.getSelectionModel().getSelectedItem();
                if (prontuarioSelecionado != null) {
                    String idOrdem = String.valueOf(prontuarioSelecionado.getIdOrdem());
                    String dataAtendimentoFormatada = sdf.format(prontuarioSelecionado.getDataAtendimento());

                    txtSessaoDoProntuarioAqui.setText(idOrdem);
                    htmlEditor.setHtmlText(prontuarioSelecionado.getDescricao());
                    txtDataDoProntuarioAqui.setText(dataAtendimentoFormatada);
                }
            }
        });
    }

    public String gerarNomeArquivo(String nomePaciente, int numeroSessao) {
        String nomePacienteSanitizado = nomePaciente.replaceAll("[^a-zA-Z0-9]", "_");

        return String.format("%s_sessao_%d.docx",
                nomePacienteSanitizado,
                numeroSessao);
    }

    public void carregarProntuario(Prontuario prontuario) {
        if (prontuario != null) {
            txtSessaoDoProntuarioAqui.setText(String.valueOf(prontuario.getIdOrdem()));
            htmlEditor.setHtmlText(prontuario.getDescricao());
            txtDataDoProntuarioAqui.setText(sdf.format(prontuario.getDataAtendimento()));
        }
    }

    @Override
    public void receberParametro(Prontuario prontuario) {
        carregarProntuario(prontuario);
    }
}

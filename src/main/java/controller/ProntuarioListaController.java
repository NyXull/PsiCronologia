package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Paciente;
import model.entities.Prontuario;
import model.services.ProntuarioService;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ProntuarioListaController implements Initializable {

    private final SimpleDateFormat sdf;

    public ProntuarioListaController() {
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
    public Button btNovoProntuario;

    @FXML
    public Text txtNovoProntuario;

    @FXML
    public Button btNomeDoPacienteAqui;

    private ObservableList<Prontuario> obsProntuarios;
    private FilteredList<Prontuario> prontuariosFiltrados;

    public void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    public void onBtNovoProntuario() {
        ViewLoader.loadView("/fxml/prontuario-editar.fxml", "/css/prontuario-editar.css");
    }

    public void onBtNomeDoPacienteAquiAction() {
        ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
        vBox1ProntuarioLista.prefWidthProperty().bind(hBoxPaiProntuarioLista.widthProperty().multiply(0.25));
        vBox2ProntuarioLista.prefWidthProperty().bind(hBoxPaiProntuarioLista.widthProperty().multiply(0.75));

        exibirNomePaciente();

        carregarListaProntuarios();
    }

    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPacienteAqui.setText(nomeFormatado.toString());
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
                    ViewLoader.loadView("/fxml/prontuario-editar.fxml", "/css/prontuario-editar.css",
                            prontuarioSelecionado);
                }
            }
        });
    }
}

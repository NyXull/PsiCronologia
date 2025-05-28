package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.entities.Paciente;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class ProntuarioListaController implements Initializable {

    @FXML
    public HBox hBoxPaiProntuarioLista;

    @FXML
    public VBox vBox1ProntuarioLista;

    @FXML
    public Button btHome;

    @FXML
    public Text txtPaciente;

    @FXML
    public Line lineLinha;

    @FXML
    public VBox vboxPesquisa;

    @FXML
    public TextField txtPesquisar;

    @FXML
    public ListView listView;

    @FXML
    public VBox vBox2ProntuarioLista;

    @FXML
    public Button btNovoProntuario;

    @FXML
    public Text txtNovoProntuario;

    @FXML
    public Button btNomeDoPacienteAqui;

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

        Paciente paciente = SessaoPaciente.getPaciente();
        if (paciente != null) {
            btNomeDoPacienteAqui.setText(paciente.getNomePaciente());
        }
    }
}

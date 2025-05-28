package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.entities.Paciente;
import util.SessaoPaciente;
import util.ViewLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class ProntuarioEditarController implements Initializable {

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
    public VBox vBox3FundoRosa;

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
    public VBox vBox1FundoOpcoes;

    @FXML
    public Button btNegrito;

    @FXML
    public Button btItalico;

    @FXML
    public Button btSublinhado;

    @FXML
    public Button btRasurado;

    @FXML
    public VBox vBox4FundoRosa;

    @FXML
    public TextArea txtAreaProntuario;

    @FXML
    public Text txtProntuario;

    @FXML
    public VBox vBox1FundoRosa;

    @FXML
    public Text txtData;

    @FXML
    public Text txtDataDoProntuarioAqui;

    @FXML
    public VBox vBox2FundoRosa;

    @FXML
    public Text txtDetalhesDoProntuario;

    @FXML
    public Button btSalvar;

    @FXML
    public Button btNomeDoPacienteAqui;

    @FXML
    public TextField txtNumeroSessao;

    @FXML
    public void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    public void onBtSalvar() {
        System.out.println("onBtSalvar");
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

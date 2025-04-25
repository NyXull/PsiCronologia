package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ViewLoader;

public class CadastroPacienteController implements Initializable{

	@FXML
    private HBox hBoxPaiCadastroPaciente;
	
	@FXML
	private VBox vBox1CadastroPaciente;
	
	@FXML
	private VBox vBox2CadastroPaciente;
	
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtDataNascimento;
    
    @FXML
    private TextField txtCPF;
    
    @FXML
    private TextField txtTelefone;
    
    @FXML
    private Button btCadastrar;
    
    @FXML
    public void onBtCadastrarAction() {
    	ViewLoader.loadView("/fxml/cadastro-paciente-finalizado.fxml", "/css/cadastro-paciente-finalizado.css");
    }

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.25));
        vBox2CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.75));		
	}
}
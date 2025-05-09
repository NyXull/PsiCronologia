package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import model.services.PacienteService;
import util.Alerts;
import util.ViewLoader;

public class CadastroPacienteController implements Initializable{
	
	private final SimpleDateFormat sdf;
	
	public CadastroPacienteController() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
	}

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
    	Paciente paciente = validacaoEInstaciacao();
    	
    	if (paciente != null) {    		
    		PacienteService pacienteService = new PacienteService();
    		
    		if(pacienteService.cpfJaCadastrado(paciente.getCpf())) {
    			Alerts.showAlert("Erro de Validação", "CPF já cadastrado", "Use um CPF por paciente.", AlertType.ERROR);
    		}
    		else {
    			pacienteService.cadastrarPaciente(paciente);
        		
        		ViewLoader.loadView("/fxml/cadastro-paciente-finalizado.fxml", "/css/cadastro-paciente-finalizado.css");
    		}    		
    	}    	    
    }

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.25));
        vBox2CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.75));		
	}
	
	private Paciente validacaoEInstaciacao() {
		String nomePaciente = txtNome.getText();
		String emailPaciente = txtEmail.getText();
		String dataNascStr = txtDataNascimento.getText();
		String cpf = txtCPF.getText();
		String telefone = txtTelefone.getText();
		
		if (nomePaciente.isEmpty() || emailPaciente.isEmpty() || dataNascStr.isEmpty() ||
				cpf.isEmpty() || telefone.isEmpty()) {
			Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.", AlertType.ERROR);
			return null;
		}
		
		try {
			Date dataNascimento = sdf.parse(dataNascStr);
			
			Paciente paciente = new Paciente();
			
			paciente.setNomePaciente(nomePaciente);
			paciente.setEmailPaciente(emailPaciente);
			paciente.setDataNascimento(dataNascimento);
			paciente.setCpf(cpf);
			paciente.setTelefone(telefone);
			
			return paciente;			
		}
		catch (Exception e) {
			Alerts.showAlert("Erro de Validação", "Data inválida", "Use um formato válido. Exemplo: 08/06/2024.", AlertType.ERROR);
	        return null;
		}
	}
}
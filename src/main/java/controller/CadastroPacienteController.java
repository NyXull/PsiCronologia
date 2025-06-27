package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import model.services.PacienteService;
import util.Alerts;
import util.Constraints;
import util.SessaoPaciente;
import util.SessaoUsuario;
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
    private Label labelErroEmail;
    
    @FXML
    private TextField txtDataNascimento;
    
    @FXML
    private TextField txtCPF;
    
    @FXML
    private TextField txtTelefone;
    
    @FXML
    private Button btCadastrar;
    
    @FXML
    private Button btVoltar;
    
    private boolean emailValido = false;
    
    @FXML
    public void onBtVoltarAction() { 
    	ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }
    
    @FXML
    public void onBtCadastrarAction() {
    	Paciente paciente = validacaoEInstaciacao();
    	
    	if (paciente != null) {    		
    		PacienteService pacienteService = new PacienteService();
    		
    		try {
    			pacienteService.cadastrarOuAssociarPaciente(paciente, SessaoUsuario.getPsicologoLogado().getIdPsico());
    			
    			SessaoPaciente.setPaciente(paciente);
    			
    			ViewLoader.loadView("/fxml/cadastro-paciente-finalizado.fxml", "/css/cadastro-paciente-finalizado.css");    			
    		}
    		catch(IllegalStateException e) {
    			Alerts.showAlert("Erro de Validação", "CPF já cadastrado", "Você já cadastrou este paciente!", AlertType.ERROR);
    		}
    		catch (Exception e) {
    			Alerts.showAlert("Erro inesperado", "Erro ao cadastrar paciente", e.getMessage(), AlertType.ERROR);
    		}
    	}    	    
    }

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
        padraoLarguraVBox();
        iniciarValidacaoSintaxeEmail();
        btCadastrar.setDefaultButton(true);
	}
	
	private void iniciarValidacaoSintaxeEmail() {
		txtEmail.focusedProperty().addListener((obs, estavaFocado, estaFocado) -> {
			if (!estaFocado) {
				String email = txtEmail.getText();
				if (email.isEmpty()) {
					labelErroEmail.setText("");
					emailValido = true;
				}
				else if (!Constraints.validacaoSintaxeEmail(email)) {
					labelErroEmail.setText("Email inválido");
					emailValido = false;
				}
				else {
					labelErroEmail.setText("");
					emailValido = true;
				}
			}
		});		
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
	
	private void padraoLarguraVBox() {
		vBox1CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.25));
        vBox2CadastroPaciente.prefWidthProperty().bind(hBoxPaiCadastroPaciente.widthProperty().multiply(0.75));
	}
}
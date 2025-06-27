package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Psicologo;
import model.services.EmailService;
import model.services.PsicologoService;
import model.services.VerificacaoEmailService;
import util.Alerts;
import util.Constraints;
import util.EmailTemplates;
import util.ViewLoader;

public class CadastroController implements Initializable{

	@FXML
	private VBox vBox1Cadastro;
	
	@FXML
	private VBox vBox2Cadastro;

	@FXML
	private HBox hBoxPaiCadastro;
	
	@FXML
	private Button btVoltar;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtEmail;
	
	@FXML
	private Label lblErroEmail;

	@FXML
	private PasswordField txtSenha;
	
	@FXML
	private Label lblErroSenha;

	@FXML
	private Button btCadastrar;
	
	private boolean emailValido = false;
	
	private boolean senhaValida = false;
	
	@FXML
	public void onBtVoltarAction() {
		ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
	}
	
	@FXML
	public void onBtCadastrar() {
		Psicologo psicologo = validacaoEInstanciacaoPsico();
		
		if (psicologo != null) {			
			PsicologoService psicoService = new PsicologoService();
			
			if (psicoService.emailJaCadastrado(psicologo.getEmailPsico())) {	    	   
				Alerts.showAlert("Erro de Validação", "Email já cadastrado", "Use outro endereço de email.", AlertType.ERROR);
	    	}
			else {				
				VerificacaoEmailService verificacaoService = servicoVerificacao(psicologo);				
				psicoService.cadastrarPsicologo(psicologo);
		    	
		    	ViewLoader.loadView("/fxml/verificacao-email.fxml", "/css/verificacao-email.css");
			}
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		padraoLarguraVBox();
		iniciarValidacoes();
		btCadastrar.setDefaultButton(true);
	}
	
	private void iniciarValidacoes() {
		txtEmail.focusedProperty().addListener((obs, estavaFocado, estaFocado) -> {
			if (!estaFocado) {
				String email = txtEmail.getText();
				if (email.isEmpty()) {
					lblErroEmail.setText("");
					emailValido = true;
				}
				else if (!Constraints.validacaoSintaxeEmail(email)) {
					lblErroEmail.setText("Email inválido!");
					emailValido = false;
				}	
				else {
					lblErroEmail.setText("");
					emailValido = true;
				}
				atualizarEstadoBotaoCadastrar();
			}			
		});
		
		txtSenha.textProperty().addListener((obs, valorAntigo, valorAtual) -> {
			if (valorAtual.isEmpty()) {
				lblErroSenha.setText("");
				senhaValida = true;
			}
			else if (!Constraints.senhaValida(valorAtual)) {
				lblErroSenha.setText("Mínimo 8 caracteres, uma letra maiúscula e um símbolo.");
				senhaValida = false;
			}
			else {
				lblErroSenha.setText("");
				senhaValida = true;
			}
			atualizarEstadoBotaoCadastrar();
		});
	}

	private void atualizarEstadoBotaoCadastrar() {
		
		btCadastrar.setDisable(!emailValido || !senhaValida);
	}

	private Psicologo validacaoEInstanciacaoPsico() {
		try {
			String nomePsico = txtNome.getText();
			String emailPsico = txtEmail.getText();
			String senhaPsico = txtSenha.getText();
			
			if (nomePsico.isEmpty() || emailPsico.isEmpty() || senhaPsico.isEmpty()) {
	            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.", AlertType.ERROR);
	            return null;
	        }
			else {
				Psicologo objPsicologo = new Psicologo();
				objPsicologo.setNomePsico(nomePsico);
		    	objPsicologo.setEmailPsico(emailPsico);
		    	objPsicologo.setSenhaPsico(senhaPsico);
				
		    	return objPsicologo;
			}			
		}
		catch (Exception e) {
    		e.printStackTrace();    
    		return null;
    	}		
	}
	
	private VerificacaoEmailService servicoVerificacao(Psicologo psicologo) {		
		VerificacaoEmailService verificacaoService = new VerificacaoEmailService();
    	
		String codigo = verificacaoService.gerarCodigo(psicologo.getEmailPsico());
    	
    	String corpoEmail = EmailTemplates.getEmailVerificacao(psicologo.getNomePsico(), codigo);
    	
    	EmailService emailService = new EmailService();
		
		emailService.enviarEmail(
				"Verificação de email",
				corpoEmail,
				psicologo.getEmailPsico());
		
		return verificacaoService;
	}	
	
	private void padraoLarguraVBox() {
		vBox1Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.25));
		vBox2Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.75));
	}
}
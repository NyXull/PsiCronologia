package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.services.EmailService;
import model.services.VerificacaoEmailService;
import util.Constraints;
import util.EmailTemplates;
import util.ViewLoader;

public class RecuperacaoSenhaController implements Initializable {

	@FXML
	private HBox hBoxPaiRecuperacaoSenha;
	
	@FXML
	private VBox vBox1RecuperacaoSenha;
	
	@FXML
	private VBox vBox2RecuperacaoSenha;
	
	@FXML
	private TextField textFieldEmail;
	
	@FXML
	private Label lblErroSintaxeEmail;
	
	@FXML
	private Button btEnviar;	
	
	private boolean emailValido = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();	
		validacaoEmail();
	}
	
	@FXML
	public void onBtEnviarAction() {
		if (textFieldEmail.getText().isEmpty()) {
			lblErroSintaxeEmail.setText("Insira um email válido!");
		}
		else {
			VerificacaoEmailService verificacaoService = servicoRecuperacao(textFieldEmail.getText());
			String email = textFieldEmail.getText();
			ViewLoader.loadView("/fxml/codigo-recuperacao-senha.fxml", "/css/codigo-recuperacao-senha.css", email);
		}			
	}
	
	private void validacaoEmail() {
		textFieldEmail.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
			if (textoNovo.isEmpty()) {
				lblErroSintaxeEmail.setText("");
				emailValido = true;
			}
			else if (!Constraints.validacaoSintaxeEmail(textoNovo)) {
					lblErroSintaxeEmail.setText("Email deve ser: exemplo@email.com");
					emailValido = false;
				}
				else {
					lblErroSintaxeEmail.setText("");
					emailValido = true;
				}
			atualizarEstadoBotaoEnviar();
		});
	}
	
	private void atualizarEstadoBotaoEnviar() {
		btEnviar.setDisable(!emailValido);		
	}

	private VerificacaoEmailService servicoRecuperacao(String email) {
		VerificacaoEmailService verificacaoService = new VerificacaoEmailService();
		String codigo = verificacaoService.gerarCodigo(email);		
		String corpoEmail = EmailTemplates.getEmailRecuperacao(codigo);
		EmailService emailService = new EmailService();
		
		emailService.enviarEmail(
				"Recuperação de senha", 
				corpoEmail, 
				email);
		return verificacaoService;
	}
	
	public String getEmail() {
		return textFieldEmail.getText();
	}
	
	private void padraoLarguraVBox() {
		vBox1RecuperacaoSenha.prefWidthProperty().bind(hBoxPaiRecuperacaoSenha.widthProperty().multiply(0.25));
		vBox2RecuperacaoSenha.prefWidthProperty().bind(hBoxPaiRecuperacaoSenha.widthProperty().multiply(0.75));
	}
}
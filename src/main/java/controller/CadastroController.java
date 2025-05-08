package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Psicologo;
import model.services.PsicologoService;
import util.Alerts;
import util.ViewLoader;

public class CadastroController implements Initializable{

	@FXML
	private VBox vBox1Cadastro;
	
	@FXML
	private VBox vBox2Cadastro;

	@FXML
	private HBox hBoxPaiCadastro;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private Button btCadastrar;
	
	@FXML
	public void onBtCadastrar() {
		try {
			String nomePsico = txtNome.getText();
			String emailPsico = txtEmail.getText();
			String senhaPsico = txtSenha.getText();
			
			if (nomePsico.isEmpty() || emailPsico.isEmpty() || senhaPsico.isEmpty()) {
	            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.", AlertType.ERROR);
	        }
			else {			
				Psicologo objPsicologo = new Psicologo();
				objPsicologo.setNomePsico(nomePsico);
		    	objPsicologo.setEmailPsico(emailPsico);
		    	objPsicologo.setSenhaPsico(senhaPsico);
				
		    	PsicologoService psicoService = new PsicologoService();
		    	
		    	if (psicoService.emailJaCadastrado(emailPsico)) {
		    	    Alerts.showAlert("Erro de Validação", "Email já cadastrado", "Use outro endereço de email.", AlertType.ERROR);
		    	    return;
		    	}
		    	
		    	psicoService.cadastrarPsicologo(objPsicologo);
		    	
		    	ViewLoader.loadView("/fxml/verificacao-email.fxml", "/css/verificacao-email.css");
			}
		}
		catch (Exception e) {
    		e.printStackTrace();    		
    	} 	
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)		
		vBox1Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.25));
		vBox2Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.75));
	}
}
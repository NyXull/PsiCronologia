package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.interfaces.PsicologoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Psicologo;
import model.services.PsicologoService;
import util.Alerts;
import util.ViewLoader;

public class LoginController implements Initializable {

    @FXML
    private VBox vbox1Login;

    @FXML
    private VBox vbox2Login;

    @FXML
    private HBox hboxLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btCadastro;

    @FXML
    private Button btEntrar;

    @FXML
    private Text txtLogin;
    
    @FXML
    public void onBtCadastroAction() {
    	ViewLoader.loadView("/fxml/cadastro.fxml", "/css/cadastro.css");    	
    }
    
    @FXML
    public void onBtEntrarAction(ActionEvent event) {
    	try {
    		String emailPsico = txtEmail.getText();
        	String senhaPsico = txtSenha.getText();
        	
        	if (emailPsico.isEmpty() || senhaPsico.isEmpty()) {
	            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.", AlertType.ERROR);
	        }
			else {        	
	        	Psicologo objPsicologo = new Psicologo();
	        	objPsicologo.setEmailPsico(emailPsico);
	        	objPsicologo.setSenhaPsico(senhaPsico);
	        	
	        	PsicologoService psicoService = new PsicologoService();
	        	ResultSet rsPsicologoDAO = psicoService.autenticacao(objPsicologo);
	        	
	        	if (rsPsicologoDAO.next()) {
	        		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");        		
	        	}
	        	else {
	        		Alerts.showAlert("Erro ao logar", "Email ou Senha inválidos!", "Preencha todos os campos corretamente.", AlertType.ERROR);
	        	}
			}
    	}
    	catch (SQLException e) {
    		Alerts.showAlert(null, "LoginController:", null, AlertType.ERROR);
    		
    	}    	
    }
    
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    	// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
        vbox1Login.prefWidthProperty().bind(hboxLogin.widthProperty().multiply(0.25));
        vbox2Login.prefWidthProperty().bind(hboxLogin.widthProperty().multiply(0.75));        
    } 
}
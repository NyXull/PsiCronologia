package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.PsicologoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Psicologo;
import model.services.PsicologoService;
import util.Alerts;
import util.ViewLoader;

public class LoginController2 implements Initializable {

    @FXML
    private VBox vBox1Login2;

    @FXML
    private VBox vBox2Login2;

    @FXML
    private HBox hBoxPaiLogin2;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btEntrar;

    @FXML
    private Text txtLogin;
    
    @FXML
    public void onBtEntrarAction() {
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
	        		Alerts.showAlert(null, "Email ou Senha inválidos!", null, AlertType.ERROR);
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
    	vBox1Login2.prefWidthProperty().bind(hBoxPaiLogin2.widthProperty().multiply(0.25));
    	vBox2Login2.prefWidthProperty().bind(hBoxPaiLogin2.widthProperty().multiply(0.75));        
    } 
}
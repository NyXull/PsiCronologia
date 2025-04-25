package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ViewLoader;

public class VerificacaoEmailController implements Initializable{

	@FXML
	private VBox vBox1VerificacaoEmail;
	
	@FXML
	private VBox vBox2VerificacaoEmail;
	
	@FXML
	private HBox hBoxPaiVerificacaoEmail;
	
	@FXML
	private Button btLogin;	
	
	@FXML
	public void onBtLoginAction() {
		System.out.println("onBtLoginAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1VerificacaoEmail.prefWidthProperty().bind(hBoxPaiVerificacaoEmail.widthProperty().multiply(0.25));
		vBox2VerificacaoEmail.prefWidthProperty().bind(hBoxPaiVerificacaoEmail.widthProperty().multiply(0.75));		
	}
}
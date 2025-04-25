package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		ViewLoader.loadView("/fxml/verificacao-email.fxml", "/css/verificacao-email.css");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)		
		vBox1Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.25));
		vBox2Cadastro.prefWidthProperty().bind(hBoxPaiCadastro.widthProperty().multiply(0.75));
	}
}
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.services.VerificacaoEmailService;
import util.Alerts;
import util.ViewLoader;

public class VerificacaoEmailController implements Initializable {

	@FXML
	private VBox vBox1VerificacaoEmail;

	@FXML
	private VBox vBox2VerificacaoEmail;

	@FXML
	private HBox hBoxPaiVerificacaoEmail;

	@FXML
	private TextField txtCodigoVerificacao;
	
	@FXML
	private Button btVerificar;

	@FXML
	public void onBtVerificarAction() {
		String codigo = txtCodigoVerificacao.getText().trim();

		VerificacaoEmailService emailService = new VerificacaoEmailService();

		if (emailService.validarCodigo(codigo)) {
			ViewLoader.loadView("/fxml/login2.fxml", "/css/login.css");
		} else {
			Alerts.showAlert("Código inválido", "Código expirado ou incorreto", "Tente novamente", AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1VerificacaoEmail.prefWidthProperty().bind(hBoxPaiVerificacaoEmail.widthProperty().multiply(0.25));
		vBox2VerificacaoEmail.prefWidthProperty().bind(hBoxPaiVerificacaoEmail.widthProperty().multiply(0.75));
	}
}
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.services.PsicologoService;
import util.Constraints;
import util.ViewLoader;
import util.interfaces.ParametroRecebivel;

public class NovaSenhaRecuperacaoController implements Initializable, ParametroRecebivel<String>{

	@FXML
	private HBox hBoxPaiNovaSenhaRecuperacao;
	
	@FXML
	private VBox vBox1NovaSenhaRecuperacao;
	
	@FXML
	private VBox vBox2NovaSenhaRecuperacao;
	
	@FXML
	private PasswordField passwordFieldNovaSenha;
	
	@FXML
	private PasswordField passwordFieldConfirmacaoSenha;
	
	@FXML
	private Label lblErroNovaSenha;
	
	@FXML
	private Label lblErroConfirmacaoSenha;
	
	@FXML
	private Button btAvancar;
	
	private String email;
	
	private boolean senhaValida = false;
	
	@FXML
	public void onBtAvancarAction() {
		String novaSenha = passwordFieldNovaSenha.getText();
		PsicologoService psicoService = new PsicologoService();
		psicoService.atualizarSenha(email, novaSenha);
			ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
	}
	
	private void validacaoSintaxeSenha() {
		passwordFieldNovaSenha.textProperty().addListener((obs, valorAntigo, valorAtual) -> {
			if (valorAtual.isEmpty()) {
				lblErroNovaSenha.setText("");
				senhaValida = true;
			}
			else if (!Constraints.senhaValida(valorAtual)) {
				lblErroNovaSenha.setText("Mínimo 8 caracteres, uma letra maiúscula e um símbolo.");
				senhaValida = false;
			}
			else {
				lblErroNovaSenha.setText("");
				senhaValida = true;
			}
			atualizarEstadoBotaoAvancar();
		});
		
		passwordFieldConfirmacaoSenha.textProperty().addListener((obs, valorAntigo, valorAtual) -> {
			if (valorAtual.isEmpty()) {
				lblErroConfirmacaoSenha.setText("");
				senhaValida = true;
			}
			else if (!Constraints.senhaValida(valorAtual)) {
				lblErroConfirmacaoSenha.setText("Mínimo 8 caracteres, uma letra maiúscula e um símbolo.");
				senhaValida = false;
			}
			else if (!passwordFieldConfirmacaoSenha.getText().equals(passwordFieldNovaSenha.getText())) {
				lblErroConfirmacaoSenha.setText("As senhas devem ser iguais!");
				senhaValida = false;
			}
			else {
				lblErroConfirmacaoSenha.setText("");
				senhaValida = true;
			}
			atualizarEstadoBotaoAvancar();
		});
	}
	
	private void atualizarEstadoBotaoAvancar() {
		btAvancar.setDisable(!senhaValida);		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();	
		validacaoSintaxeSenha();
		btAvancar.setDefaultButton(true);
	}
	
	private void padraoLarguraVBox() {
		vBox1NovaSenhaRecuperacao.prefWidthProperty().bind(hBoxPaiNovaSenhaRecuperacao.widthProperty().multiply(0.25));
		vBox2NovaSenhaRecuperacao.prefWidthProperty().bind(hBoxPaiNovaSenhaRecuperacao.widthProperty().multiply(0.75));		
	}

	@Override
	public void receberParametro(String parametro) {
		this.email = parametro;		
	}
}
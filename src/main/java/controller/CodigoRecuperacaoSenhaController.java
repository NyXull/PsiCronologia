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
import model.services.VerificacaoEmailService;
import util.ViewLoader;
import util.interfaces.ParametroRecebivel;

public class CodigoRecuperacaoSenhaController implements Initializable, ParametroRecebivel<String>{

	@FXML
	private HBox hBoxPaiCodigoRecuperacao;
	
	@FXML
	private VBox vBox1CodigoRecuperacao;
	
	@FXML
	private VBox vBox2CodigoRecuperacao;
	
	@FXML
	private TextField textFieldCodigo;
	
	@FXML
	private Label lblErroCodigo;
	
	@FXML
	private Button btAvancar;
	
	private String email;
	
	private boolean codigoValido = false;
	
	@FXML
	public void onBtAvancarAction() {
		String codigo = textFieldCodigo.getText();
		
		if (codigo.isEmpty()) {
			lblErroCodigo.setText("Insira um código válido!");
			codigoValido = false;
			return;
		}
		
		boolean codigoEhValido = validarCodigoComServico(codigo);
		
		if (codigoEhValido) {
			lblErroCodigo.setText("");
			codigoValido = true;
			ViewLoader.loadView("/fxml/nova-senha-recuperacao.fxml", "/css/nova-senha-recuperacao.css", email);
		}
		else {
			lblErroCodigo.setText("Código inválido ou expirado.");
			codigoValido = false;
		}	
	}
	
	private void validacaoCodigo() {
		textFieldCodigo.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
			if (textoNovo.isEmpty()) {
				lblErroCodigo.setText("Insira um código válido!");
				codigoValido = false;
			}
			else {				
				lblErroCodigo.setText("");
				codigoValido = true;				
			}
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();	
		validacaoCodigo();
		btAvancar.setDefaultButton(true);
	}
	
	private boolean validarCodigoComServico(String codigo) {
		VerificacaoEmailService service = new VerificacaoEmailService();
		return service.validarCodigo(codigo);
	}
	
	private void padraoLarguraVBox() {
		vBox1CodigoRecuperacao.prefWidthProperty().bind(hBoxPaiCodigoRecuperacao.widthProperty().multiply(0.25));
		vBox2CodigoRecuperacao.prefWidthProperty().bind(hBoxPaiCodigoRecuperacao.widthProperty().multiply(0.75));		
	}

	@Override
	public void receberParametro(String parametro) {
		this.email = parametro;		
	}
}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ViewLoader;

public class CadastroPacienteFinalizadoController implements Initializable{

	@FXML
	private HBox hBoxPaiCadastroPacienteFinalizado;
	
	@FXML
	private VBox vBox1CadastroPacienteFinalizado;
	
	@FXML
	private VBox vBox2CadastroPacienteFinalizado;
	
	@FXML
	private Button btHome;
	
	@FXML
	private Button btEditarPaciente;
	
	@FXML
	private Button btNovoPaciente;
	
	@FXML
	public void onBtHomeAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}
	
	@FXML
	public void onBtEditarPacienteAction() {
		ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
	}
	
	@FXML
	public void onBtNovoPacienteAction() {
		ViewLoader.loadView("/fxml/cadastro-paciente.fxml", "/css/cadastro-paciente.css");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1CadastroPacienteFinalizado.prefWidthProperty().bind(hBoxPaiCadastroPacienteFinalizado.widthProperty().multiply(0.25));
		vBox2CadastroPacienteFinalizado.prefWidthProperty().bind(hBoxPaiCadastroPacienteFinalizado.widthProperty().multiply(0.75));		
	}
}
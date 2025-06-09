package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class FinanceiroStatusController implements Initializable{

    @FXML
    private HBox hBoxPaiFinanceiroStatus;
       
    @FXML
    private VBox vBox1FinanceiroStatus;
    
    @FXML
    private VBox vBox2FinanceiroStatus;
    
    @FXML
    private Button btHome;
    
    @FXML
    private Button btNomeDoPaciente;
    
    @FXML
    private Button btProntuario;
    
    @FXML
    private Button btAgenda;
    
    @FXML
    private Button btRelatorios;
    
    @FXML
    private ComboBox comboBoxMesParaAlterar;
    
    @FXML
    private ComboBox comboBoxMesParaExcluir;
    
    @FXML
    private ComboBox comboBoxStatus;
    
    @FXML
    private Button btExcluir;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox1FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.25));
		vBox2FinanceiroStatus.prefWidthProperty().bind(hBoxPaiFinanceiroStatus.widthProperty().multiply(0.75));
		
		exibirNomePaciente();		
	}    

	@FXML
    private void onBtHomeAction() {
    	ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }
    
    @FXML
	private void onBtNomeDoPacienteAction() {
		ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
	}
	
	@FXML
	private void onBtProntuarioAction() {
		ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
	}
	
	@FXML
	private void onBtAgendaAction() {
		ViewLoader.loadView("/fxml/agenda-editar.fxml", "/css/agenda-editar.css");
	}
	
	@FXML
	private void onBtRelatoriosAction() {
		System.out.println("onBtRelatoriosAction");
	}
	
	@FXML
	private void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}
	
	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomeDoPaciente.setText(nomeFormatado.toString());		
	}
}
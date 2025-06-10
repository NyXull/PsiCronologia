package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class RelatorioController implements Initializable{

	@FXML
	private VBox vBox1Relatorios;
	
	@FXML
	private VBox vBox2Relatorios;
	
	@FXML
	private HBox hBoxPaiRelatorios;
	
    @FXML
    private Button btNomeDoPaciente;
    
    @FXML
    private Button btProntuario;
    
    @FXML
    private Button btAgenda;
    
    @FXML
    private Button btFinanceiro;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox1Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.25));
		vBox2Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.75));		
		
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
	private void onBtFinanceiroAction() {
		ViewLoader.loadView("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css");
	}
	
	 @FXML public void onBtSalvarAction() {
		 System.out.println("onBtSalvarAction"); 
	 }
	 		
	 @FXML public void onBtBaixarPdfAction() {
		 System.out.println("onBtBaixarPdfAction"); 
	 }
	 
	 @FXML public void onBtExcluirModeloAction() {
		 System.out.println("onBtExcluirModeloAction"); 
	 }
	 

	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomeDoPaciente.setText(nomeFormatado.toString());		
	}

}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class FinanceiroPagamentoController implements Initializable{

	@FXML
	private VBox vBox1FinanceiroPagamento;
	
	@FXML
	private VBox vBox2FinanceiroPagamento;
	
	@FXML
	private HBox hBoxPaiFinanceiroPagamento;
	
    @FXML
    private Button btNomeDoPacienteAqui;
    
    @FXML
    private Button btProntuario;
    
    @FXML
    private Button btAgenda;
    
    @FXML
    private Button btRelatorios;
    
    @FXML
    private TextField textFieldValorPorSessao;
    
    @FXML
    private TextField textFieldVencimento;
    
    @FXML
    private TextField textFieldQuantidadePorMes;
    
    @FXML
    private Label lblTotal;
    
    @FXML
    private ComboBox comboBoxStatus;
    
    @FXML
    private ComboBox comboBoxMes;
    
    @FXML
    private Button btSalvar;
    
    @FXML
    private Button btStatusMensal;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)		
		vBox1FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.25));
		vBox2FinanceiroPagamento.prefWidthProperty().bind(hBoxPaiFinanceiroPagamento.widthProperty().multiply(0.75));		
		
		exibirNomePaciente();
	}

	@FXML
	private void onBtHomeAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}
	
	@FXML
	private void onBtNomeDoPacienteAquiAction() {
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
		ViewLoader.loadView("/fxml/relatorio.fxml", "/css/relatorio.css");
	}
	
	@FXML
	private void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}
	
	@FXML
	private void onBtStatusMensalAction() {
		ViewLoader.loadView("/fxml/financeiro-status.fxml", "/css/financeiro-status.css");
	}
	
	@FXML
	private void onBtAlterarAction() {
		System.out.println("onBtAlterarAction");
	}
	
	@FXML
	private void onBtExcluirAction() {
		System.out.println("onBtExcluirAction");
	}
	
	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomeDoPacienteAqui.setText(nomeFormatado.toString());
	}
}

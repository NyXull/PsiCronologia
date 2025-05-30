package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class AgendaEditarController implements Initializable{

	 @FXML
	 private VBox vBox1AgendaEditar;
	 
	 @FXML
	 private VBox vBox2AgendaEditar;
	 
	 @FXML
	 private HBox hBoxPaiAgendaEditar;
	 
	 @FXML
	 private Button btInicio;
	 
	 @FXML
	 private Button btNomeDoPaciente;
	
	 @FXML
	 private Button btProntuario;
	 
	 @FXML
	 private Button btFinanceiro;
	 
	 @FXML
	 private Button btRelatorios;
	 
	 @FXML
	 private Button btBiblioteca;
	
	 @FXML
		public void onBtInicioAction() {
			ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
		}
	 
	 @FXML
		public void onbtNomeDoPaciente() {
			ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
		}
	
	 @FXML
	 public void onBtProntuario() {
		 ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
	 }
	 
	 @FXML
	 public void onBtFinanceiro() {
		 System.out.println("onBtFinanceiro");
	 }
	 
	 @FXML
	 public void onBtRelatorios() {
		 System.out.println("onBtRelatorios");
	 }
	 
	 @FXML
	 public void onBtBiblioteca() {
		 System.out.println("onBtBiblioteca");
	 }	 
	 
    public void initialize(URL url, ResourceBundle rb) {
    	vBox1AgendaEditar.prefWidthProperty().bind(hBoxPaiAgendaEditar.widthProperty().multiply(0.25));
    	vBox2AgendaEditar.prefWidthProperty().bind(hBoxPaiAgendaEditar.widthProperty().multiply(0.75));
    	
    	Paciente paciente = SessaoPaciente.getPaciente();
		 if (paciente != null) {
			 btNomeDoPaciente.setText(paciente.getNomePaciente());
		 }
    }
}
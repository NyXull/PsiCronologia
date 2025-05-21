package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Paciente;
import util.SessaoPaciente;

public class HomePacienteController implements Initializable{

	 @FXML
	 private VBox vBox1HomePaciente;
	 
	 @FXML
	 private VBox vBox2HomePaciente;
	 
	 @FXML
	 private HBox hBoxPaiHomePaciente;
	 
	 @FXML
	 private Text txtNomeDoPaciente;
	
	 @FXML
	 private Button btProntuario;
	 
	 @FXML
	 private Button btAgenda;
	 
	 @FXML
	 private Button btFinanceiro;
	 
	 @FXML
	 private Button btRelatorios;
	 
	 @FXML
	 private Button btBiblioteca;
	 
	 @FXML
	 public void onBtProntuario() {
		 System.out.println("onBtProntuario");
	 }
	 
	 @FXML
	 public void onBtAgenda() {
		 System.out.println("onBtAgenda");
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
	 
	 @Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		 vBox1HomePaciente.prefWidthProperty().bind(hBoxPaiHomePaciente.widthProperty().multiply(0.25));
		 vBox2HomePaciente.prefWidthProperty().bind(hBoxPaiHomePaciente.widthProperty().multiply(0.75));
		 
		 Paciente paciente = SessaoPaciente.getPaciente();
		 if (paciente != null) {
			 txtNomeDoPaciente.setText(paciente.getNomePaciente());
		 }
	}    
}
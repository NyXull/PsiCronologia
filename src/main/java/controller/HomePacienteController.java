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
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class HomePacienteController implements Initializable {

	@FXML
	private VBox vBox1HomePaciente;

	@FXML
	private VBox vBox2HomePaciente;

	@FXML
	private HBox hBoxPaiHomePaciente;

	@FXML
	private Button btInicio;

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
	private Button btNomeDoPaciente;

	@FXML
	public void onBtProntuario() {
		ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
	}

	@FXML
	public void onBtAgenda() {
		ViewLoader.loadView("/fxml/agenda-editar.fxml", "/css/agenda-editar.css");
	}

	@FXML
	public void onBtFinanceiro() {
		ViewLoader.loadView("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css");
	}

	@FXML
	public void onBtRelatorios() {
		ViewLoader.loadView("/fxml/relatorio.fxml", "/css/relatorio.css");
	}

	@FXML
	public void onBtInicioAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
		vBox1HomePaciente.prefWidthProperty().bind(hBoxPaiHomePaciente.widthProperty().multiply(0.25));
		vBox2HomePaciente.prefWidthProperty().bind(hBoxPaiHomePaciente.widthProperty().multiply(0.75));

		exibirNomePaciente();
	}

	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomeDoPaciente.setText(nomeFormatado.toString());
	}
}
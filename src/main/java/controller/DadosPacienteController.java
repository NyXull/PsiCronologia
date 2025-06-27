package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import model.services.PacienteService;
import util.Alerts;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class DadosPacienteController implements Initializable {

	@FXML
	private HBox hBoxPaiDadosPaciente;

	@FXML
	private VBox vBox1DadosPaciente;

	@FXML
	private VBox vBox2DadosPaciente;

	@FXML
	private Button btHome;

	@FXML
	private Button btNomePaciente;

	@FXML
	private TextField txtFieldNome;

	@FXML
	private TextField txtFieldEmail;

	@FXML
	private TextField txtFieldDataNasc;

	@FXML
	private TextField txtFieldCpf;

	@FXML
	private TextField txtFieldTelefone;

	@FXML
	private Button btEditar;

	@FXML
	private Button btExcluir;

	@FXML
	private GridPane gridDadosPaciente;

	@FXML
	public void onBtHomeAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}

	@FXML
	public void onBtNomePacienteAction() {
		ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
	}

	@FXML
	public void onBtEditarAction() {
		ViewLoader.loadView("/fxml/atualizar-dados-paciente.fxml", "/css/atualizar-dados-paciente.css");
	}

	@FXML
	public void onBtExcluirAction() {
		Paciente paciente = SessaoPaciente.getPaciente();
		PacienteService servicoPaciente = new PacienteService();

		if (paciente != null) {
			boolean excluido = Alerts.confirmarExclusaoPaciente(paciente, servicoPaciente);

			if (excluido) {
				ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
			} 
		}
		else {
			Alerts.showAlert("Erro", "Tente novamente, por favor.", null, Alert.AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();
		exibirNomePaciente();
		exibirDadosDoPaciente();
	}

	private void padraoLarguraVBox() {
		vBox1DadosPaciente.prefWidthProperty().bind(hBoxPaiDadosPaciente.widthProperty().multiply(0.25));
		vBox2DadosPaciente.prefWidthProperty().bind(hBoxPaiDadosPaciente.widthProperty().multiply(0.75));
	}

	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomePaciente.setText(nomeFormatado.toString());
	}

	private void exibirDadosDoPaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String nomePaciente = paciente.getNomePaciente();
		String emailPaciente = paciente.getEmailPaciente();
		Date dataNasc = paciente.getDataNascimento();
		String cfpPaciente = paciente.getCpf();
		String telefonePaciente = paciente.getTelefone();

		txtFieldNome.setText(nomePaciente);
		txtFieldEmail.setText(emailPaciente);
		String dataFormatada = sdf.format(dataNasc);
		txtFieldDataNasc.setText(dataFormatada);
		txtFieldCpf.setText(cfpPaciente);
		txtFieldTelefone.setText(telefonePaciente);
	}
}
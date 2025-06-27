package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entities.Paciente;
import model.services.PacienteService;
import util.Alerts;
import util.Constraints;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.ViewLoader;

public class AtualizarDadosPacienteController implements Initializable{

	@FXML
	private HBox hBoxPaiAtualizarDadosPaciente;
	
	@FXML
	private VBox vBox1AtualizarDadosPaciente;
	
	@FXML
	private VBox vBox2AtualizarDadosPaciente;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private Button btHome;
	
	@FXML
	private Button btNomePaciente;
	
	@FXML
    private TextField txtNomeAtualizar;
    
    @FXML
    private TextField txtEmailAtualizar;
    
    @FXML
    private Label labelErroEmailAtualizar;
    
    @FXML
    private TextField txtDataNascimentoAtualizar;
    
    @FXML
    private Label labelErroData;
    
    @FXML
    private TextField txtCPFAtualizar;
    
    @FXML
    private TextField txtTelefoneAtualizar;
    
    @FXML
    private Label labelSucesso;
	
	@FXML
	private Button btAtualizar;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private boolean emailValido = true;
	
	private boolean dataValida = true;
		
	@FXML
	private void onBtVoltarAction() {
		ViewLoader.loadView("/fxml/dados-paciente.fxml", "/css/dados-paciente.css");
	}
	
	@FXML
	private void onBtHomeAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}
	
	@FXML
	private void onBtNomePacienteAction() {
		ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
	}
	
	@FXML
	private void onBtAtualizarAction() {
		Paciente paciente = SessaoPaciente.getPaciente();
		
		if (!validarCampos()) return;
		
		try {
			paciente.setNomePaciente(txtNomeAtualizar.getText());
			paciente.setEmailPaciente(txtEmailAtualizar.getText());
			paciente.setCpf(txtCPFAtualizar.getText());
			paciente.setTelefone(txtTelefoneAtualizar.getText());
			
			Date dataNasc = sdf.parse(txtDataNascimentoAtualizar.getText());			
			paciente.setDataNascimento(dataNasc);
			
			new PacienteService().atualizarPacienteAtual(paciente);
			
			SessaoPaciente.setPaciente(paciente);
			
			labelSucesso.setText("Dados atualizados com sucesso!");
			
        } catch (Exception e) {
            Alerts.showAlert("Erro inesperado", "Erro ao atualizar paciente", "Tente novamente", AlertType.ERROR);
        }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();
		exibirNomePaciente();
		preencherCamposComDadosDoPaciente();
		iniciarValidacaoSintaxeEmailEDataNasc();
        btAtualizar.setDefaultButton(true);
	}
	
	private void padraoLarguraVBox() {
		vBox1AtualizarDadosPaciente.prefWidthProperty().bind(hBoxPaiAtualizarDadosPaciente.widthProperty().multiply(0.25));
		vBox2AtualizarDadosPaciente.prefWidthProperty().bind(hBoxPaiAtualizarDadosPaciente.widthProperty().multiply(0.75));
	}
	
	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomePaciente.setText(nomeFormatado.toString());
	}
	
	private void preencherCamposComDadosDoPaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		
		txtNomeAtualizar.setText(paciente.getNomePaciente());
		txtEmailAtualizar.setText(paciente.getEmailPaciente());
		txtCPFAtualizar.setText(paciente.getCpf());
		txtTelefoneAtualizar.setText(paciente.getTelefone());
		txtDataNascimentoAtualizar.setText(sdf.format(paciente.getDataNascimento()));
	}
	
	private void iniciarValidacaoSintaxeEmailEDataNasc() {
		txtEmailAtualizar.focusedProperty().addListener((obs, estavaFocado, estaFocado) -> {
			if (!estaFocado) {
				String email = txtEmailAtualizar.getText();
				if (email.isEmpty()) {
					labelErroEmailAtualizar.setText("");
					emailValido = true;
				}
				else if (!Constraints.validacaoSintaxeEmail(email)) {
					labelErroEmailAtualizar.setText("Email inválido");
					emailValido = false;
				}
				else {
					labelErroEmailAtualizar.setText("");
					emailValido = true;
				}
				atualizarEstadoBotaoAtualizar();
			}
		});
		
		txtDataNascimentoAtualizar.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if (valorNovo.isEmpty()) {
				labelErroData.setText("");
				dataValida = true;
			}
			else if (!valorNovo.matches("\\d{2}/\\d{2}/\\d{4}")) {
				labelErroData.setText("Data inválida. Use o formato do exemplo: 01/01/2025");
				dataValida = false;
			}
			else {
				labelErroData.setText("");
				dataValida = true;
			}
			atualizarEstadoBotaoAtualizar();
		});
	}
	
	private void atualizarEstadoBotaoAtualizar() {
		btAtualizar.setDisable(!emailValido || !dataValida);		
	}

	private boolean validarCampos() {
		if (txtNomeAtualizar.getText().isEmpty() || txtEmailAtualizar.getText().isEmpty() || 
				txtCPFAtualizar.getText().isEmpty() || txtTelefoneAtualizar.getText().isEmpty()
				|| txtDataNascimentoAtualizar.getText().isEmpty()) {
			
			Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha todos os campos.", AlertType.ERROR);
			return false;
		}
		
		if (!emailValido) {
			 Alerts.showAlert("Erro de Validação", "Email inválido!", "Corrija o endereço de email.", AlertType.ERROR);
	            return false;
		}
		return true;
	}
}
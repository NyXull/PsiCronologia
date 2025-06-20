package controller;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Paciente;
import model.entities.Psicologo;
import model.services.PacienteService;
import util.SessaoPaciente;
import util.SessaoUsuario;
import util.ViewLoader;

public class HomeController implements Initializable {
    
	@FXML
	private HBox hBoxPaiHome;
	
	@FXML
	private VBox vBox1Home;
	
	@FXML
	private VBox vBox2Home;
	
	@FXML
	private Button btAgenda;
	
	@FXML
	private Button btBiblioteca;
	
	@FXML
	private Button btSair;
	
	@FXML
	private Button btAdicionar;
	
	@FXML
	private TextField txtPesquisar;
	
	@FXML
	private ListView<Paciente> listViewPacientes;
	
	private ObservableList<Paciente> obsPacientes;
	
	private FilteredList<Paciente> pacientesFiltrados;
	
	@FXML
	private void onBtAgendaAction() {
		ViewLoader.loadView("/fxml/agenda-visualizacao.fxml", "/css/agenda-visualizacao.css");
	}
	
	@FXML
	private void onBtBibliotecaAction() {
		ViewLoader.loadView("/fxml/biblioteca.fxml", "/css/biblioteca.css");
	}
	
	@FXML
	private void onBtSairAction() {
		SessaoUsuario.encerrarSessao();
	    ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
	}
	
	@FXML
	private void onBtAdicionarAction() {
		ViewLoader.loadView("/fxml/cadastro-paciente.fxml", "/css/cadastro-paciente.css");
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	padraoLarguraVBox();
    	    	
    	iniciarListaPacientes();    	
    }

	private void iniciarListaPacientes() {
		// Busca pacientes pelo ID do psicólogo
    	int idPsicologoLogado = SessaoUsuario.getPsicologoLogado().getIdPsico();
    	
    	PacienteService pacienteService = new PacienteService();
    	
    	List<Paciente> pacientes = pacienteService.listarPorPsicologo(idPsicologoLogado);
    	
    	Collections.sort(pacientes, Comparator.comparing(Paciente::getNomePaciente, String.CASE_INSENSITIVE_ORDER));
    	
    	obsPacientes = FXCollections.observableArrayList(pacientes);
    	
    	//Lista filtrável com base na lista original
    	pacientesFiltrados = new FilteredList<>(obsPacientes, p -> true);
    	
    	txtPesquisar.textProperty().addListener((obs, antigoValor, novoValor) -> {
    		pacientesFiltrados.setPredicate(paciente -> {
    			if (novoValor == null || novoValor.isBlank()) return true;
    			
    			String nomeLower = paciente.getNomePaciente().toLowerCase();
    			
    			return nomeLower.contains(novoValor.toLowerCase());
    		});
    	});
    	
    	pacientesFiltrados.addListener((ListChangeListener<Paciente>) change -> {
    		if (pacientesFiltrados.isEmpty()) {
    			Text placeholderText = new Text("Nenhum paciente encontrado!");
    			placeholderText.setStyle("-fx-font-family: 'Nunito'; -fx-font-size: 13pt; -fx-fill: #5F3AFC; -fx-font-style: italic;");
    			listViewPacientes.setPlaceholder(placeholderText);
    		}
    		else {
    			listViewPacientes.setPlaceholder(null);
    		}
    	});
    	
    	//Pacientes filtrados na ListView    	
    	listViewPacientes.setItems(pacientesFiltrados);
    	
    	// Mostra só o nome na ListView
    	listViewPacientes.setCellFactory(lv -> new ListCell<>() {
    		@Override
    		protected void updateItem(Paciente paciente, boolean empty) {
    			super.updateItem(paciente, empty);
    			if (empty || paciente == null) {
    				setText(null);
    			} 
    			else {
    				setText(paciente.getNomePaciente());
    			}
    		}
    	});
    	
    	// Deixa o nome/item clicável
    	listViewPacientes.setOnMouseClicked(event -> {
    		if (event.getClickCount() == 1) {
    			Paciente pacienteSelecionado = listViewPacientes.getSelectionModel().getSelectedItem();
    			if (pacienteSelecionado != null) {
    				SessaoPaciente.setPaciente(pacienteSelecionado);
    				ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
    			}
    		}
    	});		
	}
	
	private void padraoLarguraVBox() {
		vBox1Home.prefWidthProperty().bind(hBoxPaiHome.widthProperty().multiply(0.25));
    	vBox2Home.prefWidthProperty().bind(hBoxPaiHome.widthProperty().multiply(0.75));
	}
}
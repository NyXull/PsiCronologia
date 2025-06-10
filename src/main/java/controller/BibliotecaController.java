package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.SessaoUsuario;
import util.ViewLoader;

public class BibliotecaController implements Initializable{

    @FXML
    private HBox hBoxPaiBiblioteca;
    
    @FXML
    private VBox vBox1Biblioteca;
    
    @FXML
    private VBox vBox2Biblioteca;
    
    @FXML
    private Button btInicio;
    
    @FXML
    private Button btAgenda;
    
    @FXML
    private Button btSair;
    
    @FXML
    private Button btAdicionar;
    
    @FXML
    private Button btSalvar;
    
    @FXML
    private TableView tableArquivos;
    
    @FXML
	public void onBtInicioAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}
    
    @FXML
	public void onBtAgendaAction() {
    	ViewLoader.loadView("/fxml/agenda-visualizacao.fxml", "/css/agenda-visualizacao.css");
	}
    
    @FXML
	private void onBtSairAction() {
		SessaoUsuario.encerrarSessao();
	    ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
	}
    
    @FXML
	private void onBtAdicionarAction() {
		System.out.println("onBtAdicionarAction");
	}
    
    @FXML
	private void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		padraoLarguraVBox();
		
	}

	private void padraoLarguraVBox() {
		vBox1Biblioteca.prefWidthProperty().bind(hBoxPaiBiblioteca.widthProperty().multiply(0.25));
		vBox2Biblioteca.prefWidthProperty().bind(hBoxPaiBiblioteca.widthProperty().multiply(0.75));		
	}
}

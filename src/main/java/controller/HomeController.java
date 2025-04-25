package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private void onBtAgendaAction() {
		System.out.println("onBtAgendaAction");
	}
	
	@FXML
	private void onBtBibliotecaAction() {
		System.out.println("onBtBibliotecaAction");
	}
	
	@FXML
	private void onBtSairAction() {
		System.out.println("onBtSairAction");
	}
	
	@FXML
	private void onBtAdicionarAction() {
		System.out.println("onBtAdicionarAction");
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
    	vBox1Home.prefWidthProperty().bind(hBoxPaiHome.widthProperty().multiply(0.25));
    	vBox2Home.prefWidthProperty().bind(hBoxPaiHome.widthProperty().multiply(0.75)); 
    }
}
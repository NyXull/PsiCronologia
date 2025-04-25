package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.ViewLoader;

public class LoginController2 implements Initializable {

    @FXML
    private VBox vBox1Login2;

    @FXML
    private VBox vBox2Login2;

    @FXML
    private HBox hBoxPaiLogin2;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btEntrar;

    @FXML
    private Text txtLogin;
    
    @FXML
    public void onBtEntrarAction() {
    	ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }
    
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    	// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
    	vBox1Login2.prefWidthProperty().bind(hBoxPaiLogin2.widthProperty().multiply(0.25));
    	vBox2Login2.prefWidthProperty().bind(hBoxPaiLogin2.widthProperty().multiply(0.75));        
    } 
}
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

public class LoginController implements Initializable {

    @FXML
    private VBox vbox1Login;

    @FXML
    private VBox vbox2Login;

    @FXML
    private HBox hboxLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btCadastro;

    @FXML
    private Button btEntrar;

    @FXML
    private Text txtLogin;
    
    @FXML
    public void onBtCadastroAction() {
    	System.out.println("onBtCadastroAction");    	
    }
    
    @FXML
    public void onBtEntrarAction() {
    	System.out.println("onBtEntrarAction");
    }
    
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    	// Largura proporcional para vbox1 (1/4) e vbox2 (3/4)
        vbox1Login.prefWidthProperty().bind(hboxLogin.widthProperty().multiply(0.25));
        vbox2Login.prefWidthProperty().bind(hboxLogin.widthProperty().multiply(0.75));        
    } 
}
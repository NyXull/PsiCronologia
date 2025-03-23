package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.TrocarCena;

import java.io.IOException;

public class LoginController {

    public Label labelPopup;
    public Text textPopup1;
    public Text textPopup2;
    public ImageView imageViewPopup;
    private boolean isPopupVisivel = false;

    @FXML
    private void initialize() {
        if (labelPopup != null) {
            ocultarPopup();
        }
    }

    @FXML
    private void navegarParaCadastro(ActionEvent evento) throws IOException {
        TrocarCena.trocarCena("/fxml/cadastro.fxml", "/css/cadastro.css", evento);
    }

    @FXML
    private void navegarParaHome(ActionEvent evento) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", evento);
    }

    @FXML
    private void alternarVisibilidadePopup() {
        if (isPopupVisivel) {
            ocultarPopup();
        } else {
            exibirPopup();
        }
        isPopupVisivel = !isPopupVisivel;
    }

    private void exibirPopup() {
        labelPopup.setVisible(true);
        textPopup1.setVisible(true);
        textPopup2.setVisible(true);
        imageViewPopup.setVisible(true);
    }

    private void ocultarPopup() {
        labelPopup.setVisible(false);
        textPopup1.setVisible(false);
        textPopup2.setVisible(false);
        imageViewPopup.setVisible(false);
    }
}

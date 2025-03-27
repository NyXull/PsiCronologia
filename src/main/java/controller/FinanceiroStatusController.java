package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.TrocarCena;

import java.io.IOException;

public class FinanceiroStatusController {

    public Button botaoExcluir;

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @FXML
    private void navegarParaFinanceiroPagamento(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css", event);
    }


    @FXML
    private void mostrarPopupExcluir() {
        Popup popup = criarPopup();
        Stage stage = (Stage) botaoExcluir.getScene().getWindow();
        Bounds bounds = calcularPosicaoInicial(botaoExcluir, popup);

        configurarListenersDeMovimento(stage, botaoExcluir, popup);
        popup.show(stage, bounds.getMinX(), bounds.getMinY());
    }

    private Popup criarPopup() {
        Popup popup = new Popup();
        VBox layout = criarLayout();
        popup.getContent().add(layout);
        return popup;
    }

    private VBox criarLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getStyleClass().add("layout");

        Label pergunta = new Label("CERTEZA QUE DESEJA EXCLUIR?");
        pergunta.getStyleClass().add("labelPergunta");

        HBox botoesLayout = criarBotoesLayout();
        layout.getChildren().addAll(pergunta, botoesLayout);

        return layout;
    }

    private HBox criarBotoesLayout() {
        HBox botoesLayout = new HBox(10);
        botoesLayout.setAlignment(Pos.CENTER);

        Button botaoSim = criarBotao("Sim", "botaoSim", e -> ((Popup) ((Node) e.getSource()).getScene().getWindow()).hide());
        Button botaoNao = criarBotao("Não", "botaoNao", e -> ((Popup) ((Node) e.getSource()).getScene().getWindow()).hide());

        botoesLayout.getChildren().addAll(botaoSim, botaoNao);
        return botoesLayout;
    }

    private Button criarBotao(String texto, String estilo, EventHandler<ActionEvent> acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add(estilo);
        botao.setOnAction(acao);
        return botao;
    }

    private Bounds calcularPosicaoInicial(Node referencia, Popup popup) {
        Bounds bounds = referencia.localToScreen(referencia.getBoundsInLocal());
        double larguraReferencia = referencia.getBoundsInLocal().getWidth();
        double alturaReferencia = referencia.getBoundsInLocal().getHeight();
        double x = bounds.getMinX() + (larguraReferencia / 2) - (popup.getWidth() / 2);
        double y = bounds.getMinY() + alturaReferencia;
        return new BoundingBox(x, y, 0, 0);
    }

    private void configurarListenersDeMovimento(Stage stage, Node referencia, Popup popup) {
        stage.xProperty().addListener((obs, oldVal, newVal) -> {
            Bounds newBounds = referencia.localToScreen(referencia.getBoundsInLocal());
            double larguraReferencia = referencia.getBoundsInLocal().getWidth();
            double newX = newBounds.getMinX() + (larguraReferencia / 2) - (popup.getWidth() / 2);
            popup.setX(newX);
        });

        stage.yProperty().addListener((obs, oldVal, newVal) -> {
            Bounds newBounds = referencia.localToScreen(referencia.getBoundsInLocal());
            double alturaReferencia = referencia.getBoundsInLocal().getHeight();
            double newY = newBounds.getMinY() + alturaReferencia;
            popup.setY(newY);
        });
    }

}

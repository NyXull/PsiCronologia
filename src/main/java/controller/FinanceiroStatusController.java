package controller;

import javafx.beans.value.ChangeListener;
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
    private Popup popupExcluir;
    private ChangeListener<Number> stageXListener;
    private ChangeListener<Number> stageYListener;

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @FXML
    private void navegarParaFinanceiroPagamento(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css", event);
    }

    @FXML
    private void navegarParaRelatorio(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/relatorio.fxml", "/css/relatorio.css", event);
    }

    @FXML
    private void navegarParaBiblioteca(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/biblioteca.fxml", "/css/biblioteca.css", event);
    }

    @FXML
    private void mostrarPopupExcluir() {
        if (popupExcluir != null && popupExcluir.isShowing()) {
            fecharPopup();
        }

        popupExcluir = criarPopup();
        Stage stage = (Stage) botaoExcluir.getScene().getWindow();
        Bounds bounds = calcularPosicaoInicial(botaoExcluir, popupExcluir);

        configurarListenersDeMovimento(stage, botaoExcluir, popupExcluir);
        popupExcluir.show(stage, bounds.getMinX(), bounds.getMinY());
    }


    private Popup criarPopup() {
        Popup popup = new Popup();
        VBox layout = criarLayout();
        popup.getContent().add(layout);
        popupExcluir = popup;
        return popupExcluir;
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

        Button botaoSim = criarBotao("Sim", "botaoSim", e -> ((Node) e.getSource()).getScene().getWindow().hide());
        Button botaoNao = criarBotao("Não", "botaoNao", e -> ((Node) e.getSource()).getScene().getWindow().hide());

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
        stageXListener = (_, _, _) -> {
            Bounds newBounds = referencia.localToScreen(referencia.getBoundsInLocal());
            if (newBounds == null) return;

            double larguraReferencia = referencia.getBoundsInLocal().getWidth();
            double newX = newBounds.getMinX() + (larguraReferencia / 2) - (popup.getWidth() / 2);
            popup.setX(newX);
        };

        stageYListener = (_, _, _) -> {
            Bounds newBounds = referencia.localToScreen(referencia.getBoundsInLocal());
            if (newBounds == null) return;

            double alturaReferencia = referencia.getBoundsInLocal().getHeight();
            double newY = newBounds.getMinY() + alturaReferencia;
            popup.setY(newY);
        };

        stage.xProperty().addListener(stageXListener);
        stage.yProperty().addListener(stageYListener);
    }

    private void fecharPopup() {
        if (popupExcluir != null && popupExcluir.isShowing()) {
            popupExcluir.hide();
            Stage stage = (Stage) botaoExcluir.getScene().getWindow();
            stage.xProperty().removeListener(stageXListener);
            stage.yProperty().removeListener(stageYListener);
            popupExcluir = null;
        }
    }

}

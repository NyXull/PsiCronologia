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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.entities.TrocarCena;

import java.io.IOException;

public class BibliotecaController {

    public ScrollPane scrollPane;
    private Popup popupArquivo;
    private ChangeListener<Number> stageXListener;
    private ChangeListener<Number> stageYListener;
    private ChangeListener<Number> scrollListener;

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
    private void navegarParaAgenda(ActionEvent event) throws IOException {
        fecharPopup();
        TrocarCena.trocarCena("/fxml/agenda-visualizacao.fxml", "/css/agenda-visualizacao.css", event);
    }

    @FXML
    private void mostrarPopupArquivo(ActionEvent event) {
        Button botao = (Button) event.getSource();

        if (popupArquivo != null && popupArquivo.isShowing()) {
            fecharPopup();
        }

        popupArquivo = criarPopup();
        Stage stage = (Stage) botao.getScene().getWindow();
        Bounds bounds = calcularPosicaoInicial(botao, popupArquivo);

        configurarListenersDeMovimento(stage, botao, popupArquivo);
        popupArquivo.show(stage, bounds.getMinX(), bounds.getMinY());

        scrollPane.vvalueProperty().addListener((_, _, _) -> {
            if (popupArquivo != null && popupArquivo.isShowing()) {
                Bounds newBounds = botao.localToScreen(botao.getBoundsInLocal());
                if (newBounds == null) return;

                double alturaReferencia = botao.getBoundsInLocal().getHeight();
                double newY = newBounds.getMinY() + alturaReferencia;

                if (newY < 150 || newY > 500) {
                    if (popupArquivo.isShowing()) {
                        popupArquivo.hide();
                        popupArquivo = null;
                    }
                }
            }
        });

    }

    private Popup criarPopup() {
        Popup popup = new Popup();
        VBox layout = criarLayout();
        popup.getContent().add(layout);
        popupArquivo = popup;
        return popupArquivo;
    }

    private VBox criarLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(0));
        layout.getStyleClass().add("layout");
        layout.setFillWidth(true);

        VBox botoesLayout = criarBotoesLayout();
        layout.getChildren().addAll(botoesLayout);

        return layout;
    }

    private VBox criarBotoesLayout() {
        VBox botoesLayout = new VBox(0);
        botoesLayout.setAlignment(Pos.CENTER);
        botoesLayout.setFillWidth(true);

        Button botaoAbrir = criarBotao("Abrir", e -> ((Node) e.getSource()).getScene().getWindow().hide());
        Button botaoCopiar = criarBotao("Copiar", e -> ((Node) e.getSource()).getScene().getWindow().hide());
        Button botaoExcluir = criarBotao("Excluir", e -> ((Node) e.getSource()).getScene().getWindow().hide());
        Button botaoRenomear = criarBotao("Renomear", e -> ((Node) e.getSource()).getScene().getWindow().hide());

        botoesLayout.getChildren().addAll(botaoAbrir, botaoCopiar, botaoExcluir, botaoRenomear);

        return botoesLayout;
    }

    private Button criarBotao(String texto, EventHandler<ActionEvent> acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add("botaoPopup");
        botao.setOnAction(acao);
        botao.setMaxWidth(Double.MAX_VALUE);
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
        if (popupArquivo != null && popupArquivo.isShowing()) {
            popupArquivo.hide();

            Window window = popupArquivo.getScene().getWindow();
            if (window instanceof Stage stage) {
                stage.xProperty().removeListener(stageXListener);
                stage.yProperty().removeListener(stageYListener);
                scrollPane.vvalueProperty().removeListener(scrollListener);
            }

            popupArquivo = null;
        }
    }

}

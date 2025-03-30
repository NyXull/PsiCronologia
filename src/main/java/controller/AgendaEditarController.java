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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.TrocarCena;

import java.io.IOException;

public class AgendaEditarController {

    @FXML
    private Pane containerDias;

    @FXML
    private Pane containerHorarios;

    private Popup popupTravarAgenda;
    private ChangeListener<Number> stageXListener;
    private ChangeListener<Number> stageYListener;

    @FXML
    private Pane areaDiasMes;

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

    public void initialize() {
        criarBotoesDias();

        criarDiasDaSemana();

        criarHorarios();
    }

    private void criarDiasDaSemana() {
        String[] diasSemana = {"DOM.", "SEG.", "TER.", "QUA.", "QUI.", "SEX.", "SAB."};
        String[] diasMes = {"10", "11", "12", "13", "14", "15", "16"};

        for (int i = 0; i < diasSemana.length; i++) {
            Pane paneDia = new Pane();
            paneDia.getStyleClass().add("quadrado-dia");
            paneDia.setPrefSize(50, 50);
            paneDia.setLayoutX(i * 50);

            Text textoDiaSemana = new Text(diasSemana[i]);
            textoDiaSemana.getStyleClass().add("dia-semana");
            textoDiaSemana.setLayoutX(9);
            textoDiaSemana.setLayoutY(20);

            Text textoDiaMes = new Text(diasMes[i]);
            textoDiaMes.getStyleClass().add("dia-mes");
            textoDiaMes.setLayoutX(11);
            textoDiaMes.setLayoutY(40);

            paneDia.getChildren().addAll(textoDiaSemana, textoDiaMes);

            containerDias.getChildren().add(paneDia);
        }
    }

    private void criarHorarios() {
        int numHorarios = 10;
        int colunas = 7;

        for (int i = 0; i < numHorarios; i++) {
            for (int j = 0; j < colunas; j++) {

                Pane paneHorario = new Pane();
                paneHorario.getStyleClass().add("quadrado-horario");
                paneHorario.setPrefSize(50, 50);
                paneHorario.setLayoutX(j * 50);
                paneHorario.setLayoutY(i * 50);

                Button botaoTravar = new Button();
                botaoTravar.setPrefSize(45, 45);
                botaoTravar.getStyleClass().add("botao-travar-agenda");
                botaoTravar.setLayoutX(2.5);
                botaoTravar.setLayoutY(2.5);
                botaoTravar.setOnAction(_ -> mostrarPopupTravarAgenda(botaoTravar));

                Text textoHora = new Text(String.format("%02d:00", 8 + i));
                textoHora.getStyleClass().add("hora");
                textoHora.setLayoutX(12);
                textoHora.setLayoutY(15);

                Text textoPaciente = new Text("-");
                textoPaciente.getStyleClass().add("nome-paciente");
                textoPaciente.setLayoutX(15);
                textoPaciente.setLayoutY(40);

                paneHorario.getChildren().addAll(botaoTravar, textoHora, textoPaciente);

                containerHorarios.getChildren().add(paneHorario);
            }
        }
    }

    private void criarBotoesDias() {
        int x = 15;
        int y = 0;
        int dia = 1;

        int primeiroDiaSemana = 3;

        x += primeiroDiaSemana * 40;

        for (int semana = 0; semana < 5; semana++) {
            for (int diaSemana = 0; diaSemana < 7; diaSemana++) {
                if (dia > 31) break;

                if (dia == 1 && semana == 0 && diaSemana < primeiroDiaSemana) {
                    continue;
                }

                Button botao = new Button(dia > 0 ? String.valueOf(dia) : "-");
                botao.setPrefHeight(30);
                botao.setPrefWidth(30);
                botao.setLayoutX(x);
                botao.setLayoutY(y);
                if (dia > 0) {
                    botao.getStyleClass().add("botao-numero-dia-semana");
                    botao.setOnAction(_ -> selecionarDia(botao));
                } else {
                    botao.getStyleClass().add("numero-dia-semana");
                }

                if (Math.abs(x - 15) < 40) {
                    botao.getStyleClass().add("domingo");
                }

                areaDiasMes.getChildren().add(botao);

                x += 40;
                dia++;

                if (diaSemana == 6) {
                    x = 15;
                    y += 35;
                }
            }
        }
    }

    private void selecionarDia(Button botao) {
        for (Node node : areaDiasMes.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("selecionado");
            }
        }

        if (!botao.getStyleClass().contains("selecionado") && !botao.getStyleClass().contains("domingo")) {
            botao.getStyleClass().add("selecionado");
        }
    }

    private void mostrarPopupTravarAgenda(Button botaoClicado) {
        if (popupTravarAgenda != null && popupTravarAgenda.isShowing()) {
            fecharPopup(botaoClicado);
        }

        popupTravarAgenda = criarPopup(botaoClicado);
        Stage stage = (Stage) botaoClicado.getScene().getWindow();
        Bounds bounds = calcularPosicaoInicial(botaoClicado, popupTravarAgenda);

        configurarListenersDeMovimento(stage, botaoClicado, popupTravarAgenda);
        popupTravarAgenda.show(stage, bounds.getMinX(), bounds.getMinY());
    }

    private Popup criarPopup(Button botaoClicado) {
        Popup popup = new Popup();
        VBox layout = criarLayout(botaoClicado);
        popup.getContent().add(layout);
        popupTravarAgenda = popup;
        return popupTravarAgenda;
    }

    private VBox criarLayout(Button botaoClicado) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getStyleClass().add("layout");

        Label pergunta = new Label("TRAVAR AGENDA?");
        pergunta.getStyleClass().add("labelPergunta");

        HBox botoesLayout = criarBotoesLayout(botaoClicado);
        layout.getChildren().addAll(pergunta, botoesLayout);

        return layout;
    }

    private HBox criarBotoesLayout(Button botaoClicado) {
        HBox botoesLayout = new HBox(10);
        botoesLayout.setAlignment(Pos.CENTER);

        Button botaoSim = criarBotao("Sim", "botaoSim", e -> {
            Pane quadrado = (Pane) botaoClicado.getParent();
            quadrado.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35)");

            ((Node) e.getSource()).getScene().getWindow().hide();
        });

        Button botaoNao = criarBotao("Não", "botaoNao", e -> {
            Pane quadrado = (Pane) botaoClicado.getParent();
            quadrado.setStyle("-fx-background-color: transparent;");

            ((Node) e.getSource()).getScene().getWindow().hide();
        });

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

    private void fecharPopup(Button botaoClicado) {
        if (popupTravarAgenda != null && popupTravarAgenda.isShowing()) {
            popupTravarAgenda.hide();
            Stage stage = (Stage) botaoClicado.getScene().getWindow();
            stage.xProperty().removeListener(stageXListener);
            stage.yProperty().removeListener(stageYListener);
            popupTravarAgenda = null;
        }
    }

    private void fecharPopup() {
        if (popupTravarAgenda != null && popupTravarAgenda.isShowing()) {
            popupTravarAgenda.hide();

            Window window = popupTravarAgenda.getScene().getWindow();
            if (window instanceof Stage stage) {
                stage.xProperty().removeListener(stageXListener);
                stage.yProperty().removeListener(stageYListener);
            }

            popupTravarAgenda = null;
        }
    }

}

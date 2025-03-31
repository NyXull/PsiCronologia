package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.TrocarCena;

import java.io.IOException;

public class AgendaVisualizacaoController {

    @FXML
    private Pane containerDias;

    @FXML
    private Pane containerHorarios;

    @FXML
    private Pane areaDiasMes;

    @FXML
    private void navegarParaHome(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/home.fxml", "/css/home.css", event);
    }

    @FXML
    private void navegarParaLogin(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/login.fxml", "/css/login.css", event);
    }

    @FXML
    private void navegarParaAgenda(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/agenda-visualizacao.fxml", "/css/agenda-visualizacao.css", event);
    }

    @FXML
    private void navegarParaBiblioteca(ActionEvent event) throws IOException {
        TrocarCena.trocarCena("/fxml/biblioteca.fxml", "/css/biblioteca.css", event);
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

                Text textoHora = new Text(String.format("%02d:00", 8 + i));
                textoHora.getStyleClass().add("hora");
                textoHora.setLayoutX(12);
                textoHora.setLayoutY(15);

                Text textoPaciente = new Text("-");
                textoPaciente.getStyleClass().add("nome-paciente");
                textoPaciente.setLayoutX(15);
                textoPaciente.setLayoutY(40);

                paneHorario.getChildren().addAll(textoHora, textoPaciente);

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

}

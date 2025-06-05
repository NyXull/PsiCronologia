package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.enums.TipoCancelamento;
import util.enums.TipoRecorrencia;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("show-alert");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image(Alerts.class.getResourceAsStream("/img/icon_exclamacao.png"));
		stage.getIcons().add(icon);

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), e -> alert.getDialogPane().setTranslateX(0)),
				new KeyFrame(Duration.millis(50), e -> alert.getDialogPane().setTranslateX(4)),
				new KeyFrame(Duration.millis(100), e -> alert.getDialogPane().setTranslateX(-4)),
				new KeyFrame(Duration.millis(150), e -> alert.getDialogPane().setTranslateX(2)),
				new KeyFrame(Duration.millis(200), e -> alert.getDialogPane().setTranslateX(-2)),
				new KeyFrame(Duration.millis(250), e -> alert.getDialogPane().setTranslateX(1)),
				new KeyFrame(Duration.millis(300), e -> alert.getDialogPane().setTranslateX(0)));
		timeline.play();

		alert.show();
	}

	public static Optional<TipoRecorrencia> mostrarPopupAgendamentoCompleto(String hora, String data, String nomePaciente) {
		
		while (true) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Agendamento de horário");
			alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora +
					"\n\nDeseja agendar esse horário para " + nomePaciente + "?");

			alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
			alert.getDialogPane().getStyleClass().add("mostrarPopupAgendamentoCompleto");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			Image icon = new Image(Alerts.class.getResourceAsStream("/img/icon_calendario.png"));
			stage.getIcons().add(icon);

			ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
			ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
			alert.getButtonTypes().setAll(botaoSim, botaoNao);

			Optional<ButtonType> resultAgendar = alert.showAndWait();
			if (resultAgendar.isEmpty() || resultAgendar.get() != botaoSim) {
				return Optional.of(TipoRecorrencia.CANCELADO);
			}

			alert.setTitle("Agendamento recorrente");
			alert.setHeaderText("Deseja tornar esse horário recorrente?");
			alert.setContentText(null);
			alert.getButtonTypes().setAll(botaoSim, botaoNao);

			Optional<ButtonType> resultRecorrente = alert.showAndWait();
			if (resultRecorrente.isEmpty() || resultRecorrente.get() != botaoSim) {
				return Optional.of(TipoRecorrencia.PONTUAL);
			}

			ButtonType semanal = new ButtonType("Semanal", ButtonBar.ButtonData.OK_DONE);
			ButtonType quinzenal = new ButtonType("Quinzenal", ButtonBar.ButtonData.OK_DONE);
			ButtonType mensal = new ButtonType("Mensal", ButtonBar.ButtonData.OK_DONE);
			ButtonType voltar = new ButtonType("Voltar", ButtonBar.ButtonData.CANCEL_CLOSE);

			alert.setTitle("Tipo de recorrência");
			alert.setHeaderText("Escolha o tipo de recorrência:");
			alert.getButtonTypes().setAll(semanal, quinzenal, mensal, voltar);

			Optional<ButtonType> resultTipo = alert.showAndWait();
			if (resultTipo.isEmpty() || resultTipo.get() == voltar) {
				continue;
			}

			if (resultTipo.get() == semanal) return Optional.of(TipoRecorrencia.SEMANAL);
			if (resultTipo.get() == quinzenal) return Optional.of(TipoRecorrencia.QUINZENAL);
			if (resultTipo.get() == mensal)	return Optional.of(TipoRecorrencia.MENSAL);

			return Optional.of(TipoRecorrencia.CANCELADO);
		}
	}
	
	public static void agendamentoRealizadoComSucesso(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("agendamentoRealizadoComSucesso");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image(Alerts.class.getResourceAsStream("/img/icon_calendario.png"));
		stage.getIcons().add(icon);		

		alert.show();
	}	
	
	public static Optional<TipoCancelamento> confirmarCancelamentoDoAgendamento(String hora, String data, String nomePaciente, boolean ehRecorrente) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Cancelamento de horário");
		alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora);

		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("confirmarCancelamentoDoAgendamento");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image(Alerts.class.getResourceAsStream("/img/icon_cancelar.png"));
		stage.getIcons().add(icon);		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), e -> alert.getDialogPane().setTranslateX(0)),
				new KeyFrame(Duration.millis(50), e -> alert.getDialogPane().setTranslateX(4)),
				new KeyFrame(Duration.millis(100), e -> alert.getDialogPane().setTranslateX(-4)),
				new KeyFrame(Duration.millis(150), e -> alert.getDialogPane().setTranslateX(2)),
				new KeyFrame(Duration.millis(200), e -> alert.getDialogPane().setTranslateX(-2)),
				new KeyFrame(Duration.millis(250), e -> alert.getDialogPane().setTranslateX(1)),
				new KeyFrame(Duration.millis(300), e -> alert.getDialogPane().setTranslateX(0)));
		timeline.play();
		
		ButtonType botaoCancelar = new ButtonType("Cancelar");
		ButtonType apenasEsse = new ButtonType("Apenas este");
        ButtonType todaRecorrencia = new ButtonType("Toda recorrência");
        ButtonType botaoSim = new ButtonType("Sim");
		
		if (ehRecorrente) {
			alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora +
					"\n\nDeseja cancelar apenas esse horário ou toda a recorrência para " + nomePaciente + "?");
			alert.getButtonTypes().setAll(apenasEsse, todaRecorrencia, botaoCancelar);
		}
		else {
			alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora +
					"\n\nDeseja cancelar esse horário para " + nomePaciente + "?");
			alert.getButtonTypes().setAll(botaoSim, botaoCancelar);
		}
		
		Optional<ButtonType> resposta = alert.showAndWait();
		
		if (resposta.isEmpty() || resposta.get() == botaoCancelar) {
			return Optional.of(TipoCancelamento.CANCELADO);
		}
		else if (resposta.get() == apenasEsse || resposta.get() == botaoSim) {
			return Optional.of(TipoCancelamento.PONTUAL);
		}
		else if (resposta.get() == todaRecorrencia) {
			return Optional.of(TipoCancelamento.RECORRENTE);
		}
		 return Optional.of(TipoCancelamento.CANCELADO);
	}
}
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
import model.entities.Paciente;
import model.services.PacienteService;
import util.enums.TipoCancelamento;
import util.enums.TipoRecorrencia;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		personalizarAlertaComVibracao(alert, "/img/icon_exclamacao.png", "show-alert");

		alert.show();
	}

	public static Optional<TipoRecorrencia> mostrarPopupAgendamentoCompleto(String hora, String data, String nomePaciente) {
		
		while (true) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Agendamento de horário");
			alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora +
					"\n\nDeseja agendar esse horário para " + nomePaciente + "?");

			personalizarAlerta(alert, "/img/icon_calendario.png", "mostrarPopupAgendamentoCompleto");
			
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

		personalizarAlerta(alert, "/img/icon_calendario.png", "agendamentoRealizadoComSucesso");

		alert.show();
	}	
	
	public static Optional<TipoCancelamento> confirmarCancelamentoDoAgendamento(String hora, String data, String nomePaciente, boolean ehRecorrente) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Cancelamento de horário");
		alert.setHeaderText(LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + hora);

		personalizarAlertaComVibracao(alert, "/img/icon_cancelar.png", "confirmarCancelamentoDoAgendamento");
		
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

	private static void personalizarAlerta(Alert alert, String caminhoIcone, String classeCSS) {
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Alerts.class.getResourceAsStream(caminhoIcone)));
		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add(classeCSS);
	}
	
	private static void personalizarAlertaComVibracao(Alert alert, String caminhoIcone, String classeCSS) {
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Alerts.class.getResourceAsStream(caminhoIcone)));
		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/alerts.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add(classeCSS);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), e -> alert.getDialogPane().setTranslateX(0)),
				new KeyFrame(Duration.millis(50), e -> alert.getDialogPane().setTranslateX(4)),
				new KeyFrame(Duration.millis(100), e -> alert.getDialogPane().setTranslateX(-4)),
				new KeyFrame(Duration.millis(150), e -> alert.getDialogPane().setTranslateX(2)),
				new KeyFrame(Duration.millis(200), e -> alert.getDialogPane().setTranslateX(-2)),
				new KeyFrame(Duration.millis(250), e -> alert.getDialogPane().setTranslateX(1)),
				new KeyFrame(Duration.millis(300), e -> alert.getDialogPane().setTranslateX(0)));
		timeline.play();
	}
	
	public static boolean confirmarExclusaoPaciente(Paciente paciente, PacienteService servicoPaciente) {
				
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exclusão de paciente");
		alert.setHeaderText("Essa ação é definitiva \n\n Tem certeza que deseja excluir o paciente: "
		+ paciente.getNomePaciente() + "?");
		alert.setContentText("");

		personalizarAlertaComVibracao(alert, "/img/icon_cancelar.png", "confirmarCancelamentoDoAgendamento");
		
		ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
		ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(botaoSim, botaoCancelar);
		
		Optional<ButtonType> resposta = alert.showAndWait();
				
		if (resposta.isPresent() && resposta.get() == botaoSim) {
			servicoPaciente.deletarPacienteAtual(paciente);	
			exclusaoRealizadaComSucesso();
			return true;
		}	
		return false;
	}
	
	public static boolean confirmarExclusaoDeDocumento(String nomeModelo) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exclusão de documento");
		alert.setHeaderText("Essa ação é irreversível \n\n Tem certeza que deseja excluir o documento: "
		+ nomeModelo + "?");
		alert.setContentText("");
		
		personalizarAlertaComVibracao(alert, "/img/icon_cancelar.png", "confirmarCancelamentoDoAgendamento");
		
		ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
		ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(botaoSim, botaoCancelar);
		
		Optional<ButtonType> resposta = alert.showAndWait();
		return resposta.isPresent() && resposta.get() == botaoSim;	
	}
	
	public static void exclusaoRealizadaComSucesso() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Sucesso");
		alert.setHeaderText("Exclusão relizada com sucesso!");
		alert.setContentText(null);

		personalizarAlerta(alert, "/img/icon_lixeira.png", "agendamentoRealizadoComSucesso");

		alert.show();
	}
}
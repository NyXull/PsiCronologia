package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Agenda;
import model.entities.Paciente;
import model.entities.Psicologo;
import model.services.AgendaService;
import util.Alerts;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.SessaoUsuario;
import util.ViewLoader;
import util.enums.TipoCancelamento;
import util.enums.TipoRecorrencia;

public class AgendaEditarController implements Initializable {

	@FXML
	private VBox vBox1AgendaEditar;

	@FXML
	private VBox vBox2AgendaEditar;

	@FXML
	private HBox hBoxPaiAgendaEditar;

	@FXML
	private Button btInicio;

	@FXML
	private Button btNomeDoPaciente;

	@FXML
	private Button btProntuario;

	@FXML
	private Button btFinanceiro;

	@FXML
	private Button btRelatorios;

	@FXML
	private Button btBiblioteca;

	@FXML
	private Text txtMesAtual;

	@FXML
	private Button btMesAnterior;

	@FXML
	private Button btMesSeguinte;

	@FXML
	private GridPane gridDiaDoMes;

	@FXML
	private GridPane gridHorarios;

	private YearMonth mesAtual;

	@FXML
	public void onBtInicioAction() {
		ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
	}

	@FXML
	public void onBtNomeDoPaciente() {		
		ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
	}

	@FXML
	public void onBtProntuario() {
		ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
	}

	@FXML
	public void onBtFinanceiro() {
		ViewLoader.loadView("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css");
	}

	@FXML
	public void onBtRelatorios() {
		ViewLoader.loadView("/fxml/relatorio.fxml", "/css/relatorio.css");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vBox1AgendaEditar.prefWidthProperty().bind(hBoxPaiAgendaEditar.widthProperty().multiply(0.25));
		vBox2AgendaEditar.prefWidthProperty().bind(hBoxPaiAgendaEditar.widthProperty().multiply(0.75));

		exibirNomePaciente();
		mesAtual = YearMonth.now();
		atualizarCalendario();
		atualizarHorarios(LocalDate.now());
	}

	private void atualizarCalendario() {
		gridDiaDoMes.getChildren().clear();

		Locale locale = Locale.forLanguageTag("pt-BR");
		String nomeMes = mesAtual.getMonth().getDisplayName(TextStyle.FULL, locale);

		txtMesAtual.setText(nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1) + " " + mesAtual.getYear());

		LocalDate primeiroDiaDoMes = mesAtual.atDay(1);
		int diaDaSemana = primeiroDiaDoMes.getDayOfWeek().getValue();

		int diasNoMes = mesAtual.lengthOfMonth();
		int linha = 0;
		int coluna = (diaDaSemana % 7);

		LocalDate hoje = LocalDate.now();

		for (int dia = 1; dia <= diasNoMes; dia++) {
			Button botaoDia = new Button(String.valueOf(dia));

			if (hoje.getDayOfMonth() == dia && hoje.getMonthValue() == mesAtual.getMonthValue()
					&& hoje.getYear() == mesAtual.getYear()) {

				botaoDia.getStyleClass().add("btDiaAtual");
			} else {
				botaoDia.getStyleClass().add("btDiaMes");
			}

			final int diaSelecionado = dia;
			botaoDia.setOnAction(e -> {
				gridDiaDoMes.getChildren().forEach(node -> node.getStyleClass().remove("btDiaClicado"));

				botaoDia.getStyleClass().add("btDiaClicado");

				LocalDate dataSelecionada = mesAtual.atDay(diaSelecionado);

				atualizarHorarios(dataSelecionada);
			});

			gridDiaDoMes.add(botaoDia, coluna, linha);

			coluna++;
			if (coluna > 6) {
				coluna = 0;
				linha++;
			}
		}
	}

	@FXML
	public void onBtMesAnterior() {
		mesAtual = mesAtual.minusMonths(1);
		atualizarCalendario();
	}

	@FXML
	public void onBtMesSeguinte() {
		mesAtual = mesAtual.plusMonths(1);
		atualizarCalendario();
	}

	private void atualizarHorarios(LocalDate dataSelecionada) {
		gridHorarios.getChildren().clear();

		Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
		AgendaService agendaService = new AgendaService();

		List<LocalTime> horariosOcupados = agendaService.buscarHorariosOcupados(dataSelecionada, psicologo);

		int linha = 0;
		int coluna = 0;

		LocalTime horario = LocalTime.of(8, 0);
		LocalTime fim = LocalTime.of(19, 30);

		boolean dataEhHoje = dataSelecionada.isEqual(LocalDate.now());
		LocalTime horarioAtual = LocalTime.now();

		while (!horario.isAfter(fim)) {

			String horarioFormatado = horario.format(DateTimeFormatter.ofPattern("HH:mm"));
			Button botaoHorario = new Button(horarioFormatado);
			botaoHorario.getStyleClass().add("btHorario");

			boolean ocupado = horariosOcupados.contains(horario);

			if (ocupado) {
				botaoHorario.getStyleClass().add("btHorarioOcupado");
			} 
			else {
				botaoHorario.getStyleClass().add("btHorarioDisponivel");
			}

			Optional<Agenda> agOpt = agendaService.buscarAgendamento(dataSelecionada, horario, psicologo);
			agOpt.ifPresent(agenda -> {
				String nomePaciente = agenda.getPaciente().getNomePaciente();
				Tooltip tooltip = new Tooltip("Paciente: " + nomePaciente);
				Tooltip.install(botaoHorario, tooltip);
			});

			final LocalTime horarioFinal = horario;

			boolean horarioEhPassado = dataSelecionada.isBefore(LocalDate.now())
					|| (dataEhHoje && horario.isBefore(horarioAtual));

			if (!horarioEhPassado) {
				botaoHorario.setOnAction(event -> {

					if (ocupado) {
						tratarCliqueEmHorarioAgendado(dataSelecionada, horarioFinal);
					} 
					else {
						tratarCliqueEmHorarioDisponivel(dataSelecionada, horarioFinal, botaoHorario);
					}
				});
			}
			else {
				botaoHorario.setOnAction(null);
				botaoHorario.getStyleClass().add("btHorarioInativo");
			}

			gridHorarios.add(botaoHorario, coluna, linha);

			coluna++;
			if (coluna > 5) {
				coluna = 0;
				linha++;
			}
			horario = horario.plusMinutes(30);
		}
	}

	private void tratarCliqueEmHorarioAgendado(LocalDate data, LocalTime horario) {
		Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
		Paciente paciente = SessaoPaciente.getPaciente();
		AgendaService agendaService = new AgendaService();

		Optional<Agenda> agendaOptional = agendaService.buscarAgendamento(data, horario, psicologo);

		if (agendaOptional.isEmpty()) {
			Alerts.showAlert("Erro", "Agendamento não encontrado", null, Alert.AlertType.ERROR);
			return;
		}

		Agenda agenda = agendaOptional.get();
		TipoRecorrencia tipo = TipoRecorrencia.valueOf(agenda.getRecorrencia().toUpperCase());
		boolean ehRecorrente = tipo != TipoRecorrencia.PONTUAL;

		Optional<TipoCancelamento> tipoCancelamento = Alerts.confirmarCancelamentoDoAgendamento(horario.toString(),
				data.toString(), paciente.getNomePaciente(), ehRecorrente);

		if (tipoCancelamento.isEmpty() || tipoCancelamento.get() == TipoCancelamento.CANCELADO) {
			return;
		}

		switch (tipoCancelamento.get()) {
			case PONTUAL:
				agendaService.cancelar(data, horario, psicologo);
				Alerts.showAlert("Cancelado", "Agendamento cancelado com sucesso!", null, Alert.AlertType.INFORMATION);
				break;
	
			case RECORRENTE:
				agendaService.cancelarRecorrente(horario, psicologo, Optional.empty());
				Alerts.showAlert("Cancelado", "Todos os agendamentos da recorrência foram cancelados!", null,
						Alert.AlertType.INFORMATION);
				break;
	
			default:
				break;
		}
		atualizarHorarios(data);
	}

	private void tratarCliqueEmHorarioDisponivel(LocalDate data, LocalTime horario, Button botao) {
		Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomePaciente = paciente.getNomePaciente();
		String horaFormatada = horario.format(DateTimeFormatter.ofPattern("HH:mm"));

		Optional<TipoRecorrencia> resultado = Alerts.mostrarPopupAgendamentoCompleto(horaFormatada, data.toString(),
				nomePaciente);

		resultado.ifPresent(tipo -> {
			AgendaService agendaService = new AgendaService();

			try {
				switch (tipo) {
					case PONTUAL -> {
						agendaService.agendar(data, horario, psicologo, paciente, "pontual");
						Alerts.agendamentoRealizadoComSucesso("Sucesso", "Agendamento realizado!", null,
								Alert.AlertType.INFORMATION);
						destacarBotaoComoAgendado(botao);
						atualizarHorarios(data);
					}
					case SEMANAL, QUINZENAL, MENSAL -> {
						agendarRecorrente(data, horario, psicologo, paciente, agendaService, botao, tipo);
						atualizarHorarios(data);
					}
					case CANCELADO -> {
						// nada acontece
					}
				}
			} catch (IllegalStateException e) {
				Alerts.showAlert("Erro", e.getMessage(), null, Alert.AlertType.ERROR);
			} catch (Exception e) {
				Alerts.showAlert("Erro", "Erro inesperado: " + e.getMessage(), null, Alert.AlertType.ERROR);
			}
		});
	}

	private void agendarRecorrente(LocalDate dataInicial, LocalTime horario, Psicologo psicologo, Paciente paciente,
			AgendaService agendaService, Button botaoHorario, TipoRecorrencia tipo) {

		try {
			int repeticoes = switch (tipo) {
				case SEMANAL -> 4;
				case QUINZENAL -> 4;
				case MENSAL -> 3;
				default -> throw new IllegalArgumentException("Tipo de recorrência inválido para este método");
			};
			
			agendaService.agendarRecorrente(dataInicial, horario, psicologo, paciente, tipo, repeticoes);
			Alerts.agendamentoRealizadoComSucesso("Sucesso", "Agendamentos recorrentes realizados", null,
					Alert.AlertType.INFORMATION);
			
			destacarBotaoComoAgendado(botaoHorario);
			atualizarHorarios(dataInicial);
		} 
		catch (IllegalStateException e) {
			Alerts.showAlert("Aviso", "Você já tem um ou mais horários recorrentes nesse dia!", null,
					Alert.AlertType.WARNING);
		} 
		catch (Exception e) {
			Alerts.showAlert("Erro", "Erro inesperado: " + e.getMessage(), null, Alert.AlertType.ERROR);
		}
	}

	private void destacarBotaoComoAgendado(Button botaoOriginal) {
		botaoOriginal.getStyleClass().remove("btHorarioDisponivel");

		if (!botaoOriginal.getStyleClass().contains("btHorarioOcupado")) {
			botaoOriginal.getStyleClass().add("btHorarioOcupado");
		}
	}

	private void exibirNomePaciente() {
		Paciente paciente = SessaoPaciente.getPaciente();
		String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
		btNomeDoPaciente.setText(nomeFormatado.toString());
	}
}
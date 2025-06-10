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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entities.Agenda;
import model.entities.Psicologo;
import model.services.AgendaService;
import util.ExibirNomeDoPaciente;
import util.SessaoUsuario;
import util.ViewLoader;

public class AgendaVisualizacaoController implements Initializable{

	@FXML
	private VBox vBox1AgendaVisualizacao;
	
	@FXML
	private VBox vBox2AgendaVisualizacao;
	
	@FXML
	private HBox hBoxPaiAgendaVisualizacao;
	
	@FXML
	private Button btInicio;
	
	@FXML
	private Button btFinanceiro;
	
	@FXML
	private Button btBiblioteca;
	
	@FXML
	private Button btSair;
	
	@FXML
	private Text txtMesAtual;
	
	@FXML
	private Button btSemanaAnterior;
	
	@FXML
	private Button btSemanaSeguinte;
	
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
	public void onBtFinanceiroAction() {
		System.out.println("onBtFinanceiroAction");
	}
	
	@FXML
	public void onBtBibliotecaAction() {
		ViewLoader.loadView("/fxml/biblioteca.fxml", "/css/biblioteca.css");
	}
	
	@FXML
	public void onBtSairAction() {
		SessaoUsuario.encerrarSessao();
	    ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vBox1AgendaVisualizacao.prefWidthProperty().bind(hBoxPaiAgendaVisualizacao.widthProperty().multiply(0.25));
		vBox2AgendaVisualizacao.prefWidthProperty().bind(hBoxPaiAgendaVisualizacao.widthProperty().multiply(0.75));
		
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
	
	private void atualizarHorarios(LocalDate dataSelecionada) {
		gridHorarios.getChildren().clear();
		
		Psicologo psicolgo = SessaoUsuario.getPsicologoLogado();
		AgendaService agendaService = new AgendaService();
		
		List<LocalTime> horariosOcupados = agendaService.buscarHorariosOcupados(dataSelecionada, psicolgo);
		
		int linha = 0;
		int coluna = 0;
		
		LocalTime horario = LocalTime.of(8, 0);
		LocalTime fim = LocalTime.of(19, 30);
		
		LocalTime horarioAtual = LocalTime.now();
		
		while (!horario.isAfter(fim)) {
			
			String horarioFormatado = horario.format(DateTimeFormatter.ofPattern("HH:mm"));
			Label labelHorario = new Label(horarioFormatado);
			
			boolean ocupado = horariosOcupados.contains(horario);
			
			if (ocupado) {
				Optional<Agenda> agendaOpt = agendaService.buscarAgendamento(dataSelecionada, horario, psicolgo);
				String nomePaciente = agendaOpt.get().getPaciente().getNomePaciente().toString();
				String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(nomePaciente);
				labelHorario = new Label(horarioFormatado + " \n " + nomeFormatado);
				Tooltip tooltip = new Tooltip(nomePaciente);
				Tooltip.install(labelHorario, tooltip);
				labelHorario.getStyleClass().add("lblHorarioOcupado");
			}
			else {
				labelHorario = new Label(horarioFormatado + "\n");
				labelHorario.getStyleClass().add("lblHorarioDisponivel");
			}
			
			gridHorarios.add(labelHorario, coluna, linha);
			coluna++;
			
			if (coluna > 5) {
				coluna = 0;
				linha++;
			}
			horario = horario.plusMinutes(30);
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
}
package controller;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import model.entities.Paciente;
import model.entities.Relatorio;
import model.services.RelatorioService;
import util.Alerts;
import util.ExibirNomeDoPaciente;
import util.SessaoPaciente;
import util.SessaoUsuario;
import util.ViewLoader;
import util.html.HtmlUtils;
import util.html.PdfExporter;

public class RelatorioController implements Initializable {

    @FXML
    public TextField textFieldNomeDocumento;

    @FXML
    public ComboBox<String> comboBoxModelo;

    @FXML
    private VBox vBox1Relatorios;

    @FXML
    private VBox vBox2Relatorios;

    @FXML
    private HBox hBoxPaiRelatorios;

    @FXML
    private Button btNomeDoPaciente;

    @FXML
    private Button btProntuario;

    @FXML
    private Button btAgenda;

    @FXML
    private Button btFinanceiro;
    
    @FXML
    private HTMLEditor htmlEditor;

    private ObservableList<String> modelos = FXCollections.observableArrayList();

    private RelatorioService relatorioService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox1Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.25));
        vBox2Relatorios.prefWidthProperty().bind(hBoxPaiRelatorios.widthProperty().multiply(0.75));

        relatorioService = new RelatorioService();
        
        carregarModelos();
        
        exibirNomePaciente();

        comboBoxModelo.setItems(modelos);
        comboBoxModelo.setOnAction(e -> carregarTextoDoModelo());
    }

    private void carregarModelos() {
		modelos.clear();
		modelos.addAll(relatorioService.listarNomesDeModelo(SessaoUsuario.getIdPsico()));		
	}

	@FXML
    private void onBtHomeAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    private void onBtNomeDoPacienteAction() {
        ViewLoader.loadView("/fxml/home-paciente.fxml", "/css/home-paciente.css");
    }

    @FXML
    private void onBtProntuarioAction() {
        ViewLoader.loadView("/fxml/prontuario-lista.fxml", "/css/prontuario-lista.css");
    }

    @FXML
    private void onBtAgendaAction() {
        ViewLoader.loadView("/fxml/agenda-editar.fxml", "/css/agenda-editar.css");
    }

    @FXML
    private void onBtFinanceiroAction() {
        ViewLoader.loadView("/fxml/financeiro-pagamento.fxml", "/css/financeiro-pagamento.css");
    }

    @FXML
    public void onBtSalvarAction() {
        String nomeModelo = textFieldNomeDocumento.getText().trim();
        String conteudoHtml = htmlEditor.getHtmlText();
        
        if (nomeModelo.isEmpty() || Jsoup.parse(conteudoHtml).text().trim().isEmpty()) {
        	Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha o nome e o conteúdo do documento.", Alert.AlertType.ERROR);
            return;
        }
        
        Relatorio relatorio = new Relatorio(null, SessaoUsuario.getIdPsico(), conteudoHtml, nomeModelo);
        relatorioService.salvarOuAtualizarRelatorio(relatorio);
        
        carregarModelos();
        carregarModelos();
        Alerts.showAlert("Sucesso", null, "Modelo salvo com sucesso!", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void onBtBaixarPdfAction() {
        String nomeArquivo = textFieldNomeDocumento.getText().trim();
        String conteudoOriginal = htmlEditor.getHtmlText();
        
        if (nomeArquivo.isEmpty() || Jsoup.parse(conteudoOriginal).text().trim().isEmpty()) {
        	Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha o nome e o conteúdo do documento.", Alert.AlertType.ERROR);
            return;
        }
        
        try {
        	String htmlFormatado = HtmlUtils.prepararHtmlParaPDF(conteudoOriginal);
        	
        	String userHome = System.getProperty("user.home");
        	String caminhoCompleto = userHome + "/Documents/" + nomeArquivo + ".pdf";
        	
        	PdfExporter.exportarComoPdf(htmlFormatado, caminhoCompleto);

        	Alerts.showAlert("Sucesso", null, "Documento exportado como PDF.", Alert.AlertType.INFORMATION);
        }
        catch (Exception e) {
            Alerts.showAlert("Erro", "Exportação falhou", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onBtExcluirModeloAction() {
        String nomeSelecionado = comboBoxModelo.getSelectionModel().getSelectedItem();
        
        if (nomeSelecionado == null) {
        	Alerts.showAlert("Erro", null, "Nenhum modelo selecionado para exclusão.", Alert.AlertType.ERROR);
            return;
        }
        
        boolean confirmar = Alerts.confirmarExclusaoDeDocumento(nomeSelecionado);
        
        if (confirmar) {
        	Relatorio modelo = relatorioService.buscarPorNomeModelo(nomeSelecionado, SessaoUsuario.getIdPsico());
                     
	        if (modelo != null) {
	        	relatorioService.excluirRelatorio(modelo.getId());
	        	carregarModelos();
	        	htmlEditor.setHtmlText("");
	        	textFieldNomeDocumento.clear();
	        	 Alerts.showAlert("Sucesso", null, "Modelo excluído com sucesso!", Alert.AlertType.INFORMATION);
	        }
        }
    }
    
    private void carregarTextoDoModelo() {
    	String nomeSelecionado = comboBoxModelo.getSelectionModel().getSelectedItem();
    	
    	if (nomeSelecionado != null) {
    		Relatorio modelo = relatorioService.buscarPorNomeModelo(nomeSelecionado, SessaoUsuario.getIdPsico());
    		if (modelo != null) {
    			htmlEditor.setHtmlText(modelo.getDescricao());
    			textFieldNomeDocumento.setText(modelo.getNomeModelo());
    		}
    	}
    }


    private void exibirNomePaciente() {
        Paciente paciente = SessaoPaciente.getPaciente();
        String nomeFormatado = ExibirNomeDoPaciente.formatarNomePaciente(paciente);
        btNomeDoPaciente.setText(nomeFormatado.toString());
    }

    public void gerarNomeModelo() {
        String nomeModelo = textFieldNomeDocumento.getText();

        if (nomeModelo.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Preencha o nome do documento.",
                    Alert.AlertType.ERROR);
            return;
        }

        if (!modelos.contains(nomeModelo)) {
            modelos.add(nomeModelo);
        }
    }
}

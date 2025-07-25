package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entities.Biblioteca;
import model.entities.Psicologo;
import model.services.BibliotecaService;
import util.Alerts;
import util.SessaoUsuario;
import util.ViewLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class BibliotecaController implements Initializable {

    @FXML
    private HBox hBoxPaiBiblioteca;

    @FXML
    private VBox vBox1Biblioteca;

    @FXML
    private VBox vBox2Biblioteca;

    @FXML
    private Button btInicio;

    @FXML
    private Button btAgenda;

    @FXML
    private Button btSair;

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btSalvar;

    @FXML
    private TilePane tilePaneArquivos;

    @FXML
    private ScrollPane scrollPaneArquivos;

    private final Set<String> arquivosAdicionados = new HashSet<>();

    @FXML
    public void onBtInicioAction() {
        ViewLoader.loadView("/fxml/home.fxml", "/css/home.css");
    }

    @FXML
    public void onBtAgendaAction() {
        ViewLoader.loadView("/fxml/agenda-visualizacao.fxml", "/css/agenda-visualizacao.css");
    }

    @FXML
    private void onBtSairAction() {
        SessaoUsuario.encerrarSessao();
        ViewLoader.loadView("/fxml/login2.fxml", "/css/login2.css");
    }

    @FXML
    private void onBtAdicionarAction() {
        adicionarArquivo();
    }

    @FXML
    private void onBtSalvarAction() {
        List<Biblioteca> bibliotecas = listarArquivos();

        if (bibliotecas != null && !bibliotecas.isEmpty()) {
            BibliotecaService bibliotecaService = new BibliotecaService();

            for (Biblioteca biblioteca : bibliotecas) {
                bibliotecaService.salvarArquivo(biblioteca);
            }

            Alerts.showAlert("Sucesso", "Arquivos salvos", "Os arquivos foram salvos com sucesso.", Alert.AlertType.INFORMATION);
        } else {
            Alerts.showAlert("Aviso", "Nenhum arquivo para salvar", "Nenhum arquivo válido foi encontrado.", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        padraoLarguraVBox();

        tilePaneArquivos.prefWidthProperty().bind(scrollPaneArquivos.widthProperty());

        if (psicologoTemArquivosSalvos()) {
            try {
                carregarArquivos();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    private void padraoLarguraVBox() {
        vBox1Biblioteca.prefWidthProperty().bind(hBoxPaiBiblioteca.widthProperty().multiply(0.25));
        vBox2Biblioteca.prefWidthProperty().bind(hBoxPaiBiblioteca.widthProperty().multiply(0.75));
    }

    private void adicionarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione arquivos");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "Arquivos suportados (*.txt, *.png, *.jpeg, *.jpg, *.pdf, *.docx)",
                "*.txt", "*.png", "*.jpeg", "*.jpg", "*.pdf", "*.docx"
        );
        fileChooser.getExtensionFilters().add(extensionFilter);

        Stage stage = (Stage) btAdicionar.getScene().getWindow();

        List<File> arquivosSelecionados = fileChooser.showOpenMultipleDialog(stage);

        if (arquivosSelecionados != null) {
            for (File file : arquivosSelecionados) {
                String nome = file.getName();
                if (!arquivosAdicionados.contains(nome)) {
                    arquivosAdicionados.add(nome);
                    VBox item = criarItemArquivo(nome, file.getPath());
                    tilePaneArquivos.getChildren().add(item);
                }
            }
        }
    }

    private VBox criarItemArquivo(String nomeArquivo, String caminhoCompletoDoArquivo) {
        ImageView icone = criarIconeArquivo(nomeArquivo);
        Label label = new Label(nomeArquivo);

        label.setMaxWidth(100);
        label.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(icone, label);

        vbox.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(label, Priority.ALWAYS);

        label.getStyleClass().add("label");

        ContextMenu contextMenu = new ContextMenu();

        MenuItem copiarItem = new MenuItem("Copiar");
        MenuItem excluirItem = new MenuItem("Excluir");

        copiarItem.setOnAction(event -> {
            File arquivoOriginal = new File(caminhoCompletoDoArquivo);
            if (!arquivoOriginal.exists()) {
                return;
            }

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();

            content.putFiles(Collections.singletonList(arquivoOriginal));

            clipboard.setContent(content);
        });

        excluirItem.setOnAction(event -> {
            File arquivo = new File(caminhoCompletoDoArquivo);
            if (arquivo.exists()) {
                boolean excluido = arquivo.delete();

                if (excluido) {
                    excluirArquivo(nomeArquivo, caminhoCompletoDoArquivo);
                    Alerts.showAlert("Exclusão de arquivo", "Arquivo excluído!", "O arquivo selecionado foi deletado.", Alert.AlertType.INFORMATION);
                } else {
                    Alerts.showAlert("Exclusão de arquivo", "Não foi possível excluir o arquivo!", "Verifique se o arquivo está aberto ou se você tem permissão para exclusão.", Alert.AlertType.ERROR);
                    return;
                }
            }

            tilePaneArquivos.getChildren().remove(vbox);
            arquivosAdicionados.remove(nomeArquivo);
        });

        contextMenu.getItems().addAll(copiarItem, excluirItem);

        contextMenu.getStyleClass().add("context-menu");

        vbox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                contextMenu.show(vbox, event.getScreenX(), event.getScreenY());
            } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                abrirArquivo(caminhoCompletoDoArquivo);
            }

        });

        vbox.setCursor(Cursor.HAND);

        vbox.setUserData(caminhoCompletoDoArquivo);

        Tooltip tooltip = new Tooltip(nomeArquivo);
        Tooltip.install(label, tooltip);

        return vbox;
    }

    private ImageView criarIconeArquivo(String nomeArquivo) {
        String extensao = "";

        int i = nomeArquivo.lastIndexOf('.');
        if (i > 0) {
            extensao = nomeArquivo.substring(i + 1).toLowerCase();
        }

        String caminhoIcone = "";

        switch (extensao) {
            case "txt":
                caminhoIcone = "/img/icon_txt.png";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                caminhoIcone = "/img/icon_img.png";
                break;
            case "pdf":
                caminhoIcone = "/img/icon_pdf.png";
                break;
            case "docx":
                caminhoIcone = "/img/icon_docx.png";
                break;
            default:
                break;
        }

        Image imagem = new Image(Objects.requireNonNull(getClass().getResource(caminhoIcone)).toExternalForm());
        ImageView imageView = new ImageView(imagem);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        return imageView;
    }

    public void abrirArquivo(String caminhoCompletoDoArquivo) {
        try {
            File arquivo = new File(caminhoCompletoDoArquivo);

            if (arquivo.exists()) {
                Desktop.getDesktop().open(arquivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Biblioteca validacaoEInstaciacao(String caminhoArquivo, String nomeArquivo) {

        if (caminhoArquivo == null || caminhoArquivo.isEmpty() ||
                nomeArquivo == null || nomeArquivo.isEmpty()) {
            Alerts.showAlert("Erro de Validação", "Campos obrigatórios!", "Os arquivos precisam ter todos os atributos.", Alert.AlertType.ERROR);
            return null;
        }

        if (caminhoArquivo.length() > 100 || nomeArquivo.length() > 100) {
            Alerts.showAlert("Erro de Validação", "Limite de caracteres alcançado!", "Um arquivo tem endereço ou nome muito longo.", Alert.AlertType.ERROR);
            return null;
        }

        try {
            Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
            if (psicologo == null) {
                return null;
            }

            Biblioteca biblioteca = new Biblioteca();

            biblioteca.setIdPsicologo(psicologo.getIdPsico());
            biblioteca.setCaminhoArquivo(caminhoArquivo);
            biblioteca.setNomeArquivo(nomeArquivo);

            return biblioteca;
        } catch (Exception e) {
            Alerts.showAlert("Erro de Validação", "Erro inesperado!", "Ocorreu um erro ao salvar arquivos.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    public List<Biblioteca> listarArquivos() {
        List<Biblioteca> bibliotecas = new ArrayList<>();

        for (Node node : tilePaneArquivos.getChildren()) {
            if (node instanceof VBox vBox) {
                String nomeArquivo = null;

                if (!vBox.getChildren().isEmpty() && vBox.getChildren().get(1) instanceof Label label) {
                    nomeArquivo = label.getText();
                }

                String caminhoCompletoDoArquivo = (String) vBox.getUserData();

                if (nomeArquivo != null && !nomeArquivo.isEmpty() &&
                        caminhoCompletoDoArquivo != null && !caminhoCompletoDoArquivo.isEmpty()) {
                    Biblioteca biblioteca = validacaoEInstaciacao(caminhoCompletoDoArquivo, nomeArquivo);
                    if (biblioteca != null) {
                        bibliotecas.add(biblioteca);
                    }
                }
            }
        }

        return bibliotecas;
    }

    public boolean psicologoTemArquivosSalvos() {
        Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
        if (psicologo == null) {
            return false;
        }

        BibliotecaService bibliotecaService = new BibliotecaService();

        return bibliotecaService.psicologoTemArquivosSalvos(psicologo.getIdPsico());
    }

    private void carregarArquivos() {
        Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
        if (psicologo == null) {
            return;
        }

        BibliotecaService bibliotecaService = new BibliotecaService();

        List<Biblioteca> arquivosDoPsicologo = bibliotecaService.carregarArquivos(psicologo.getIdPsico());

        for (Biblioteca arquivo : arquivosDoPsicologo) {
            String nome = arquivo.getNomeArquivo();

            // Verifica se já não está na lista (por segurança)
            if (!arquivosAdicionados.contains(nome)) {
                arquivosAdicionados.add(nome);
                VBox item = criarItemArquivo(nome, arquivo.getCaminhoArquivo());
                tilePaneArquivos.getChildren().add(item);
            }
        }
    }


    private void excluirArquivo(String nomeArquivo, String caminhoArquivo) {
        Psicologo psicologo = SessaoUsuario.getPsicologoLogado();
        if (psicologo == null) {
            return;
        }

        BibliotecaService bibliotecaService = new BibliotecaService();

        bibliotecaService.excluirArquivo(psicologo.getIdPsico(), nomeArquivo, caminhoArquivo);
    }
}

package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import util.interfaces.ParametroRecebivel;
import view.PsiCronologiaApp;

import java.io.IOException;

public class ViewLoader {

    public static <T> T loadView(String absoluteName, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(absoluteName));//busca a nova view
            HBox newHBox = loader.load(); //carrega e monta a view

            Scene mainScene = PsiCronologiaApp.getMainScene(); //pega a cena atual
            ScrollPane rootScroll = (ScrollPane) mainScene.getRoot(); //pega a estrutura principal do palco atual
            // (ScrollPane)

            rootScroll.setContent(newHBox); //troca tudo o que estava no ScrollPane pelo conteúdo da nova view

            mainScene.getStylesheets().clear(); //limpa o css 
            mainScene.getStylesheets().add(ViewLoader.class.getResource(cssPath).toExternalForm()); //chama o novo css

            return loader.getController(); //retorna o controller da nova view
        } catch (IOException e) {
            Alerts.showAlert(null, "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
            return null;
        }
    }

    public static <T, C extends ParametroRecebivel<T>> C loadView(String absoluteName, String cssPath, T parametro) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(absoluteName));
            HBox newHBox = loader.load();

            Scene mainScene = PsiCronologiaApp.getMainScene();
            ScrollPane rootScroll = (ScrollPane) mainScene.getRoot();
            rootScroll.setContent(newHBox);

            mainScene.getStylesheets().clear();
            mainScene.getStylesheets().add(ViewLoader.class.getResource(cssPath).toExternalForm());

            C controller = loader.getController();
            controller.receberParametro(parametro);

            return controller;

        } catch (IOException e) {
            Alerts.showAlert(null, "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
            return null;
        }
    } 
}

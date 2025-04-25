package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import view.PsiOrganizeApp;

public class ViewLoader {
	
	public static <T> T loadView(String absoluteName, String cssPath) {
        try {
        	FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(absoluteName));//busca a nova view
            HBox newHBox = loader.load(); //carrega e monta a view

            Scene mainScene = PsiOrganizeApp.getMainScene(); //pega a cena atual
            ScrollPane rootScroll = (ScrollPane) mainScene.getRoot(); //pega a estrutura principal do palco atual (ScrollPane)
            
            rootScroll.setContent(newHBox); //troca tudo o que estava no ScrollPane pelo conteúdo da nova view

            mainScene.getStylesheets().clear(); //limpa o css 
            mainScene.getStylesheets().add(ViewLoader.class.getResource(cssPath).toExternalForm()); //chama o novo css

            return loader.getController(); //retorna o controller da nova view
        }
        catch (IOException e) {
            Alerts.showAlert(null, "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
            return null;
        }
    }
}
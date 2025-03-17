package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        Scene scene = new Scene(root, 600, 600, Color.rgb(150, 123, 252));

        Image icon = new Image("C:\\Projetos-Intellij\\TCC\\PsiOrganize\\src\\main\\resources\\img\\java_logo_256-256.png");
        Image imagem = new Image("C:\\Projetos-Intellij\\TCC\\PsiOrganize\\src\\main\\resources\\img\\Duke.png");
        ImageView imageView = new ImageView(imagem);

        Text psiOrganize = new Text();
        Text cadastro = new Text();
        Text nome = new Text();
        Text email = new Text();
        Text senha = new Text();
        Text nomeFicticio = new Text();
        Text emailFicticio = new Text();
        Text senhaFicticia = new Text();

        Line linha1 = new Line();
        Line linha2 = new Line();
        Line linha3 = new Line();

        Rectangle retanguloFundo = new Rectangle();

        psiOrganize.setText("PsiOrganize");
        psiOrganize.setX(50);
        psiOrganize.setY(400);
        psiOrganize.setFont(Font.font("Verdana", 24.5));
        psiOrganize.setFill(Color.WHITE);

        cadastro.setText("CADASTRO");
        cadastro.setX(375);
        cadastro.setY(150);
        cadastro.setFont(Font.font("Verdana", 19.5));
        cadastro.setFill(Color.BLACK);

        nome.setText("NOME");
        nome.setX(300);
        nome.setY(200);
        nome.setFont(Font.font("Verdana", 16.5));
        nome.setFill(Color.BLACK);

        nomeFicticio.setText("Maria de Padilha Santos");
        nomeFicticio.setX(300);
        nomeFicticio.setY(250);
        nomeFicticio.setFont(Font.font("Verdana", 14.5));
        nomeFicticio.setFill(Color.BLACK);

        email.setText("EMAIL");
        email.setX(300);
        email.setY(300);
        email.setFont(Font.font("Verdana", 16.5));
        email.setFill(Color.BLACK);

        emailFicticio.setText("maria@gmail.com");
        emailFicticio.setX(300);
        emailFicticio.setY(350);
        emailFicticio.setFont(Font.font("Verdana", 14.5));
        emailFicticio.setFill(Color.BLACK);

        senha.setText("SENHA");
        senha.setX(300);
        senha.setY(400);
        senha.setFont(Font.font("Verdana", 16.5));
        senha.setFill(Color.BLACK);

        senhaFicticia.setText("**********");
        senhaFicticia.setX(300);
        senhaFicticia.setY(450);
        senhaFicticia.setFont(Font.font("Verdana", 14.5));
        senhaFicticia.setFill(Color.BLACK);

        linha1.setStartX(300);
        linha1.setEndX(550);
        linha1.setStartY(260);
        linha1.setEndY(260);
        linha1.setStroke(Color.BLACK);
        linha1.setStrokeWidth(2);

        linha2.setStartX(300);
        linha2.setEndX(550);
        linha2.setStartY(360);
        linha2.setEndY(360);
        linha2.setStroke(Color.BLACK);
        linha2.setStrokeWidth(2);

        linha3.setStartX(300);
        linha3.setEndX(550);
        linha3.setStartY(460);
        linha3.setEndY(460);
        linha3.setStroke(Color.BLACK);
        linha3.setStrokeWidth(2);

        retanguloFundo.setX(250);
        retanguloFundo.setY(0);
        retanguloFundo.setWidth(350);
        retanguloFundo.setHeight(600);
        retanguloFundo.setFill(Color.rgb(235, 209,241));

        imageView.setX(100);
        imageView.setY(300);

        root.getChildren().add(retanguloFundo);
        root.getChildren().add(psiOrganize);
        root.getChildren().add(nome);
        root.getChildren().add(email);
        root.getChildren().add(senha);
        root.getChildren().add(cadastro);
        root.getChildren().add(nomeFicticio);
        root.getChildren().add(emailFicticio);
        root.getChildren().add(senhaFicticia);
        root.getChildren().add(linha3);
        root.getChildren().add(linha2);
        root.getChildren().add(linha1);
        root.getChildren().add(imageView);

        stage.setResizable(false);
        stage.setTitle("PsiOrganize");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }
}